package cpoo2.s02.fabrique;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S03 ex. 2 — filet de non-régression. Verte AVANT votre refactoring, elle doit
 * le rester APRÈS. Les quatre {@code assertNotSame} sont le cœur de la Q6 :
 * une fabrique qui met les instances en cache les fait virer au rouge.
 */
class ExpFactoryTest {
	ExpFactory fact;

	@BeforeEach
	void setUp() {
		fact = new ExpFactory();
	}

	@Test
	void testUnknown() {
		assertTrue(fact.createExp("ifihdsoi").isEmpty());
	}

	@Test
	void testNull() {
		assertTrue(fact.createExp(null).isEmpty());
	}

	@Test
	void testMult() {
		assertTrue(fact.createExp("mult").orElseThrow() instanceof Mult);
	}

	@Test
	void testMin() {
		assertTrue(fact.createExp("min").orElseThrow() instanceof Min);
	}

	@Test
	void testMod() {
		assertTrue(fact.createExp("mod").orElseThrow() instanceof Mod);
	}

	@Test
	void testPlus() {
		assertTrue(fact.createExp("plus").orElseThrow() instanceof Plus);
	}

	@Test
	void testPlusPlus() {
		assertNotSame(fact.createExp("plus").orElseThrow(), fact.createExp("plus").orElseThrow());
	}

	@Test
	void testModMod() {
		assertNotSame(fact.createExp("mod").orElseThrow(), fact.createExp("mod").orElseThrow());
	}

	@Test
	void testMultMult() {
		assertNotSame(fact.createExp("mult").orElseThrow(), fact.createExp("mult").orElseThrow());
	}

	@Test
	void testMinMin() {
		assertNotSame(fact.createExp("min").orElseThrow(), fact.createExp("min").orElseThrow());
	}
}
