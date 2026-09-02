package cpoo2.s03.arbre;

/**
 * Q9 — produit l'expression sous forme de balises.
 *
 * <pre>
 * &lt;Formula name="foo"&gt;
 *   &lt;Plus&gt;
 *     &lt;Value value="2" /&gt;
 *     &lt;Value value="3" /&gt;
 *   &lt;/Plus&gt;
 * &lt;/Formula&gt;
 * </pre>
 *
 * <p>Le test compare en ignorant les espaces : indentez comme vous voulez.
 */
public class SerialiseurXML implements VisiteurArbre {
	private final StringBuilder sb = new StringBuilder();

	public String resultat() {
		return sb.toString();
	}

	@Override
	public void visiterArbre(final Arbre arbre) {
		// TODO Q9
	}

	@Override
	public void visiterNoeudPlus(final NoeudPlus n) {
		// TODO Q9
	}

	@Override
	public void visiterNoeudMoins(final NoeudMoins n) {
		// TODO Q9
	}

	@Override
	public void visiterNoeudValeur(final NoeudValeur n) {
		// TODO Q9
	}
}
