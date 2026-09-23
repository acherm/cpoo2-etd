package cpoo2.s02.pont;

import cpoo2.s02.pont.api.Infrastructure;
import cpoo2.s02.pont.impl.InfrastructureEnMemoire;
import cpoo2.s02.pont.service.Rappels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S02 ex. 3, Q12 : l'oracle. Il ne compile qu'une fois la famille « en mémoire »
 * écrite ({@code InfrastructureEnMemoire}, dans {@code impl}) et le service
 * {@code Rappels} construit à partir d'une {@code Infrastructure}.
 *
 * <p>Lisez-le avant de coder : il dit ce que la famille doit offrir de plus que
 * l'API (une horloge qu'on <b>avance</b>, une boîte d'envoi qu'on <b>consulte</b>).
 */
class RappelsTest {
	static final Instant T0 = Instant.parse("2026-09-21T10:00:00Z");

	InfrastructureEnMemoire infra;
	Rappels rappels;

	@BeforeEach
	void setUp() {
		infra = new InfrastructureEnMemoire(T0);
		final Infrastructure vueDuService = infra;   // le service ne voit que l'API
		rappels = new Rappels(vueDuService);
	}

	@Test
	void rienNestEnvoyeAvantLEcheance() {
		rappels.planifier("alice", "rendre le TP", T0.plus(Duration.ofHours(1)));
		assertEquals(0, rappels.traiter());
		assertTrue(infra.envoyes().isEmpty());
		assertEquals(1, rappels.enAttente());
	}

	@Test
	void leRappelPartQuandLHorlogeAvance() {
		rappels.planifier("alice", "rendre le TP", T0.plus(Duration.ofHours(1)));
		infra.avancer(Duration.ofHours(2));
		assertEquals(1, rappels.traiter());
		assertEquals(1, infra.envoyes().size());
		assertEquals("alice", infra.envoyes().get(0).destinataire());
		assertEquals(0, rappels.enAttente());
	}

	@Test
	void unRappelNePartQuUneFois() {
		rappels.planifier("bob", "réunion", T0);
		rappels.traiter();
		rappels.traiter();
		assertEquals(1, infra.envoyes().size());
	}

	@Test
	void leJournalEstDateParLHorlogeDeLaFamille() {
		infra.avancer(Duration.ofMinutes(5));
		rappels.planifier("bob", "réunion", T0);
		final var entrees = infra.journal().entrees();
		assertEquals(1, entrees.size());
		assertEquals(T0.plus(Duration.ofMinutes(5)), entrees.get(0).quand(),
				"le journal et le service doivent lire la même horloge");
	}

	@Test
	void lEnvoiEstDateParLHorlogeDeLaFamille() {
		rappels.planifier("bob", "réunion", T0);
		infra.avancer(Duration.ofDays(1));
		rappels.traiter();
		assertEquals(T0.plus(Duration.ofDays(1)), infra.envoyes().get(0).quand(),
				"la boîte d'envoi et le service doivent lire la même horloge");
	}
}
