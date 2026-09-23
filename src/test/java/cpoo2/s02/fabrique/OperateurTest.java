package cpoo2.s02.fabrique;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S02 ex. 2, Q6 et Q7 : la frontière ({@code Operateur.de}) et la création
 * depuis le domaine ({@code creer(Operateur)}). Contrairement à
 * {@code ExpFactoryTest}, cette suite est <b>rouge</b> au départ : c'est un
 * énoncé.
 */
class OperateurTest {

	@Test
	void deChaineConnue() {
		assertEquals(Operateur.MULT, Operateur.de("mult").orElseThrow());
		assertEquals(Operateur.MOD, Operateur.de("mod").orElseThrow());
	}

	@Test
	void deChaineInconnueOuNulle() {
		assertTrue(Operateur.de("ifihdsoi").isEmpty());
		assertTrue(Operateur.de(null).isEmpty());
	}

	@ParameterizedTest
	@EnumSource
	void chaqueOperateurCreeUneExpression(final Operateur op) {
		assertTrue(new ExpFactory().creer(op).isPresent(), "aucun opérateur ne doit être oublié");
	}

	@ParameterizedTest
	@EnumSource
	void chaqueAppelCreeUneNouvelleInstance(final Operateur op) {
		final ExpFactory fact = new ExpFactory();
		assertNotSame(fact.creer(op).orElseThrow(), fact.creer(op).orElseThrow());
	}

	@Test
	void laClasseConcreteSuitLOperateur() {
		final ExpFactory fact = new ExpFactory();
		assertTrue(fact.creer(Operateur.MULT).orElseThrow() instanceof Mult);
		assertTrue(fact.creer(Operateur.PLUS).orElseThrow() instanceof Plus);
		assertTrue(fact.creer(Operateur.MIN).orElseThrow() instanceof Min);
		assertTrue(fact.creer(Operateur.MOD).orElseThrow() instanceof Mod);
	}
}
