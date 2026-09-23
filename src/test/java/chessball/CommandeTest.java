package chessball;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S05, Q4 bis : l'oracle de la Commande. Un coup est un objet qui <b>sait s'exécuter</b> ; la
 * partie ne fait plus que l'arbitrer et l'appeler ; un carnet de coups se rejoue.
 */
class CommandeTest {

    @Test
    void unDeplacementSaitSExecuterSansArbitrage() {
        Partie partie = Partie.standard();
        new Coup.Deplacement(Position.of("a1"), Position.of("c3")).executer(partie);   // illégal, mais on n'arbitre pas ici
        assertTrue(partie.plateau().pieceEn(Position.of("c3")).isPresent(), "le coup a été appliqué tel quel");
        assertTrue(partie.plateau().estVide(Position.of("a1")));
        assertEquals(0, partie.journal().size(), "exécuter n'est pas jouer : pas de journal, pas de trait");
        assertEquals(Couleur.BLEUS, partie.trait());
    }

    @Test
    void unTirSaitMarquer() {
        Plateau plateau = new Plateau(8);
        Piece tour = new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT");
        plateau.placer(tour, Position.of("d4"));
        plateau.donnerBallon(tour);
        Partie partie = new Partie(plateau, Regles.duJeuDeBase(), Couleur.BLEUS);
        new Coup.Tir(Position.of("d4"), Direction.NORD).executer(partie);
        assertEquals(1, partie.score(Couleur.BLEUS), "colonne libre jusqu'à la rangée 8 : but");
        assertTrue(plateau.positionDuBallon().isEmpty(), "le ballon est sorti, on réengagera");
    }

    @Test
    void arbitrerNeChangeRien() {
        Partie partie = Partie.standard();
        String avant = partie.plateauEnTexte();
        assertTrue(partie.arbitrer(new Coup.Deplacement(Position.of("a1"), Position.of("a4"))).accepte());
        assertFalse(partie.arbitrer(new Coup.Deplacement(Position.of("a1"), Position.of("c3"))).accepte());
        assertEquals(avant, partie.plateauEnTexte());
        assertEquals(Couleur.BLEUS, partie.trait());
    }

    @Test
    void unCarnetSeRejoueEtRedonneLeMemeMatch() {
        Partie originale = Partie.standard();
        List<Coup> carnet = List.of(
                new Coup.PoserBallon(Position.of("d5")),
                new Coup.Deplacement(Position.of("d1"), Position.of("d5")),   // la dame prend le ballon (J5)
                new Coup.Deplacement(Position.of("a8"), Position.of("a5")),   // la tour rouge libère a8
                new Coup.Tir(Position.of("d5"), Direction.NORD_OUEST));       // c6, b7, a8, puis le coin : but (J17)
        for (Coup c : carnet) assertTrue(originale.jouer(c).accepte(), c.toString());
        assertEquals(1, originale.score(Couleur.BLEUS));

        Partie rejouee = Partie.standard();
        rejouee.rejouer(originale.journal());
        assertEquals(originale.plateauEnTexte(), rejouee.plateauEnTexte());
        assertEquals(originale.score(Couleur.BLEUS), rejouee.score(Couleur.BLEUS));
        assertEquals(originale.trait(), rejouee.trait());
        assertEquals(originale.journal(), rejouee.journal(), "les coups sont des valeurs : le carnet est comparable");
    }
}
