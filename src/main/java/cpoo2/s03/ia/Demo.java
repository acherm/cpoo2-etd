package cpoo2.s03.ia;

import java.util.Random;

import cpoo2.jeu.Coup;
import cpoo2.jeu.PartieDEssai;
import cpoo2.jeu.Position;

/**
 * La séance en une exécution. Chaque partie affiche « pas encore écrit » tant que son exercice
 * ne l'est pas.
 *
 * <pre>{@code
 * mvn -q compile
 * java -cp target/classes cpoo2.s03.ia.Demo
 * }</pre>
 */
public final class Demo {

    public static void main(String[] args) {
        PartieDEssai depart = PartieDEssai.depart();
        System.out.println("ChessBall, la position de départ de la boîte, Bleus au trait :");
        System.out.print(depart.plateau().enTexte());
        System.out.println(depart.coupsLegaux().size() + " coups légaux : " + depart.coupsLegaux());

        essayer("Exercice 1, Adaptateur : la bibliothèque de grilles", () -> {
            PartieDEssai mur = Exemples.leMur();
            System.out.print(mur.plateau().enTexte());
            Position f6 = Position.of("f6");
            Position d6 = Position.of("d6");
            System.out.println("de f6 à d6, à vol d'oiseau : " + new DistancesAVolDOiseau().pas(mur.plateau(), f6, d6));
            System.out.println("de f6 à d6, avec gridkit   : " + new DistancesGridkit().pas(mur.plateau(), f6, d6));
            System.out.println("l'IA gloutonne à vol d'oiseau joue : " + new IAGloutonne(new DistancesAVolDOiseau()).choisirCoup(mur));
            System.out.println("l'IA gloutonne avec gridkit joue   : " + new IAGloutonne(new DistancesGridkit()).choisirCoup(mur));
        });

        essayer("Exercice 2, Décorateur : les services empilés sur une IA", () -> {
            PartieDEssai devantSonBut = Exemples.leBallonDevantSonBut();
            System.out.print(devantSonBut.plateau().enTexte());
            IA pousseChezElle = jeu -> Coup.lire("poussée d4-d5");
            IA ia = new IAJournalisee(new SansButContreSonCamp(pousseChezElle), ligne -> System.out.println("  journal : " + ligne));
            System.out.println("joué : " + ia.choisirCoup(devantSonBut));
            IA aleatoireProtegee = new SansButContreSonCamp(new IAAleatoire(new Random(1)));
            System.out.println("l'aléatoire protégée joue : " + aleatoireProtegee.choisirCoup(devantSonBut));
        });

        essayer("Exercice 3, Proxy : la partie en lecture seule", () -> {
            Rencontre honnete = new Rencontre(PartieDEssai.depart(), new IAAleatoire(new Random(1)), new IAAleatoire(new Random(2)));
            System.out.println("une IA honnête joue : " + honnete.tourSuivant());
            PartieDEssai partie = PartieDEssai.depart();
            try {
                new Rencontre(partie, new IATricheuse(), new IAAleatoire(new Random(2))).tourSuivant();
                System.out.println("la tricheuse est passée : " + partie.coupsJoues() + " joués pour un tour");
            } catch (UnsupportedOperationException e) {
                if (e.getMessage() != null && e.getMessage().startsWith("TODO")) throw e;
                System.out.println("la tricheuse est prise : " + e.getMessage() + " ; coups joués : " + partie.coupsJoues());
            }
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
}
