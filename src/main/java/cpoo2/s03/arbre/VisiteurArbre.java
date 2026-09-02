package cpoo2.s03.arbre;

/**
 * S04 — l'interface de visite.
 *
 * <p>Remarquez que les quatre méthodes ont des <b>noms distincts</b>. Elles
 * pourraient toutes s'appeler {@code visiter} : c'est l'objet de la Q6, et
 * {@link SurchargeDemo} vous laisse en faire l'expérience.
 */
public interface VisiteurArbre {
	void visiterArbre(Arbre arbre);
	void visiterNoeudPlus(NoeudPlus n);
	void visiterNoeudMoins(NoeudMoins n);
	void visiterNoeudValeur(NoeudValeur n);
}
