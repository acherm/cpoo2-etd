package cpoo2.s01.echauffement;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * S01, partie 0, ex. 1.3 — l'immuabilité d'un record est superficielle.
 * Deux fuites, deux tests rouges. Le troisième est vert dès le départ :
 * pourquoi ?
 */
class EquipeTest {

	@Test
	void laListeRecueAuConstructeurNeFuitPas() {
		final var pieces = new ArrayList<>(List.of("tour"));
		final var e = new Equipe("Bleus", pieces);
		pieces.add("roi");                              // par la référence extérieure
		assertEquals(List.of("tour"), e.pieces());
	}

	@Test
	void lAccesseurNeLaissePasModifier() {
		final var e = new Equipe("Bleus", new ArrayList<>(List.of("tour")));
		assertThrows(UnsupportedOperationException.class, () -> e.pieces().add("fou"));
	}

	@Test
	void deuxEquipesDeMemesValeursSontEgales() {
		assertEquals(new Equipe("Bleus", List.of("tour")), new Equipe("Bleus", List.of("tour")));
	}
}
