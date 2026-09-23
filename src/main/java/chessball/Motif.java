package chessball;

import java.util.List;
import java.util.SortedSet;

/**
 * Le comportement de déplacement d'une pièce, isolé de la pièce elle-même.
 * Une pièce ne « sait » plus bouger : elle délègue.
 */
public interface Motif {

    /** Cases où la pièce peut se rendre depuis {@code depart} (cases vides, obstacles respectés — J6). */
    SortedSet<Position> accessibles(Plateau plateau, Position depart);

    /**
     * Vrai si {@code cible} est sur une trajectoire du motif depuis {@code depart}, chemin
     * intermédiaire libre — que la cible soit occupée ou non. Sert aux passes et aux tirs.
     */
    boolean vise(Plateau plateau, Position depart, Position cible);

    /** Directions le long desquelles la pièce glisse ; vide pour un sauteur (le cavalier ne tire pas). */
    default List<Direction> directions() { return List.of(); }
}
