package cpoo2.s02.pont.impl;

import cpoo2.s02.pont.api.Horloge;
import cpoo2.s02.pont.api.Infrastructure;
import cpoo2.s02.pont.api.Journal;
import cpoo2.s02.pont.api.Messagerie;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * S02, exercice 3, Q10 : la famille « en mémoire », celle des tests. Rien ne
 * part, rien n'avance tout seul, et tout se consulte.
 *
 * <p>Ce squelette ne fixe que ce que {@code RappelsTest} exige : une horloge
 * qu'on avance, une boîte d'envoi qu'on lit. Les trois produits (une horloge
 * figée, un journal en mémoire, une messagerie en mémoire) sont à écrire, ici
 * ou dans des classes voisines. La question qui compte : <b>comment
 * garantissez-vous qu'ils lisent tous la même horloge ?</b>
 *
 * <p>Q11 : une fois {@code Infrastructure} complétée, les trois méthodes de
 * création ci-dessous deviennent les siennes ({@code @Override}).
 */
public final class InfrastructureEnMemoire implements Infrastructure {

	/** Un message parti, tel que la boîte d'envoi l'a vu partir. */
	public record Envoi(Instant quand, String destinataire, String texte) { }

	public InfrastructureEnMemoire(final Instant depart) {
		super();
		// TODO Q10
	}

	/** Avance l'horloge de la famille. Le service, le journal et la messagerie doivent tous le voir. */
	public void avancer(final Duration duree) {
		// TODO Q10
	}

	/** Ce qui est parti, dans l'ordre. */
	public List<Envoi> envoyes() {
		return List.of();   // TODO Q10
	}

	public Horloge horloge() {
		return null;   // TODO Q10
	}

	public Journal journal() {
		return null;   // TODO Q10
	}

	public Messagerie messagerie() {
		return null;   // TODO Q10
	}
}
