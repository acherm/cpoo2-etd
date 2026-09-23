package chessball;

import java.util.Random;

/** Qui engage ? La réponse vient de l'extérieur : un test doit pouvoir la fixer (J1). */
@FunctionalInterface
public interface TirageAuSort {

    Couleur equipeQuiEngage();

    /** Convention du cahier des charges : {@code nextBoolean() == true} donne les Bleus. */
    static TirageAuSort aleatoire(Random random) {
        // TODO S06 Q9 ter : une lambda, le Random est REÇU, jamais créé ici
        throw new UnsupportedOperationException("TODO S06 Q9 ter");
    }

    static TirageAuSort truque(Couleur toujours) {
        // TODO S06 Q9 ter : le tirage des tests, qui ne tire jamais au sort
        throw new UnsupportedOperationException("TODO S06 Q9 ter");
    }
}
