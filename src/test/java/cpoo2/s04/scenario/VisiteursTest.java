package cpoo2.s04.scenario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/** S04, exercices 2 et 3 : le <b>Visiteur</b>, greffé puis utilisé trois fois. Tous rouges sur le squelette. */
class VisiteursTest {

    static final Scenario ATTAQUE = Scenario.lire(Demo.PREMIERE_ATTAQUE);

    /** Un visiteur témoin : il note quelle méthode a été appelée. */
    static final class Temoin implements VisiteurDeScenario {
        final List<String> appels = new ArrayList<>();
        @Override public void visiterDeplacer(Deplacer d) { appels.add("Deplacer"); }
        @Override public void visiterPousser(Pousser p) { appels.add("Pousser"); }
        @Override public void visiterTacler(Tacler t) { appels.add("Tacler"); }
        @Override public void visiterSauter(Sauter s) { appels.add("Sauter"); }
        @Override public void visiterVerifier(Verifier v) { appels.add("Verifier"); }
    }

    @Test
    void acceptAppelleLaBonneMethode() {
        Temoin t = new Temoin();
        for (String ligne : List.of("bleus déplacement b6-b5", "bleus poussée c5-d4", "bleus tacle d5-c4",
                                    "bleus saut c5-e3", "vérifier trait aux bleus")) {
            Scenario.instruction(ligne).accept(t);
        }
        assertEquals(List.of("Deplacer", "Pousser", "Tacler", "Sauter", "Verifier"), t.appels,
                "le type déclaré est Instruction : c'est accept qui retrouve le type réel");
    }

    @Test
    void lAfficheurRendLeTexte() {
        Afficheur a = new Afficheur();
        ATTAQUE.parcourir(a);
        assertEquals("""
                bleus poussée c5-d4
                rouges déplacement c2-c3
                vérifier ballon en e3 et trait aux bleus
                bleus poussée d4-e3
                rouges déplacement c3-c4
                bleus poussée e3-f2
                vérifier vainqueur bleus et ballon en g1""", a.texte());
        Scenario relu = Scenario.lire("départ officiel\n" + a.texte());
        assertEquals(ATTAQUE, relu, "ce que l'afficheur écrit, le lecteur le relit à l'identique");
    }

    @Test
    void lesStatistiques() {
        Statistiques s = new Statistiques();
        ATTAQUE.parcourir(s);
        assertEquals(List.of(2, 3, 0, 0, 2), List.of(s.deplacements(), s.poussees(), s.tacles(), s.sauts(), s.verifications()));
    }

    @Test
    void lExecuteurJoueUnScenarioJuste() {
        Executeur e = Executeur.executer(ATTAQUE);
        assertEquals(List.of(), e.echecs());
        assertTrue(e.partie().estTerminee(), "le ballon est en g1 : les Bleus ont marqué");
    }

    @Test
    void lExecuteurNoteChaqueEchec() {
        Executeur e = Executeur.executer(Scenario.lire("""
                départ officiel
                bleus poussée c5-d4
                bleus déplacement e5-e4
                rouges saut c2-c4
                vérifier ballon en d4
                rouges déplacement c2-c3
                vérifier trait aux bleus
                """));
        assertEquals(List.of(
                "instruction 2 (bleus déplacement e5-e4) : ce n'est pas aux bleus de jouer",
                "instruction 3 (rouges saut c2-c4) : coup refusé",
                "instruction 4 (vérifier ballon en d4) : fausse"), e.echecs());
    }
}
