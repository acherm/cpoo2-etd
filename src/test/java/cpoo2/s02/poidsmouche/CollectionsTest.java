package cpoo2.s02.poidsmouche;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** S03 ex. 6 — l'oracle. Le test qui compte est {@code emptyListEstPartagee}. */
class CollectionsTest {

	@Test
	void singletonListContientSonElement() {
		assertEquals("a", Collections.singletonList("a").get(0).orElseThrow());
		assertTrue(Collections.singletonList("a").contains("a"));
	}

	@Test
	void singletonListNaQuUnElement() {
		assertTrue(Collections.singletonList("a").get(1).isEmpty());
	}

	@Test
	void singletonListEstImmuable() {
		assertThrows(UnsupportedOperationException.class, () -> Collections.singletonList("a").add("b"));
	}

	@Test
	void emptyListEstVide() {
		assertTrue(Collections.emptyList().get(0).isEmpty());
		assertFalse(Collections.emptyList().contains("a"));
	}

	@Test
	void emptyListEstImmuable() {
		assertThrows(UnsupportedOperationException.class, () -> Collections.emptyList().add("b"));
	}

	@Test
	void emptyListEstPartagee() {
		// Le poids-mouche est ici : toutes les listes vides sont interchangeables,
		// donc il n'en faut qu'une. Pourquoi singletonList ne peut-elle pas faire pareil ?
		assertSame(Collections.emptyList(), Collections.emptyList());
	}
}
