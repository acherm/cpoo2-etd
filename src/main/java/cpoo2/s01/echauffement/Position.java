package cpoo2.s01.echauffement;

/**
 * S01, partie 0 — exercice 1 : un {@code record}, et ce qu'il génère.
 *
 * <p>1.1 — Le record doit <b>refuser</b> à la construction toute coordonnée
 * hors de {@code 0..7}, et offrir la fabrique statique
 * {@code Position.de("e4")} (colonne {@code a}..{@code h}, ligne {@code 1}..{@code 8}).
 * Indice : le constructeur <b>compact</b>, {@code public Position { ... }},
 * sans {@code this.ligne = ligne} (le compilateur l'ajoute après votre bloc).
 *
 * <p>1.2 — {@link PositionTest} vérifie que {@code de("e4")} et
 * {@code new Position(3, 4)} sont égales sans que vous ayez écrit {@code equals}.
 */
public record Position(int ligne, int colonne) {

	// TODO 1.1 : le constructeur compact qui refuse les coordonnées hors plateau
	// (IllegalArgumentException). Pourquoi ici, et pas seulement dans « de » ?

	/**
	 * TODO 1.1 — fabrique statique : {@code "e4"} devient (ligne 3, colonne 4).
	 * Un format autre que lettre + chiffre est refusé (IllegalArgumentException).
	 */
	public static Position de(final String algebrique) {
		throw new UnsupportedOperationException("à vous (1.1)");
	}
}
