package cpoo2.s02.enumeration;

import java.util.Optional;

/** S02, exercice 8 (hors séance). Les quatre couleurs d'un jeu de cartes. */
public enum ColourCard {
	SPADE, CLUB, HEART, DIAMOND;

	/**
	 * TODO Q25 — obtenir une couleur à partir d'une chaîne correspondant à son nom.
	 *
	 * <p>Rappels : {@code name()} donne le nom d'un élément ({@code SPADE.name()}) ;
	 * {@code values()} renvoie un tableau de tous les éléments.
	 *
	 * <p>Q26 : comparez trois solutions — {@code valueOf(nom)}, une boucle sur
	 * {@code values()}, une table statique. Que se passe-t-il si le nom est
	 * inconnu, et que coûte un appel ?
	 */
	public static Optional<ColourCard> get(final String nom) {
		return Optional.empty();
	}
}
