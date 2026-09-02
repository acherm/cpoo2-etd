package cpoo2.s01.testabilite;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * S01 — exercice 3. Testabilité et instance unique.
 *
 * <p>Je ne peux pas tester {@code getFooFoo} : il tire une valeur au hasard.
 * Refactorez {@link RandomGenerator} pour rendre {@link Foo} testable, puis
 * complétez cette classe de test.
 *
 * <p>Questions : quel est exactement le défaut de {@code RandomGenerator} ?
 * Quel patron vu en S01 le résout ? Que devient l'instance unique — disparaît-elle,
 * ou change-t-elle simplement de responsable ?
 */
class FooTest {
	@Test
	void getFooFoo() {
		fail("à écrire — mais il faut d'abord rendre Foo testable");
	}
}
