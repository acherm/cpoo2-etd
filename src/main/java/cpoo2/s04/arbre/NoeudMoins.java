package cpoo2.s04.arbre;

public class NoeudMoins extends NoeudBinaire {
	public NoeudMoins(final Noeud gauche, final Noeud droit) {
		super(gauche, droit);
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
