package cpoo2.jeu;

import java.util.List;

/**
 * Une partie, vue de l'extérieur : ce que l'arbitre expose, et ce qu'on remet à une IA
 * pour qu'elle choisisse son coup.
 */
public interface Jeu {

    /** L'équipe qui doit jouer. */
    Couleur trait();

    /** La position, figée : la lire ne change rien. */
    Plateau plateau();

    /** Les coups légaux de l'équipe au trait. */
    List<Coup> coupsLegaux();

    /**
     * Joue le coup s'il est légal.
     *
     * @return vrai si le coup est accepté, faux s'il est refusé
     * @throws UnsupportedOperationException si ce jeu ne se joue pas : opération facultative,
     *         comme {@code List.add} sur une liste non modifiable
     */
    boolean jouer(Coup coup);
}
