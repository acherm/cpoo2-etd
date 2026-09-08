package cpoo2.s01.echauffement;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** S01, partie 0, ex. 2.2 et 2.5 — l'oracle de {@link Coups#decrire}. */
class CoupsTest {

	private static final Position E2 = new Position(1, 4);
	private static final Position E4 = new Position(3, 4);

	private static String decrire(final Coup c) {
		return Coups.decrire(c).toLowerCase(Locale.ROOT);
	}

	@Test
	void unDeplacement() {
		assertTrue(decrire(new Deplacement(E2, E4)).startsWith("déplacement"));
	}

	@Test
	void unePasse() {
		assertTrue(decrire(new Passe(E2, E4)).startsWith("passe"));
	}

	@Test
	void unTir() {
		assertTrue(decrire(new Tir(E2, 3)).startsWith("tir"));
	}

	@Test
	void unDeplacementSurPlaceEstTraiteAPart() {
		// 2.5 : déconstruction + garde « when de.equals(vers) ».
		assertTrue(decrire(new Deplacement(E4, E4)).contains("sur place"));
	}
}
