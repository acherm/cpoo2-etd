package cpoo2.s02.enumeration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** S03 ex. 1 — l'oracle. Q3 : pourquoi l'absence est-elle une valeur de retour ? */
class ColourCardTest {
	@Test
	void getNullReturnsEmpty() {
		assertTrue(ColourCard.get(null).isEmpty());
	}

	@Test
	void getBadStringReturnsEmpty() {
		assertTrue(ColourCard.get("yolo").isEmpty());
	}

	@ParameterizedTest
	@EnumSource
	void getCorrectStringOK(final ColourCard cc) {
		assertEquals(cc, ColourCard.get(cc.name()).orElseThrow());
	}
}
