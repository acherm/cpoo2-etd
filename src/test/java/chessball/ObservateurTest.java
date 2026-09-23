package chessball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S04 ex. 5, Q14 et Q15 : l'oracle de l'Observateur. Cinq tests, cinq rouges sur le squelette.
 *
 * <p>Ce que les tests fixent : <b>tout coup tenté</b> est notifié, accepté ou refusé, avec son
 * verdict. C'est à l'abonné de trier (le journal ignore les refus, le compteur les compte à part).
 * Un abonné retiré ne reçoit plus rien. Un but est un second événement.</p>
 */
class ObservateurTest {

    static final Coup A1_A4 = new Coup.Deplacement(Position.of("a1"), Position.of("a4"));
    static final Coup H8_H5 = new Coup.Deplacement(Position.of("h8"), Position.of("h5"));
    static final Coup A1_C3 = new Coup.Deplacement(Position.of("a1"), Position.of("c3"));   // une tour en diagonale : refusé

    Partie partie;
    JournalDesCoups journal;
    CompteurDeCoups compteur;

    @BeforeEach
    void setUp() {
        partie = Partie.standard();
        journal = new JournalDesCoups();
        compteur = new CompteurDeCoups();
    }

    @Test
    void deuxAbonnesSontPrevenusDuMemeCoup() {
        partie.ajouterEcouteur(journal);
        partie.ajouterEcouteur(compteur);
        assertTrue(partie.jouer(A1_A4).accepte());
        assertEquals(List.of("a1-a4"), journal.lignes());
        assertEquals(1, compteur.acceptes());
        assertEquals(0, compteur.refuses());
    }

    @Test
    void unCoupRefuseEstNotifieAvecSonVerdict() {
        partie.ajouterEcouteur(journal);
        partie.ajouterEcouteur(compteur);
        assertFalse(partie.jouer(A1_C3).accepte());
        assertEquals(List.of(), journal.lignes(), "le journal ne note que les coups acceptés");
        assertEquals(1, compteur.refuses(), "le compteur, lui, compte les refus");
        assertEquals(0, compteur.acceptes());
    }

    @Test
    void unAbonneRetireNeRecoitPlusRien() {
        partie.ajouterEcouteur(journal);
        partie.ajouterEcouteur(compteur);
        partie.jouer(A1_A4);
        partie.retirerEcouteur(journal);
        partie.jouer(H8_H5);
        assertEquals(List.of("a1-a4"), journal.lignes(), "plus rien après le désabonnement");
        assertEquals(2, compteur.acceptes(), "l'autre abonné continue de recevoir");
    }

    @Test
    void uneLambdaEstUnAbonneCommeUnAutre() {
        List<Coup> vus = new ArrayList<>();
        partie.ajouterEcouteur((coup, verdict) -> vus.add(coup));
        partie.jouer(A1_A4);
        partie.jouer(A1_C3);
        assertEquals(List.of(A1_A4, A1_C3), vus, "accepté ou refusé, tout coup tenté est notifié");
    }

    @Test
    void unButEstUnSecondEvenement() {
        Plateau plateau = new Plateau(8);
        Piece tour = new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT");
        plateau.placer(tour, Position.of("d4"));
        plateau.donnerBallon(tour);
        Partie seule = new Partie(plateau, Regles.duJeuDeBase(), Couleur.BLEUS);
        seule.ajouterEcouteur(compteur);
        assertTrue(seule.jouer(new Coup.Tir(Position.of("d4"), Direction.NORD)).accepte(), "tir vers la rangée 8, rien devant");
        assertEquals(1, seule.score(Couleur.BLEUS));
        assertEquals(1, compteur.buts(), "le compteur a été prévenu du but");
        assertEquals(1, compteur.acceptes(), "et du coup qui l'a marqué");
    }
}
