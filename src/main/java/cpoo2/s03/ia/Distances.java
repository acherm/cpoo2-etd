package cpoo2.s03.ia;

import java.util.OptionalInt;

import cpoo2.jeu.Plateau;
import cpoo2.jeu.Position;

/**
 * En combien de déplacements une pièce posée en {@code depart} peut-elle atteindre
 * {@code arrivee} ? Un déplacement va d'une case à l'une de ses huit voisines, si elle est libre :
 * ni pièce ni ballon. La case de départ est celle de la pièce qui marche : elle ne lui barre
 * pas la route.
 *
 * <p>C'est l'interface dont l'{@link IAGloutonne} a besoin. Elle ne sait pas comment on calcule.</p>
 */
public interface Distances {

    /**
     * @return le nombre de déplacements, {@code 0} si {@code depart} et {@code arrivee} sont la
     *         même case, ou <b>vide</b> si {@code arrivee} est inaccessible : occupée, ou coupée
     *         du départ par des obstacles
     */
    OptionalInt pas(Plateau plateau, Position depart, Position arrivee);
}
