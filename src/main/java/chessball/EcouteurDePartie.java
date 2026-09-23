package chessball;

/**
 * Qui veut savoir ce qui se passe s'abonne. La partie ne connaît personne : elle ne connaît
 * que cette interface (séance 4, exercice 5).
 *
 * <p>Une seule méthode abstraite, donc une lambda suffit pour un abonné simple. Les autres
 * événements ont un corps par défaut : un écouteur n'implémente que ce qui l'intéresse.</p>
 */
@FunctionalInterface
public interface EcouteurDePartie {

    /** Tout coup tenté, accepté ou refusé, avec son verdict. C'est à l'abonné de trier. */
    void surCoupTente(Coup coup, Verdict verdict);

    default void surBut(Couleur marqueur) {}
}
