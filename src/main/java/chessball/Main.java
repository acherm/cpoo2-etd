package chessball;

import java.util.function.Function;

/**
 * L'application : un match complet, IA contre IA, sur le jeu de base. Se termine seul : au premier
 * camp à trois buts, ou après {@code maxDemiCoups} coups.
 *
 * <pre>
 *   java -cp target/classes chessball.Main                     graines 1 et 2, 300 demi-coups au plus
 *   java -cp target/classes chessball.Main 7 11 200
 *   java -cp target/classes chessball.Main heuristique bt      S06 contre S05, une fois écrits
 * </pre>
 *
 * <p>Par défaut les Bleus jouent « pressé » (tirer dès que possible) et les Rouges au hasard :
 * deux adversaires fournis. Les noms {@code heuristique} (séance 6) et {@code bt} (séance 5)
 * branchent les vôtres par {@link Adversaires#parNom}. L'IA lente sous délai (séance 9) enveloppe
 * n'importe lequel d'entre eux.</p>
 */
public final class Main {

    public static final int BUTS_POUR_GAGNER = 3;

    private Main() {}

    public static void main(String[] args) {
        // java chessball.Main [bleus rouges] [graineBleus graineRouges maxDemiCoups]
        // bleus et rouges : hasard, presse (fournis), heuristique (S06), bt (S05)
        int i = 0;
        String nomBleus = "presse", nomRouges = "hasard";
        if (args.length >= 2 && !estUnNombre(args[0])) { nomBleus = args[0]; nomRouges = args[1]; i = 2; }
        long graineBleus = args.length > i ? Long.parseLong(args[i]) : 1L;
        long graineRouges = args.length > i + 1 ? Long.parseLong(args[i + 1]) : 2L;
        int maxDemiCoups = args.length > i + 2 ? Integer.parseInt(args[i + 2]) : 300;

        System.out.println("Bleus : " + nomBleus + ", Rouges : " + nomRouges);
        Partie partie;
        try {
            partie = jouerUnMatch(Adversaires.parNom(nomBleus, graineBleus),
                    Adversaires.parNom(nomRouges, graineRouges), maxDemiCoups);
        } catch (UnsupportedOperationException e) {
            // un adversaire d'une séance pas encore faite : son squelette lève un TODO
            System.out.println("Pas encore : " + e.getMessage()
                    + " (heuristique arrive en S06, bt en S05 ; en attendant : presse, hasard)");
            return;
        }

        System.out.println("Journal (" + partie.journal().size() + " coups acceptés) :");
        StringBuilder ligne = new StringBuilder();
        int n = 0;
        for (Coup coup : partie.journal()) {
            ligne.append(String.format("%-10s", coup));
            if (++n % 8 == 0) { System.out.println(ligne); ligne.setLength(0); }
        }
        if (ligne.length() > 0) System.out.println(ligne);
        System.out.println();
        System.out.println(partie.plateauEnTexte());
        System.out.printf("Score final : Bleus %d, Rouges %d%n",
                partie.score(Couleur.BLEUS), partie.score(Couleur.ROUGES));
    }

    private static boolean estUnNombre(String s) {
        try { Long.parseLong(s); return true; } catch (NumberFormatException e) { return false; }
    }

    /** Joue un match sur une partie standard neuve et la rend, journal compris. */
    public static Partie jouerUnMatch(Function<Partie, Coup> bleus, Function<Partie, Coup> rouges, int maxDemiCoups) {
        Partie partie = Partie.standard();
        jouer(partie, bleus, rouges, maxDemiCoups);
        return partie;
    }

    /** Fait jouer les deux adversaires sur une partie existante, jusqu'au terme. */
    public static void jouer(Partie partie, Function<Partie, Coup> bleus, Function<Partie, Coup> rouges, int maxDemiCoups) {
        for (int i = 0; i < maxDemiCoups; i++) {
            if (partie.score(Couleur.BLEUS) >= BUTS_POUR_GAGNER || partie.score(Couleur.ROUGES) >= BUTS_POUR_GAGNER) return;
            Function<Partie, Coup> auTrait = partie.trait() == Couleur.BLEUS ? bleus : rouges;
            Coup coup = auTrait.apply(partie);
            Verdict verdict = partie.jouer(coup);
            if (!verdict.accepte()) {
                throw new IllegalStateException("l'IA a proposé un coup refusé : " + coup + " (" + verdict.motif() + ")");
            }
        }
    }
}
