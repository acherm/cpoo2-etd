package cpoo2.s01.echauffement;

import java.util.List;

/**
 * S01, partie 0 — exercice 3 : lambdas et flux.
 *
 * <p>3.1 — {@link #motsLongsEnMajuscules} : en <b>une seule expression</b> de
 * flux, les mots de plus de trois lettres, en majuscules, triés par longueur
 * croissante.
 *
 * <p>3.2 — {@link ReglesTest#peekSansOperationTerminaleNeFaitRien} est déjà
 * vert : expliquez pourquoi.
 *
 * <p>3.3 — Deux implémentations de {@link Regle} en <b>lambda</b>, sans écrire
 * une seule classe nommée (ni anonyme) : l'une accepte tout, l'autre refuse les
 * {@link Tir} de force nulle.
 */
public final class Regles {

	private Regles() { }

	/** TODO 3.1 — une seule expression de flux. */
	public static List<String> motsLongsEnMajuscules(final List<String> mots) {
		throw new UnsupportedOperationException("à vous (3.1)");
	}

	/** TODO 3.3 — accepte tout coup. */
	public static Regle toutAccepter() {
		throw new UnsupportedOperationException("à vous (3.3)");
	}

	/** TODO 3.3 — refuse les tirs de force nulle, accepte tout le reste. */
	public static Regle sansTirNul() {
		throw new UnsupportedOperationException("à vous (3.3)");
	}
}
