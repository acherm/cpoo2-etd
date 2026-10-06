package chessball;

import java.util.List;

/**
 * Ce qu'un acteur peut demander à une partie : jouer, et regarder. La {@link Partie} l'implémente.
 *
 * <p>Les accesseurs de lecture sont provisoires : la séance 7 les remplacera par un objet de
 * transfert unique, ce qui sort de la partie vers l'affichage et le réseau.</p>
 */
public interface Jeu {

    Verdict jouer(Coup coup);

    Couleur trait();

    int score(Couleur couleur);

    /** Les coups acceptés, dans l'ordre. */
    List<Coup> journal();

    String plateauEnTexte();
}
