package cpoo2.s03.arbre;

/**
 * S04 — exercice 4, Q10 et Q11. On ne discute plus, on <b>compte</b>.
 *
 * <p>Décommentez la première moitié après avoir ajouté {@code NoeudMult} (Q10),
 * la seconde après avoir ajouté {@code CompteurDeNoeuds} (Q11). Puis remplissez
 * le tableau du sujet — en comptant les fichiers que vous avez dû <b>modifier</b>,
 * pas seulement ceux que vous avez créés. Ce fichier-ci compte aussi.
 */
class ExpressionProblemTest {

	// ---- Q10 : ajouter un TYPE -------------------------------------------
//	@Test
//	void multPostfixe() {
//		final AfficheurPostfixe v = new AfficheurPostfixe();
//		new Arbre(new NoeudMult(new NoeudValeur(2), new NoeudValeur(3)), "m").accept(v);
//		assertEquals("2 3 *", v.resultat().replaceAll("\\s+", " ").trim());
//	}
//
//	@Test
//	void multEvalue() {
//		final Evaluateur v = new Evaluateur();
//		new Arbre(new NoeudMult(new NoeudValeur(2), new NoeudValeur(3)), "m").accept(v);
//		assertEquals(6, v.resultat());
//	}

	// ---- Q11 : ajouter une OPÉRATION --------------------------------------
//	@Test
//	void compteLesNoeuds() {
//		final CompteurDeNoeuds v = new CompteurDeNoeuds();
//		new Arbre(new NoeudMoins(new NoeudPlus(new NoeudValeur(2), new NoeudValeur(3)),
//		                         new NoeudValeur(4)), "foo").accept(v);
//		assertEquals(5, v.resultat());
//	}
}
