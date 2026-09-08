package cpoo2.s01.echauffement;

import java.util.List;

/**
 * S01, partie 0 — exercice 5 : la variance, à la main.
 *
 * <p>Pas de test ici, et c'est voulu : les deux questions portent sur ce que le
 * <b>compilateur</b> accepte ou refuse. Décommentez chaque bloc, compilez
 * ({@code mvn -q compile}), lisez le message, corrigez, exécutez :
 * <pre>
 * java -cp target/classes cpoo2.s01.echauffement.Variance
 * </pre>
 */
public final class Variance {

	public static void main(final String[] args) {
		// 5.1 — Ce code compile-t-il ? Sinon, corrigez-le SANS changer le type
		// de « vaches ». Indice : le cours parle de « ? extends ».
		// List<Vache> vaches = List.of(new Vache());
		// List<Animal> animaux = vaches;
		// double total = animaux.stream().mapToDouble(Animal::poids).sum();
		// System.out.println("total : " + total);

		// 5.2 — Et celui-ci ? Compile-t-il ? S'exécute-t-il ? Notez le nom de
		// l'exception. Que dit ce comportement sur les tableaux Java comparés
		// aux génériques ?
		// Object[] objets = new String[2];
		// objets[0] = 42;
		// System.out.println(objets[0]);

		System.out.println("Décommentez les blocs 5.1 et 5.2 de Variance.java.");
	}
}
