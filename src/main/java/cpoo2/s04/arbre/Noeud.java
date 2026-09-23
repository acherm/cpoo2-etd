package cpoo2.s04.arbre;

/** S04 — un nœud de l'arbre d'expression. */
public interface Noeud {
	/**
	 * Q3 bis : l'<b>Interpréteur</b>. Chaque nœud sait s'évaluer, récursivement.
	 * L'oracle est {@code InterpreteurTest}.
	 */
	int evaluer();

	/** Q5 : à implémenter dans chaque classe concrète. Une ligne, et une seule. */
	void accept(VisiteurArbre v);
}
