package cpoo2.s04.arbre;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * S04, exercice 1, Q3 bis : l'<b>Interpréteur</b>. L'évaluation vit DANS les
 * nœuds ; à la Q8 elle en sortira (Visiteur). Deux tests, rouges tant que
 * {@code evaluer()} lève {@code UnsupportedOperationException}.
 */
class InterpreteurTest {

	@Test
	void evalueLArbreDeReference() {
		// (2 + 3) - 4
		final Noeud racine = new NoeudMoins(new NoeudPlus(new NoeudValeur(2), new NoeudValeur(3)), new NoeudValeur(4));
		assertEquals(1, racine.evaluer());
	}

	@Test
	void evalueLArbreDesequilibre() {
		// 2 - (3 + 4) : la soustraction n'est pas commutative
		final Noeud racine = new NoeudMoins(new NoeudValeur(2), new NoeudPlus(new NoeudValeur(3), new NoeudValeur(4)));
		assertEquals(-5, racine.evaluer());
	}
}
