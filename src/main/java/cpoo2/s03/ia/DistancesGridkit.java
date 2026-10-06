package cpoo2.s03.ia;

import java.util.OptionalInt;

import cpoo2.jeu.Plateau;
import cpoo2.jeu.Position;

/**
 * S03, exercice 1 : l'<b>Adaptateur</b>. La bibliothèque tierce {@link gridkit.GridPaths} sait
 * calculer un plus court chemin sur une grille, obstacles compris. Elle ne parle pas notre
 * interface {@link Distances} : une grille de booléens au lieu d'un {@link Plateau}, des
 * {@code (row, col)} comptés depuis la ligne du haut au lieu de {@link Position}, et
 * {@code -1} au lieu d'un résultat vide.
 *
 * <p>Rôles : cible = {@link Distances}, adapté = {@link gridkit.GridPaths}, client =
 * {@link IAGloutonne}. On ne touche ni à l'une ni à l'autre.</p>
 */
public final class DistancesGridkit implements Distances {

    // TODO S03 Q2 : l'adapté, dans un champ privé final, réglé sur huit directions

    @Override public OptionalInt pas(Plateau plateau, Position depart, Position arrivee) {
        // TODO S03 Q3 : les coordonnées, Q4 : les obstacles, Q5 : le résultat
        throw new UnsupportedOperationException("TODO S03 Q3 à Q5");
    }
}
