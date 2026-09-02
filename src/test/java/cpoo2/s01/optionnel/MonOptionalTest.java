package cpoo2.s01.optionnel;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** S01 ex. 1 — l'oracle de {@link MonOptional}, hors {@code map} (voir Q3). */
class MonOptionalTest {

	@Nested
	class QuandVide {
		@Test
		void emptyNestPasNull() {
			assertNotNull(MonOptional.empty());
		}

		@Test
		void emptyEstPartagee() {
			// Q2, seconde raison : rien ne distingue deux vides, on peut donc
			// n'en avoir qu'une. Un constructeur public l'interdirait.
			assertSame(MonOptional.empty(), MonOptional.empty());
		}

		@Test
		void isPresentEstFaux() {
			assertFalse(MonOptional.<String>empty().isPresent());
		}

		@Test
		void orElseRendLeDefaut() {
			assertEquals("bar", MonOptional.<String>empty().orElse("bar"));
		}

		@Test
		void ofNullableRendVide() {
			assertFalse(MonOptional.ofNullable(null).isPresent());
		}

		@Test
		void ofRefuseNull() {
			// Q4 : « of(null) » est une erreur de programmation, pas une absence.
			assertThrows(NullPointerException.class, () -> MonOptional.of(null));
		}
	}

	@Nested
	class QuandPleine {
		@Test
		void ofNestPasNull() {
			assertNotNull(MonOptional.of("foo"));
		}

		@Test
		void isPresentEstVrai() {
			assertTrue(MonOptional.of("foo").isPresent());
		}

		@Test
		void orElseIgnoreLeDefaut() {
			assertEquals("foo", MonOptional.of("foo").orElse(""));
		}
	}
}
