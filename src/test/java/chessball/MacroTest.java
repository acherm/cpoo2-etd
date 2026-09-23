package chessball;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S05, Q5 ter : l'oracle de la macro-commande. Un engagement (poser le ballon, puis jouer) est
 * <b>un</b> coup : un seul tour, une seule ligne de journal, et l'arbitre vérifie ses parties.
 */
class MacroTest {

    static final Coup ENGAGEMENT = new Coup.Macro("engagement",
            new Coup.PoserBallon(Position.of("d4")),
            new Coup.Deplacement(Position.of("d1"), Position.of("d4")));

    @Test
    void unEngagementEstUnSeulCoup() {
        Partie partie = Partie.standard();
        assertTrue(partie.jouer(ENGAGEMENT).accepte());
        assertEquals(1, partie.journal().size(), "un seul coup au journal");
        assertEquals(Couleur.ROUGES, partie.trait(), "le trait a changé une fois");
        assertEquals("bD", partie.plateau().porteuse().orElseThrow().nom(), "la dame est arrivée sur le ballon");
        assertEquals("engagement[engage@d4 + d1-d4]", ENGAGEMENT.toString());
    }

    @Test
    void uneMacroDontUnePartieEstIllegaleEstRefuseeEnEntier() {
        Partie partie = Partie.standard();
        String avant = partie.plateauEnTexte();
        Coup mauvais = new Coup.Macro("engagement",
                new Coup.PoserBallon(Position.of("d4")),
                new Coup.Deplacement(Position.of("d1"), Position.of("e4")));   // la dame ne va pas en e4 depuis d1
        Verdict v = partie.jouer(mauvais);
        assertFalse(v.accepte());
        assertEquals(avant, partie.plateauEnTexte(), "rien n'a bougé, pas même le ballon posé par la première partie");
        assertTrue(partie.plateau().positionDuBallon().isEmpty());
        assertEquals(0, partie.journal().size());
    }

    @Test
    void uneMacroSAnnuleEnOrdreInverse() {
        Partie partie = Partie.standard();
        Historique historique = new Historique(partie);
        assertTrue(historique.jouer(ENGAGEMENT).accepte());
        assertTrue(historique.annuler());
        assertTrue(partie.plateau().positionDuBallon().isEmpty(), "plus de ballon en jeu");
        assertTrue(partie.plateau().pieceEn(Position.of("d1")).isPresent());
    }
}
