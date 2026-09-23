package chessball;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Objet actif (séance 9, Q11 bis) : l'IA ne bloque plus l'appelant. L'appel part dans un autre
 * fil et rend tout de suite un <em>jeton</em>, un {@link CompletableFuture} (java.util.concurrent,
 * Java 8). Si la réponse n'arrive pas dans le délai, on joue le coup de repli : le match continue.
 */
public final class AdversaireAsynchrone implements Function<Partie, Coup>, AutoCloseable {

    private final Function<Partie, Coup> reflechi;
    private final Function<Partie, Coup> repli;
    private final long delaiMs;
    private final ExecutorService fil = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "ia-chessball");
        t.setDaemon(true);                       // un fil oublié ne retient pas la JVM
        return t;
    });

    public AdversaireAsynchrone(Function<Partie, Coup> reflechi, Function<Partie, Coup> repli, long delaiMs) {
        this.reflechi = reflechi;
        this.repli = repli;
        this.delaiMs = delaiMs;
    }

    /**
     * Le jeton : l'appelant peut faire autre chose en attendant (afficher, animer, écouter).
     * TODO S09, Q11 bis : soumettre {@code reflechi.apply(partie)} au fil {@link #fil} et rendre le futur
     * (regardez {@link CompletableFuture#supplyAsync(java.util.function.Supplier, java.util.concurrent.Executor)}).
     */
    public CompletableFuture<Coup> proposer(Partie partie) {
        throw new UnsupportedOperationException("TODO S09 Q11 bis");
    }

    /**
     * Le coup réfléchi s'il arrive à temps, sinon le coup de repli. L'appelant ne voit pas la différence.
     * TODO S09, Q11 bis : calculer le coup de repli, puis attendre le jeton au plus {@link #delaiMs}
     * millisecondes ({@code completeOnTimeout}, Java 9, et {@link TimeUnit#MILLISECONDS}).
     */
    @Override public Coup apply(Partie partie) {
        throw new UnsupportedOperationException("TODO S09 Q11 bis");
    }

    /** Un objet actif tient un fil : il se ferme. Sans cela, l'exécuteur retient la JVM. TODO S09, Q11 bis. */
    @Override public void close() {
        throw new UnsupportedOperationException("TODO S09 Q11 bis");
    }
}
