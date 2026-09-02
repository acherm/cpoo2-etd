package cpoo2.s01.optionnel;

/**
 * S01 — exercice 1. Reconstruire {@code Optional} à partir de son seul usage.
 *
 * <p>On ne vous donne pas la classe : on vous donne son usage.
 * <pre>
 * MonOptional&lt;Foo&gt; opt = MonOptional.of(foo);
 * if (opt.isPresent()) { ... }
 * String str = opt.map(foo -&gt; foo.toString()).orElse("");
 * </pre>
 *
 * <p>Contraintes : la classe est <b>générique</b>, elle est <b>immuable</b>,
 * et le champ qui porte la valeur est {@code private final}.
 *
 * <p>La méthode {@code map} n'est volontairement pas déclarée ici : c'est la
 * question Q3 d'en trouver la signature exacte. Quand vous l'aurez écrite,
 * décommentez {@code MonOptionalMapTest}.
 */
public final class MonOptional<T> {

	// TODO Q1 : le champ (private final).

	// TODO Q1 : le constructeur. Pourquoi n'est-il pas public ? (Q2)

	/** TODO Q1 — fabrique statique. Q4 : que doit-elle faire d'un {@code null} ? */
	public static <T> MonOptional<T> of(final T valeur) {
		return null;
	}

	/** TODO Q4 — l'instance vide. Q2 : pourquoi peut-on la partager ? */
	public static <T> MonOptional<T> empty() {
		return null;
	}

	/** TODO Q4 — accepte {@code null}. En quoi diffère-t-elle de {@link #of} ? */
	public static <T> MonOptional<T> ofNullable(final T valeur) {
		return null;
	}

	/** TODO Q1. */
	public boolean isPresent() {
		return false;
	}

	/** TODO Q1. */
	public T orElse(final T defaut) {
		return null;
	}

	// TODO Q3 : map. Quelle signature ? Que renvoie-t-elle si la fonction rend null ?
}
