package chessball;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** S09, « Le match » : l'application tourne, et ce qu'elle a joué se rejoue à l'identique. */
class MatchTest {

    @Test
    void leMatchSeTermineSeul() {
        Partie partie = Main.jouerUnMatch(Adversaires.presse(1), Adversaires.auHasard(2), 300);
        assertTrue(partie.journal().size() <= 300);
        assertTrue(partie.score(Couleur.BLEUS) + partie.score(Couleur.ROUGES) > 0, "au moins un but en trois cents demi-coups");
    }

    @Test
    void rejouerLeJournalDuMatchRedonneLeMemeScore() {
        Partie match = Main.jouerUnMatch(Adversaires.presse(7), Adversaires.auHasard(11), 200);
        Partie rejouee = GenerateurDeCoupsTest.rejouer(match);
        assertEquals(match.plateauEnTexte(), rejouee.plateauEnTexte());
        assertEquals(match.score(Couleur.BLEUS), rejouee.score(Couleur.BLEUS));
        assertEquals(match.score(Couleur.ROUGES), rejouee.score(Couleur.ROUGES));
    }

    @Test
    void aGraineEgaleLeMatchEstLeMeme() {
        Partie a = Main.jouerUnMatch(Adversaires.presse(3), Adversaires.auHasard(4), 100);
        Partie b = Main.jouerUnMatch(Adversaires.presse(3), Adversaires.auHasard(4), 100);
        assertEquals(a.journal(), b.journal(), "le hasard est à graine : un match est rejouable, comme un rapport de MiniCheck");
    }
}
