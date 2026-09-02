package cpoo2.s03.arbre;

import java.util.Objects;

/** Un arbre d'expression : une racine et un nom. */
public class Arbre {
	public final Noeud racine;
	public final String nom;

	public Arbre(final Noeud racine, final String nom) {
		super();
		this.racine = Objects.requireNonNull(racine);
		this.nom = Objects.requireNonNull(nom);
	}

	public void accept(final VisiteurArbre v) {
		v.visiterArbre(this);
	}
}
