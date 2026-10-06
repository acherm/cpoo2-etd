package cpoo2.jeu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import cpoo2.s03.ia.Exemples;

/** Le noyau fourni de S03 : ces tests passent déjà, ils disent ce qu'on peut attendre de lui. */
class NoyauTest {

    @Test
    void laPositionDeDepartDeLaBoite() {
        Plateau p = Plateau.officiel();
        assertEquals(10, p.pieces().size(), "cinq pièces par camp");
        assertEquals(Position.of("d4"), p.ballon());
        assertFalse(p.estLibre(Position.of("d4")), "la case du ballon n'est pas libre");
        assertTrue(p.estLibre(Position.of("d3")));
        assertEquals(new Piece(Couleur.BLEUS, TypePiece.ATTAQUANT), p.pieceEn(Position.of("c5")).orElseThrow());
        assertEquals(25, PartieDEssai.depart().coupsLegaux().size());
    }

    @Test
    void laPousseeEnvoieLeBallonUneCasePlusLoin() {
        Coup.Poussee p = (Coup.Poussee) Coup.lire("poussée c5-d4");
        assertEquals(Direction.SUD_EST, p.direction());
        assertEquals(Position.of("e3"), p.arriveeDuBallon().orElseThrow());
        assertEquals(Position.of("c3"), Coup.lire("saut e5-c3").arrivee(), "le saut franchit une case");
    }

    @Test
    void laNotationFaitLAllerRetour() {
        for (PartieDEssai partie : List.of(PartieDEssai.depart(), Exemples.leMur(), Exemples.leBallonDevantSonBut())) {
            for (Coup c : partie.coupsLegaux()) {
                assertEquals(c, Coup.lire(c.toString()));
                assertEquals(partie.trait(), partie.plateau().pieceEn(c.origine()).orElseThrow().couleur(), c + " : une pièce du trait");
            }
        }
    }

    @Test
    void unePartieDEssaiNAccepteQueSesCoupsLegaux() {
        PartieDEssai partie = PartieDEssai.depart();
        assertFalse(partie.jouer(Coup.lire("déplacement c5-d4")), "d4 est la case du ballon");
        assertTrue(partie.jouer(Coup.lire("poussée c5-d4")));
        assertEquals(List.of(Coup.lire("poussée c5-d4")), partie.coupsJoues());
    }
}
