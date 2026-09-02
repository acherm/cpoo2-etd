package cpoo2.s03.arbre;

/**
 * S04 — Q6, l'expérience. « Pourquoi les méthodes {@code accept} sont-elles
 * nécessaires ? »
 *
 * <p>Supposons un visiteur qui, au lieu de quatre noms distincts, <b>surcharge</b>
 * un unique nom {@code visiter}, et un client qui l'appelle directement sans
 * passer par {@code accept}. Lancez ce {@code main} et lisez la sortie.
 *
 * <pre>mvn -q compile exec:java -Dexec.mainClass=cpoo2.s03.arbre.SurchargeDemo</pre>
 *
 * <p>ou simplement, depuis {@code target/classes} :
 * <pre>java -cp target/classes cpoo2.s03.arbre.SurchargeDemo</pre>
 *
 * <p>Répondez ensuite : à quel moment Java choisit-il une surcharge ?
 */
public final class SurchargeDemo {

	static class VisiteurSurcharge {
		String visiter(final Noeud n)        { return "visiter(Noeud)"; }
		String visiter(final NoeudPlus n)    { return "visiter(NoeudPlus)"; }
		String visiter(final NoeudValeur n)  { return "visiter(NoeudValeur)"; }
	}

	public static void main(final String[] args) {
		final VisiteurSurcharge v = new VisiteurSurcharge();

		final NoeudPlus plusConcret = new NoeudPlus(new NoeudValeur(2), new NoeudValeur(3));
		final Noeud plusDeclareNoeud = plusConcret;

		System.out.println("type déclaré NoeudPlus -> " + v.visiter(plusConcret));
		System.out.println("type déclaré Noeud     -> " + v.visiter(plusDeclareNoeud));
		System.out.println();
		System.out.println("C'est le même objet dans les deux cas.");
		System.out.println("Que conclure ? Et que change accept() ?");
	}

	private SurchargeDemo() {
		super();
	}
}
