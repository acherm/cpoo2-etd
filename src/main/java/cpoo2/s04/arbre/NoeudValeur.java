package cpoo2.s04.arbre;

public class NoeudValeur implements Noeud {
	public final int valeur;

	public NoeudValeur(final int valeur) {
		super();
		this.valeur = valeur;
	}

	@Override
	public int evaluer() {
		throw new UnsupportedOperationException("TODO Q3 bis");
	}

	@Override
	public void accept(final VisiteurArbre v) {
		// TODO Q5
	}
}
