package cpoo2.s04.scenario;

import cpoo2.jeu.Partie;
import cpoo2.s04.observateur.CompteurDeCoups;
import cpoo2.s04.observateur.JournalDesCoups;

/**
 * La séance en une exécution : un scénario, affiché, compté, joué, et une partie écoutée.
 * L'arbitre est dans {@code lib/} : Maven le met dans le chemin de classes.
 *
 * <pre>
 * mvn -q compile exec:java -Dexec.mainClass=cpoo2.s04.scenario.Demo
 * </pre>
 */
public final class Demo {

    public static final String PREMIERE_ATTAQUE = """
            # la première attaque bleue : trois poussées, un but
            départ officiel
            bleus poussée c5-d4
            rouges déplacement c2-c3
            vérifier ballon en e3 et trait aux bleus
            bleus poussée d4-e3
            rouges déplacement c3-c4
            bleus poussée e3-f2
            vérifier vainqueur bleus et ballon en g1
            """;

    public static void main(String[] args) {
        Scenario scenario = Scenario.lire(PREMIERE_ATTAQUE);
        System.out.println(PREMIERE_ATTAQUE);
        essayer("Exercice 1 et 3, l'Interpréteur et l'exécuteur", () -> {
            Executeur e = Executeur.executer(scenario);
            System.out.println(e.echecs().isEmpty() ? "scénario joué sans échec" : "échecs : " + e.echecs());
            System.out.print(e.partie().plateau().enTexte());
        });
        essayer("Exercice 3, l'afficheur", () -> {
            Afficheur a = new Afficheur();
            scenario.parcourir(a);
            System.out.println(a.texte());
        });
        essayer("Exercice 3, les statistiques", () -> {
            Statistiques s = new Statistiques();
            scenario.parcourir(s);
            System.out.println(s.deplacements() + " déplacements, " + s.poussees() + " poussées, " + s.tacles()
                    + " tacles, " + s.sauts() + " sauts, " + s.verifications() + " vérifications");
        });
        essayer("Exercice 5, l'Observateur", () -> {
            Partie partie = new Partie(scenario.depart(), scenario.trait());
            JournalDesCoups journal = new JournalDesCoups();
            CompteurDeCoups compteur = new CompteurDeCoups();
            partie.ajouterEcouteur(journal);
            partie.ajouterEcouteur(compteur);
            scenario.parcourir(new Executeur(partie));
            journal.lignes().forEach(System.out::println);
            System.out.println(compteur.coups() + " coups joués, dont " + compteur.poussees() + " poussées");
        });
    }

    private static void essayer(String titre, Runnable partie) {
        System.out.println();
        System.out.println("== " + titre);
        try {
            partie.run();
        } catch (UnsupportedOperationException e) {
            if (e.getMessage() == null || !e.getMessage().startsWith("TODO")) {
                throw e;
            }
            System.out.println("  pas encore écrit (" + e.getMessage() + ")");
        }
    }

    private Demo() {}
}
