package chessball;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Programmation réactive (séance 9, Q11 ter) : les coups d'une partie deviennent un <em>flux</em>
 * auquel on s'abonne. La partie ne connaît qu'un {@link EcouteurDePartie} de plus ; ce que les
 * abonnés en font (compter, filtrer, afficher, à leur rythme) ne la regarde pas.
 *
 * <p>Le flux est un {@link SubmissionPublisher} (java.util.concurrent, Java 9) : la contre-pression
 * est fournie, {@code submit} bloque quand un abonné n'a pas encore demandé la suite.</p>
 */
public final class FluxDeCoups implements EcouteurDePartie, AutoCloseable {

    /** Ce qui circule : un coup tenté et son verdict. */
    public record Evenement(Coup coup, Verdict verdict) {}

    private final SubmissionPublisher<Evenement> flux;

    /** Un flux avec la capacité de tampon par défaut (256 éléments par abonné). */
    public FluxDeCoups() { this.flux = new SubmissionPublisher<>(); }

    /** Un flux à petit tampon, pour voir la contre-pression à l'œuvre. */
    public FluxDeCoups(int capaciteDuTampon) {
        this.flux = new SubmissionPublisher<>(java.util.concurrent.ForkJoinPool.commonPool(), capaciteDuTampon);
    }

    public void abonner(Flow.Subscriber<? super Evenement> abonne) { flux.subscribe(abonne); }

    /** TODO S09, Q11 ter : publier l'événement sur le flux (pas l'afficher, pas le compter : publier). */
    @Override public void surCoupTente(Coup coup, Verdict verdict) {
        throw new UnsupportedOperationException("TODO S09 Q11 ter");
    }

    /** Fin du flux : chaque abonné reçoit {@code onComplete}. TODO S09, Q11 ter. */
    @Override public void close() {
        throw new UnsupportedOperationException("TODO S09 Q11 ter");
    }

    /**
     * Un abonné réutilisable : il garde ce qui passe son filtre et redemande {@code n} éléments à
     * la fois. Oublier de redemander, c'est arrêter le flux sans aucune erreur.
     */
    public static final class AbonneFiltrant implements Flow.Subscriber<Evenement> {

        private final Predicate<Evenement> filtre;
        private final long parLot;
        private final List<Evenement> recus = new ArrayList<>();
        private final java.util.concurrent.CountDownLatch fin = new java.util.concurrent.CountDownLatch(1);
        private Flow.Subscription abonnement;
        private long recusDansLeLot;

        public AbonneFiltrant(Predicate<Evenement> filtre, long parLot) {
            this.filtre = filtre;
            this.parLot = parLot;
        }

        public static AbonneFiltrant tout() { return new AbonneFiltrant(e -> true, 16); }

        /** TODO S09, Q11 ter : garder l'abonnement, et demander un premier lot. Sans cette demande, rien n'arrive jamais. */
        @Override public void onSubscribe(Flow.Subscription abonnement) {
            this.abonnement = abonnement;
            throw new UnsupportedOperationException("TODO S09 Q11 ter");
        }

        /**
         * TODO S09, Q11 ter : garder l'événement s'il passe le filtre (sous {@code synchronized (recus)}, le flux
         * livre depuis un autre fil), puis, quand le lot est consommé, en redemander un ({@code recusDansLeLot}).
         */
        @Override public void onNext(Evenement evenement) {
            throw new UnsupportedOperationException("TODO S09 Q11 ter");
        }

        @Override public void onError(Throwable erreur) { fin.countDown(); }

        @Override public void onComplete() { fin.countDown(); }

        public List<Evenement> recus() {
            synchronized (recus) { return List.copyOf(recus); }
        }

        /** Attend la fin du flux (au plus {@code millisecondes}) : le flux est asynchrone. */
        public boolean attendreLaFin(long millisecondes) throws InterruptedException {
            return fin.await(millisecondes, TimeUnit.MILLISECONDS);
        }
    }
}
