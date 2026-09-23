package chessball;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S05, Q5 bis : l'oracle du Memento. La partie sait se sauvegarder dans un objet <b>opaque</b>
 * et se restaurer ; l'historique le garde sans le lire et annule ; le coup inverse, lui, ne
 * suffit pas dès que le coup a perdu de l'information.
 */
class MementoTest {

    @Test
    void capturerPuisRestaurerRendLEtatIntact() {
        Partie partie = Partie.standard();
        partie.jouer(new Coup.PoserBallon(Position.of("d5")));
        String plateau = partie.plateauEnTexte();
        Partie.Sauvegarde avant = partie.capturer();

        partie.jouer(new Coup.Deplacement(Position.of("d1"), Position.of("d5")));   // la dame prend le ballon
        partie.jouer(new Coup.Deplacement(Position.of("a8"), Position.of("a5")));
        partie.jouer(new Coup.Tir(Position.of("d5"), Direction.NORD_OUEST));         // c6, b7, a8, le coin : but (J17)
        assertEquals(1, partie.score(Couleur.BLEUS));

        partie.restaurer(avant);
        assertEquals(plateau, partie.plateauEnTexte());
        assertEquals(0, partie.score(Couleur.BLEUS));
        assertEquals(Couleur.BLEUS, partie.trait());
        assertEquals(1, partie.journal().size(), "le journal revient à sa longueur d'alors");
    }

    @Test
    void laSauvegardeEstOpaque() {
        Class<?> s = Partie.Sauvegarde.class;
        assertTrue(Arrays.stream(s.getDeclaredFields()).allMatch(f -> Modifier.isPrivate(f.getModifiers())),
                "tous les champs privés");
        assertEquals(0, Arrays.stream(s.getDeclaredMethods()).filter(m -> Modifier.isPublic(m.getModifiers())).count(),
                "aucune méthode publique : personne ne lit l'intérieur");
        for (Constructor<?> c : s.getDeclaredConstructors()) {
            assertFalse(Modifier.isPublic(c.getModifiers()), "constructeur non public : seule la partie en fabrique");
        }
    }

    @Test
    void leCoupInverseNeSuffitPasQuandLeCoupAPrisUnBallonLibre() {
        Plateau plateau = new Plateau(8);
        Piece tour = new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT");
        plateau.placer(tour, Position.of("d1"));
        plateau.poserBallonLibre(Position.of("d5"));
        Partie partie = new Partie(plateau, Regles.duJeuDeBase(), Couleur.BLEUS);
        Coup d1d5 = new Coup.Deplacement(Position.of("d1"), Position.of("d5"));

        Partie.Sauvegarde avant = partie.capturer();
        assertTrue(partie.jouer(d1d5).accepte());
        assertTrue(plateau.porteuse().isPresent(), "la tour a ramassé le ballon (J5)");

        d1d5.annuler(partie);                                   // le coup inverse
        assertTrue(plateau.pieceEn(Position.of("d1")).isPresent(), "la pièce est revenue");
        assertFalse(plateau.ballonEstLibre(), "mais le ballon n'est PAS redevenu libre : l'inverse a perdu cette information");

        partie.restaurer(avant);                                 // la sauvegarde, elle, sait
        assertTrue(plateau.ballonEstLibre());
        assertEquals(Position.of("d5"), plateau.positionDuBallon().orElseThrow());
    }

    @Test
    void troisCoupsTroisAnnulationsLaPartieEstRevenueAuDepart() {
        Partie partie = Partie.standard();
        String depart = partie.plateauEnTexte();
        Historique historique = new Historique(partie);
        assertTrue(historique.jouer(new Coup.PoserBallon(Position.of("d4"))).accepte());
        assertTrue(historique.jouer(new Coup.Deplacement(Position.of("d1"), Position.of("d4"))).accepte());
        assertFalse(historique.jouer(new Coup.Deplacement(Position.of("a1"), Position.of("c3"))).accepte(), "refusé : rien à annuler");
        assertTrue(historique.jouer(new Coup.Deplacement(Position.of("a8"), Position.of("a5"))).accepte());
        assertEquals(3, historique.carnet().size());

        assertTrue(historique.annuler());
        assertTrue(historique.annuler());
        assertTrue(historique.annuler());
        assertFalse(historique.annuler(), "plus rien à annuler");
        assertEquals(depart, partie.plateauEnTexte());
        assertEquals(Couleur.BLEUS, partie.trait());
        assertEquals(0, partie.journal().size());
        assertEquals(0, historique.carnet().size());
    }
}
