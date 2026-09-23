package chessball;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TD6 ex. 2, Q8 : l'oracle de la Stratégie. Rouge sur le squelette.
 *
 * <p>Deux politiques interchangeables derrière {@code StrategieAdversaire}, un tournoi qui les
 * détient et en change entre deux manches <b>sans reconstruire la partie</b>.</p>
 */
class StrategieTest {

    /** Une stratégie de test : toujours le premier coup possible. Une lambda suffit. */
    static final StrategieAdversaire PREMIER_COUP = (plateau, trait) -> CoupsPossibles.pour(plateau, trait).get(0);

    @Test
    void lAleatoireNeProposeQueDesCoupsLegaux() {
        Partie partie = Partie.standard();
        StrategieAdversaire ia = new AdversaireAleatoire(new Random(42));
        for (int tour = 0; tour < 30; tour++) {
            Coup coup = ia.choisirCoup(partie.plateau().copie(), partie.trait());
            assertTrue(partie.jouer(coup).accepte(), "coup refusé au tour " + tour + " : " + coup);
        }
    }

    @Test
    void laMemeGraineRejoueLaMemeManche() {
        Tournoi a = new Tournoi(Partie.standard(), new AdversaireAleatoire(new Random(7)), new AdversaireAleatoire(new Random(11)));
        Tournoi b = new Tournoi(Partie.standard(), new AdversaireAleatoire(new Random(7)), new AdversaireAleatoire(new Random(11)));
        a.jouerManche(6);
        b.jouerManche(6);
        assertEquals(a.partie().journal().toString(), b.partie().journal().toString(),
                "le hasard est reçu et sa graine fixée : la manche est rejouable");
    }

    @Test
    void lHeuristiqueTireQuandLeButEstOuvert() {
        Plateau plateau = new Plateau(8);
        Piece tour = new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT");
        plateau.placer(tour, Position.of("d4"));
        plateau.poserBallonLibre(Position.of("d4"));          // la tour s'en empare (J5)
        plateau.placer(new Piece(Couleur.ROUGES, TypePiece.FOU, "rF"), Position.of("a8"));
        Coup coup = new AdversaireHeuristique(new Random(1)).choisirCoup(plateau, Couleur.BLEUS);
        assertInstanceOf(Coup.Tir.class, coup, "la colonne d est libre jusqu'au fond : il faut tirer");
        assertEquals(Direction.NORD, ((Coup.Tir) coup).direction());
    }

    @Test
    void lAdversaireChangeEntreDeuxManchesSansReconstruireLaPartie() {
        Partie partie = Partie.standard();
        Tournoi tournoi = new Tournoi(partie, new AdversaireAleatoire(new Random(3)), new AdversaireAleatoire(new Random(4)));
        tournoi.jouerManche(3);
        int coupsApresManche1 = partie.journal().size();

        tournoi.definirAdversaire(Couleur.BLEUS, PREMIER_COUP);   // à chaud : la partie continue
        tournoi.jouerManche(3);

        assertSame(partie, tournoi.partie(), "la partie n'a pas été reconstruite");
        assertSame(PREMIER_COUP, tournoi.adversaire(Couleur.BLEUS));
        assertEquals(2, tournoi.manchesJouees());
        assertTrue(partie.journal().size() >= coupsApresManche1 + 6, "la seconde manche s'est jouée sur le même journal");
    }

    @Test
    void leTournoiDetientSesStrategiesParLInterface() {
        boolean concret = Arrays.stream(Tournoi.class.getDeclaredFields())
                .anyMatch(f -> f.getType() == AdversaireAleatoire.class || f.getType() == AdversaireHeuristique.class);
        assertTrue(!concret, "le contexte ne connaît aucune stratégie concrète");
    }
}
