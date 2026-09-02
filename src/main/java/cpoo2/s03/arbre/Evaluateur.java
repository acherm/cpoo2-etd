package cpoo2.s03.arbre;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Q8 — calcule la valeur de l'expression.
 *
 * <p>Indice : une pile où l'on empile les valeurs visitées. Vous utilisez alors
 * le Visiteur pour réaliser proprement un <b>Interpréteur</b>.
 *
 * <p>Attention à l'ordre : la soustraction n'est pas commutative.
 */
public class Evaluateur implements VisiteurArbre {
	private final Deque<Integer> pile = new ArrayDeque<>();

	public int resultat() {
		return pile.peek() == null ? 0 : pile.peek();
	}

	@Override
	public void visiterArbre(final Arbre arbre) {
		// TODO Q8
	}

	@Override
	public void visiterNoeudPlus(final NoeudPlus n) {
		// TODO Q8
	}

	@Override
	public void visiterNoeudMoins(final NoeudMoins n) {
		// TODO Q8
	}

	@Override
	public void visiterNoeudValeur(final NoeudValeur n) {
		// TODO Q8
	}
}
