package chessball;

import java.util.function.Function;

/**
 * Une IA qui « réfléchit » : elle enveloppe une autre IA et lui fait prendre son temps. C'est
 * l'adversaire qui ne rend pas la main de la séance 9 (Objet actif).
 */
public final class AdversaireLent implements Function<Partie, Coup> {

    private final Function<Partie, Coup> enveloppe;
    private final long millisecondes;

    public AdversaireLent(Function<Partie, Coup> enveloppe, long millisecondes) {
        this.enveloppe = enveloppe;
        this.millisecondes = millisecondes;
    }

    @Override public Coup apply(Partie partie) {
        try {
            Thread.sleep(millisecondes);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("réflexion interrompue", e);
        }
        return enveloppe.apply(partie);
    }
}
