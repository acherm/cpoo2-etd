package cpoo2.s04.scenario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cpoo2.jeu.Coup;
import cpoo2.jeu.Couleur;
import cpoo2.jeu.Partie;
import cpoo2.jeu.Position;

/**
 * S04, exercice 1 : l'<b>Interpréteur</b>. L'évaluation vit dans les nœuds, la partie est le
 * contexte. Tous rouges sur le squelette.
 */
class InterpreteurTest {

    static Partie apresUnePoussee() {
        Partie p = Partie.officielle();
        p.jouer(Coup.lire("poussée c5-d4"));            // le ballon part en e3, les Rouges ont le trait
        return p;
    }

    @Test
    void lesTerminaux() {
        Partie p = apresUnePoussee();
        assertTrue(new BallonEn(Position.of("e3")).evaluer(p));
        assertFalse(new BallonEn(Position.of("d4")).evaluer(p));
        assertTrue(new TraitAux(Couleur.ROUGES).evaluer(p));
        assertFalse(new TraitAux(Couleur.BLEUS).evaluer(p));
        assertFalse(new Vainqueur(Couleur.BLEUS).evaluer(p), "personne n'a encore gagné");
    }

    @Test
    void lesNonTerminauxEvaluentLeursEnfants() {
        Partie p = apresUnePoussee();
        Condition vraie = new BallonEn(Position.of("e3"));
        Condition fausse = new TraitAux(Couleur.BLEUS);
        assertTrue(new Et(vraie, vraie).evaluer(p));
        assertFalse(new Et(vraie, fausse).evaluer(p));
        assertFalse(new Et(fausse, vraie).evaluer(p));
        assertTrue(new Non(fausse).evaluer(p));
        assertFalse(new Non(new Non(fausse)).evaluer(p), "la récursion va jusqu'au bout");
    }

    @Test
    void uneConditionLueSEvalue() {
        Partie p = apresUnePoussee();
        assertTrue(Scenario.condition(java.util.List.of("ballon", "en", "e3", "et", "non", "trait", "aux", "bleus")).evaluer(p));
        assertFalse(Scenario.condition(java.util.List.of("vainqueur", "rouges", "et", "ballon", "en", "e3")).evaluer(p));
    }

    @Test
    void leVainqueurApresUnBut() {
        Partie p = Executeur.executer(Scenario.lire(Demo.PREMIERE_ATTAQUE)).partie();
        assertTrue(new Vainqueur(Couleur.BLEUS).evaluer(p));
        assertFalse(new Vainqueur(Couleur.ROUGES).evaluer(p));
    }
}
