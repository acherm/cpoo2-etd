package cpoo2.s03.ia;

import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;

/**
 * Une IA : on lui montre la partie, elle propose un coup. Elle ne le joue pas : c'est la
 * {@link Rencontre} qui joue le coup proposé.
 */
@FunctionalInterface
public interface IA {

    Coup choisirCoup(Jeu jeu);
}
