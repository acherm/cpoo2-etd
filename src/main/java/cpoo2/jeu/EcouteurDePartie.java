package cpoo2.jeu;

import java.util.Optional;

/** Ce que la partie dit à ceux qui l'écoutent (S04, exercice 5 : l'Observateur). */
public interface EcouteurDePartie {

    /** Un coup vient d'être joué, par {@code equipe}. */
    void coupJoue(Couleur equipe, Coup coup);

    /** La partie vient de se terminer : un vainqueur, ou vide si elle est nulle. */
    default void partieTerminee(Optional<Couleur> vainqueur) {}
}
