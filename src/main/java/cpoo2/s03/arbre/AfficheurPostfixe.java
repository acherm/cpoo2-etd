package cpoo2.s03.arbre;

/** Q7 — affiche l'expression en notation postfixée. Pour (2 + 3) - 4 : « 2 3 + 4 - ». */
public class AfficheurPostfixe implements VisiteurArbre {
	private final StringBuilder sb = new StringBuilder();

	/** Le résultat est exposé, pas imprimé : c'est ce qui rend le visiteur testable. */
	public String resultat() {
		return sb.toString().trim();
	}

	@Override
	public void visiterArbre(final Arbre arbre) {
		// TODO Q7
	}

	@Override
	public void visiterNoeudPlus(final NoeudPlus n) {
		// TODO Q7
	}

	@Override
	public void visiterNoeudMoins(final NoeudMoins n) {
		// TODO Q7
	}

	@Override
	public void visiterNoeudValeur(final NoeudValeur n) {
		// TODO Q7
	}
}
