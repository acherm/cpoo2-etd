package cpoo2.s03.arbre;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** S04 — l'oracle des trois visiteurs. L'arbre de référence est (2 + 3) - 4. */
class VisiteursTest {

	/** (2 + 3) - 4 */
	private static Arbre reference() {
		return new Arbre(
			new NoeudMoins(new NoeudPlus(new NoeudValeur(2), new NoeudValeur(3)), new NoeudValeur(4)),
			"foo");
	}

	/** 2 - (3 + 4) — l'arbre déséquilibré : la soustraction n'est pas commutative. */
	private static Arbre desequilibre() {
		return new Arbre(
			new NoeudMoins(new NoeudValeur(2), new NoeudPlus(new NoeudValeur(3), new NoeudValeur(4))),
			"bar");
	}

	private static String sansEspaces(final String s) {
		return s.replaceAll("\\s+", " ").trim();
	}

	@Test
	void postfixe() {
		final AfficheurPostfixe v = new AfficheurPostfixe();
		reference().accept(v);
		assertEquals("2 3 + 4 -", sansEspaces(v.resultat()));
	}

	@Test
	void postfixeDesequilibre() {
		final AfficheurPostfixe v = new AfficheurPostfixe();
		desequilibre().accept(v);
		assertEquals("2 3 4 + -", sansEspaces(v.resultat()));
	}

	@Test
	void evalue() {
		final Evaluateur v = new Evaluateur();
		reference().accept(v);
		assertEquals(1, v.resultat());
	}

	@Test
	void evalueDesequilibre() {
		// Si vous obtenez 5 au lieu de -5, vos deux opérandes sont dépilées à l'envers.
		final Evaluateur v = new Evaluateur();
		desequilibre().accept(v);
		assertEquals(-5, v.resultat());
	}

	@Test
	void xml() {
		final SerialiseurXML v = new SerialiseurXML();
		new Arbre(new NoeudPlus(new NoeudValeur(2), new NoeudValeur(3)), "foo").accept(v);
		assertEquals(
			sansEspaces("""
				<Formula name="foo">
				  <Plus>
				    <Value value="2" />
				    <Value value="3" />
				  </Plus>
				</Formula>"""),
			sansEspaces(v.resultat()));
	}
}
