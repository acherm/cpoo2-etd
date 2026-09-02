package cpoo2.s02.monteur;

/**
 * S03 — exercice 5, option A. Monteurs fonctionnels.
 *
 * <p>Valeurs par défaut imposées : {@code age = 1}, {@code hauteur = 1},
 * {@code feuilles = false}, {@code cones = 10}.
 *
 * <p>Q14 : le code du monteur de chênes seulement.
 * Q15 : créer un chêne de 10 ans, sans feuille.
 * Q16 : que change le mot « fonctionnel » ? Montrez-le avec un monteur
 * <b>partiellement configuré</b> que l'on réutilise deux fois.
 */
public abstract class Arbre {
	protected int age;
	protected int hauteur;

	protected Arbre(final int age, final int hauteur) {
		this.age = age;
		this.hauteur = hauteur;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{age=" + age + ", hauteur=" + hauteur + '}';
	}
}

class Chene extends Arbre {
	private final boolean feuilles;

	Chene(final int age, final int hauteur, final boolean feuilles) {
		super(age, hauteur);
		this.feuilles = feuilles;
	}

	@Override
	public String toString() {
		return "Chene{age=" + age + ", hauteur=" + hauteur + ", feuilles=" + feuilles + '}';
	}
}

class Pin extends Arbre {
	private final int cones;

	Pin(final int age, final int hauteur, final int cones) {
		super(age, hauteur);
		this.cones = cones;
	}

	@Override
	public String toString() {
		return "Pin{age=" + age + ", hauteur=" + hauteur + ", cones=" + cones + '}';
	}
}
