package cpoo2.s01.echauffement;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** S01, partie 0, ex. 1.1 et 1.2 — l'oracle de {@link Position}. */
class PositionTest {

	@Nested
	class LaFabrique {
		@Test
		void e4VautLigne3Colonne4() {
			// 'e' - 'a' = 4 (colonne), '4' - '1' = 3 (ligne) : attention à l'ordre
			// des composants, le record déclare la ligne en premier.
			assertEquals(new Position(3, 4), Position.de("e4"));
		}

		@Test
		void a1EstLOrigine() {
			assertEquals(new Position(0, 0), Position.de("a1"));
		}

		@Test
		void h8EstLeCoinOppose() {
			assertEquals(new Position(7, 7), Position.de("h8"));
		}

		@Test
		void refuseUnFormatFaux() {
			assertThrows(IllegalArgumentException.class, () -> Position.de("e"));
			assertThrows(IllegalArgumentException.class, () -> Position.de("e44"));
		}

		@Test
		void refuseUneCaseHorsPlateau() {
			assertThrows(IllegalArgumentException.class, () -> Position.de("z9"));
		}
	}

	@Nested
	class LeRecord {
		@Test
		void egalParComposantsSansEcrireEquals() {
			// 1.2 : deux objets distincts, mêmes valeurs, égaux.
			final var a = Position.de("e4");
			final var b = new Position(3, 4);
			assertNotSame(a, b);
			assertEquals(a, b);
			assertEquals(a.hashCode(), b.hashCode());
		}

		@Test
		void toStringEstLisible() {
			assertEquals("Position[ligne=3, colonne=4]", new Position(3, 4).toString());
		}

		@Test
		void leConstructeurRefuseAussiLesCoordonneesHorsPlateau() {
			// La validation dans la fabrique seule ne suffit pas :
			// « new Position(99, 99) » passerait.
			assertThrows(IllegalArgumentException.class, () -> new Position(8, 0));
			assertThrows(IllegalArgumentException.class, () -> new Position(0, -1));
		}
	}
}
