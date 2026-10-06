package cpoo2.s04.scenario;

import cpoo2.jeu.Partie;

/**
 * Une condition du langage de scénario, celle qui suit {@code vérifier}. S04, exercice 1 :
 * l'<b>Interpréteur</b>. Chaque nœud sait s'évaluer sur une partie, le contexte.
 *
 * <pre>
 * condition := facteur ("et" facteur)*
 * facteur   := "non" facteur | "ballon en" CASE | "trait aux" ÉQUIPE | "vainqueur" ÉQUIPE
 * </pre>
 */
public sealed interface Condition permits BallonEn, TraitAux, Vainqueur, Et, Non {

    /** Vrai si la condition est satisfaite par cette partie, telle qu'elle est maintenant. */
    boolean evaluer(Partie partie);
}
