package cpoo2.s02.pont.service;

import cpoo2.s02.pont.api.Infrastructure;
import cpoo2.s02.pont.impl.HorlogeSysteme;
import cpoo2.s02.pont.impl.JournalConsole;
import cpoo2.s02.pont.impl.MessagerieConsole;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * S02, exercice 3. Le service métier : des rappels à envoyer quand leur
 * échéance est passée.
 *
 * <p>Tel quel, il <b>choisit lui-même</b> son horloge, son journal et sa
 * messagerie, avec trois {@code new} de classes concrètes du paquet
 * {@code impl}. C'est le défaut de {@code Foo} de la séance 1, en triple.
 *
 * <p>Q9 (Pont) : ce fichier ne doit plus importer que {@code api}.
 * Q10 : la famille « en mémoire », dans {@code impl}, à créer.
 * Q11 (Fabrique abstraite) : {@code Infrastructure}, et un seul point de
 * choix. Q12 : {@code RappelsTest} au vert.
 */
public final class Rappels {
	private record Rappel(String destinataire, String texte, Instant echeance) { }

	private final HorlogeSysteme horloge = new HorlogeSysteme();
	private final JournalConsole journal = new JournalConsole(horloge);
	private final MessagerieConsole messagerie = new MessagerieConsole();
	private final List<Rappel> enAttente = new ArrayList<>();

	/**
	 * Q9 : ce constructeur doit devenir le <b>seul</b> moyen d'obtenir l'horloge,
	 * le journal et la messagerie. Pour l'instant il ignore son argument, et les
	 * trois champs ci-dessus sont fixés par des {@code new}.
	 */
	public Rappels(final Infrastructure infra) {
		super();
		// TODO Q9
	}

	public void planifier(final String destinataire, final String texte, final Instant echeance) {
		enAttente.add(new Rappel(destinataire, texte, echeance));
		journal.noter("planifié pour " + destinataire + " à " + echeance);
	}

	/** Envoie les rappels dont l'échéance est passée, et les retire de l'attente. */
	public int traiter() {
		final Instant maintenant = horloge.maintenant();
		int envoyes = 0;
		for (final var it = enAttente.iterator(); it.hasNext();) {
			final Rappel r = it.next();
			if (!r.echeance().isAfter(maintenant)) {
				messagerie.envoyer(r.destinataire(), r.texte());
				journal.noter("envoyé à " + r.destinataire());
				it.remove();
				envoyes++;
			}
		}
		return envoyes;
	}

	public int enAttente() {
		return enAttente.size();
	}
}
