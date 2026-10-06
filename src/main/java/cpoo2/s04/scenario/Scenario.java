package cpoo2.s04.scenario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Plateau;
import cpoo2.jeu.Position;

/**
 * Un scénario : une position de départ, l'équipe au trait, et des instructions. Il se lit dans
 * un texte (fourni : ce n'est pas l'objet de la séance), et se parcourt avec un visiteur.
 *
 * <pre>
 * # la première attaque bleue
 * départ officiel
 * bleus poussée c5-d4
 * rouges déplacement c2-c3
 * vérifier ballon en e3 et trait aux bleus
 * </pre>
 *
 * <p>Une ligne qui commence par {@code #} est un commentaire. La première ligne utile est
 * {@code départ officiel}, ou {@code départ} suivi d'une position en une ligne
 * ({@link Plateau#enLigne()}) et de l'équipe au trait.</p>
 */
public record Scenario(Plateau depart, Couleur trait, List<Instruction> instructions) {

    public Scenario {
        instructions = List.copyOf(instructions);
    }

    /** Le visiteur passe sur chaque instruction, dans l'ordre : c'est la structure d'objets du Visiteur. */
    public void parcourir(VisiteurDeScenario visiteur) {
        for (Instruction i : instructions) {
            i.accept(visiteur);
        }
    }

    // ---------- la lecture (fournie) ----------

    public static Scenario lire(String texte) {
        List<String> lignes = new ArrayList<>();
        for (String l : texte.split("\\R")) {
            l = l.strip();
            if (!l.isEmpty() && !l.startsWith("#")) lignes.add(l);
        }
        if (lignes.isEmpty() || !lignes.get(0).startsWith("départ")) {
            throw new IllegalArgumentException("un scénario commence par « départ officiel »");
        }
        String[] d = lignes.get(0).split("\\s+");
        Plateau depart;
        Couleur trait;
        if (d.length == 2 && d[1].equals("officiel")) {
            depart = Plateau.officiel();
            trait = Couleur.BLEUS;
        } else if (d.length == 3) {
            depart = Plateau.depuisLigne(d[1]);
            trait = equipe(d[2]);
        } else {
            throw new IllegalArgumentException("départ illisible : " + lignes.get(0));
        }
        List<Instruction> instructions = new ArrayList<>();
        for (String l : lignes.subList(1, lignes.size())) {
            instructions.add(instruction(l));
        }
        return new Scenario(depart, trait, instructions);
    }

    static Instruction instruction(String ligne) {
        String[] m = ligne.split("\\s+");
        if (m[0].equals("vérifier")) {
            return new Verifier(condition(Arrays.asList(m).subList(1, m.length)));
        }
        if (m.length != 3) throw new IllegalArgumentException("ligne illisible : " + ligne);
        Couleur equipe = equipe(m[0]);
        String[] cases = m[2].split("-");
        if (cases.length != 2) throw new IllegalArgumentException("coup illisible : " + ligne);
        Position de = Position.of(cases[0]);
        Position vers = Position.of(cases[1]);
        return switch (m[1]) {
            case "déplacement" -> new Deplacer(equipe, de, vers);
            case "poussée" -> new Pousser(equipe, de, vers);
            case "tacle" -> new Tacler(equipe, de, vers);
            case "saut" -> new Sauter(equipe, de, vers);
            default -> throw new IllegalArgumentException("sorte de coup inconnue : " + m[1]);
        };
    }

    /** {@code condition := facteur ("et" facteur)*}, associatif à gauche. */
    static Condition condition(List<String> mots) {
        List<List<String>> facteurs = new ArrayList<>();
        List<String> courant = new ArrayList<>();
        for (String mot : mots) {
            if (mot.equals("et")) {
                facteurs.add(courant);
                courant = new ArrayList<>();
            } else {
                courant.add(mot);
            }
        }
        facteurs.add(courant);
        Condition c = facteur(facteurs.get(0));
        for (List<String> f : facteurs.subList(1, facteurs.size())) {
            c = new Et(c, facteur(f));
        }
        return c;
    }

    static Condition facteur(List<String> m) {
        if (!m.isEmpty() && m.get(0).equals("non")) return new Non(facteur(m.subList(1, m.size())));
        String t = String.join(" ", m);
        if (m.size() == 3 && t.startsWith("ballon en ")) return new BallonEn(Position.of(m.get(2)));
        if (m.size() == 3 && t.startsWith("trait aux ")) return new TraitAux(equipe(m.get(2)));
        if (m.size() == 2 && m.get(0).equals("vainqueur")) return new Vainqueur(equipe(m.get(1)));
        throw new IllegalArgumentException("condition illisible : " + t);
    }

    static Couleur equipe(String mot) {
        return switch (mot) {
            case "bleus" -> Couleur.BLEUS;
            case "rouges" -> Couleur.ROUGES;
            default -> throw new IllegalArgumentException("équipe attendue, bleus ou rouges : " + mot);
        };
    }

    static String nom(Couleur equipe) { return equipe == Couleur.BLEUS ? "bleus" : "rouges"; }
}
