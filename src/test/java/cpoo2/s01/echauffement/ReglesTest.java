package cpoo2.s01.echauffement;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** S01, partie 0, ex. 3 — lambdas et flux. */
class ReglesTest {

	@Nested
	class Flux {
		@Test
		void motsLongsEnMajusculesTriesParLongueur() {
			// 3.1
			final List<String> mots = List.of("tour", "fou", "cavalier", "roi");
			assertEquals(List.of("TOUR", "CAVALIER"), Regles.motsLongsEnMajuscules(mots));
		}

		@Test
		void lOrdreEstCeluiDesLongueurs() {
			assertEquals(List.of("ABCD", "ABCDE", "ABCDEF"),
					Regles.motsLongsEnMajuscules(List.of("abcdef", "a", "abcde", "abcd")));
		}

		@Test
		void peekSansOperationTerminaleNeFaitRien() {
			// 3.2 : ce test est vert dès le départ. Expliquez pourquoi.
			final var compte = new AtomicInteger();
			final var flux = List.of("tour", "fou").stream().peek(m -> compte.incrementAndGet());
			assertEquals(0, compte.get());              // rien n'a été parcouru
			assertEquals(2, flux.toList().size());       // une opération terminale...
			assertEquals(2, compte.get());              // ...et peek a tourné
		}
	}

	@Nested
	class Lambdas {
		private static final Position E2 = new Position(1, 4);
		private static final Position E4 = new Position(3, 4);

		@Test
		void toutAccepterAccepteTout() {
			assertTrue(Regles.toutAccepter().accepte(new Tir(E2, 0)));
			assertTrue(Regles.toutAccepter().accepte(new Passe(E2, E4)));
		}

		@Test
		void sansTirNulRefuseSeulementLesTirsDeForceNulle() {
			assertFalse(Regles.sansTirNul().accepte(new Tir(E2, 0)));
			assertTrue(Regles.sansTirNul().accepte(new Tir(E2, 2)));
			assertTrue(Regles.sansTirNul().accepte(new Deplacement(E2, E4)));
		}

		@Test
		void sansClasseNommeeNiAnonyme() {
			// 3.3 : une lambda est compilée en classe synthétique ; une classe
			// nommée ou anonyme ne l'est pas.
			assertTrue(Regles.toutAccepter().getClass().isSynthetic(), "une lambda, pas une classe");
			assertTrue(Regles.sansTirNul().getClass().isSynthetic(), "une lambda, pas une classe");
		}
	}
}
