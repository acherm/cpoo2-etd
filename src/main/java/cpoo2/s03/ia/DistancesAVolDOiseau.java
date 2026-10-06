package cpoo2.s03.ia;

import java.util.OptionalInt;

import cpoo2.jeu.Plateau;
import cpoo2.jeu.Position;

/**
 * La version maison, à vol d'oiseau : comme si le plateau était vide. En huit directions, une
 * diagonale compte pour un pas, donc c'est le plus grand des deux écarts, en colonnes et en
 * rangées. Juste sur un terrain dégagé, fausse dès qu'un obstacle barre le chemin : elle ne lit
 * même pas le plateau.
 */
public final class DistancesAVolDOiseau implements Distances {

    @Override public OptionalInt pas(Plateau plateau, Position depart, Position arrivee) {
        return OptionalInt.of(Math.max(Math.abs(arrivee.colonne() - depart.colonne()),
                                       Math.abs(arrivee.rangee() - depart.rangee())));
    }
}
