package cpoo2.s03.arbre;

import java.util.Objects;

/** Un nœud à deux fils. */
public abstract class NoeudBinaire implements Noeud {
	public final Noeud gauche;
	public final Noeud droit;

	protected NoeudBinaire(final Noeud gauche, final Noeud droit) {
		super();
		this.gauche = Objects.requireNonNull(gauche);
		this.droit = Objects.requireNonNull(droit);
	}
}
