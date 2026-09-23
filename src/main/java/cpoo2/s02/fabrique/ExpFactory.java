package cpoo2.s02.fabrique;

import java.util.Optional;

/**
 * S02, exercice 2. Cette fabrique <b>fonctionne</b> : la suite
 * {@code ExpFactoryTest} est verte avant que vous n'y touchiez. Elle n'est
 * donc pas un énoncé mais un <b>filet de non-régression</b>.
 *
 * <p>Deux défauts s'y cachent : les branches (Q5 : mesurez-les) et le fait
 * qu'une <b>chaîne</b> traverse tout le code alors que le domaine est fermé.
 *
 * <p>Q6 : la frontière. {@code createExp(String)} doit devenir une ligne :
 * convertir la chaîne en {@link Operateur}, puis déléguer à
 * {@code creer(Operateur)}.
 *
 * <p>Q7 : écrivez {@code creer(Operateur)} sans qu'un opérateur puisse
 * être <b>oublié</b>. Trois voies : {@code switch} exhaustif, la constante
 * qui porte son constructeur, ou une table. Comparez-les (Q7).
 *
 * <p>Q8 : le piège. Faire porter à la constante <b>l'instance</b>
 * ({@code MULT(new Mult())}) au lieu du constructeur ({@code MULT(Mult::new)})
 * fait virer quatre tests au rouge. Lesquels, et pourquoi ?
 */
public class ExpFactory {
	public Optional<ArithmExp> createExp(final String exp) {
		if ("mult".equals(exp)) {
			return Optional.of(new Mult());
		}
		if ("plus".equals(exp)) {
			return Optional.of(new Plus());
		}
		if ("min".equals(exp)) {
			return Optional.of(new Min());
		}
		if ("mod".equals(exp)) {
			return Optional.of(new Mod());
		}
		return Optional.empty();
	}

	/** Q7 : la création depuis le domaine, plus aucune chaîne ici. */
	public Optional<ArithmExp> creer(final Operateur op) {
		return Optional.empty();
	}
}
