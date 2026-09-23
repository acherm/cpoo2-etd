package chessball;

/** Réponse de l'arbitrage à une tentative de coup : accepté, ou refusé avec un motif explicite. */
public record Verdict(boolean accepte, String motif) {

    private static final Verdict OK = new Verdict(true, "");

    public static Verdict ok() { return OK; }

    public static Verdict refus(String motif) { return new Verdict(false, motif); }
}
