package cpoo2.s02.fabrique;

import java.util.Optional;

/**
 * S03 — exercice 2. Cette fabrique <b>fonctionne</b> : la suite
 * {@code ExpFactoryTest} est verte avant que vous n'y touchiez.
 *
 * <p>Elle n'est donc pas un énoncé mais un <b>filet de non-régression</b> :
 * votre refactoring vers le patron Commande doit la laisser verte.
 *
 * <p>Q4 : calculez la complexité cyclomatique de {@code createExp} ci-dessous.
 * Q5 : réécrivez-la avec le patron Commande, et recalculez.
 * Q6 : une {@code Map<String, ArithmExp>} — les instances directement — n'est
 * pas équivalente. Quatre tests de la suite vous le prouveront. Lesquels ?
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
}
