package cpoo2.divers.journal;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cahier de TD § 8 — le log paresseux.
 *
 * <p>Lancez ce {@code main}. Le niveau est {@code WARNING}, donc <b>aucun</b>
 * des deux messages {@code INFO} n'est écrit. Pourtant l'un des deux a coûté
 * cher. Le compteur vous dit lequel.
 *
 * <p>Questions : quel est le patron sous-jacent ? Quel est l'avantage de la
 * première forme sur la seconde ? Et à partir de quel coût de {@code toString()}
 * cela cesse-t-il d'être une coquetterie ?
 */
public final class Journalisation {

	private static int appelsAToString;

	static class Couteux {
		@Override
		public String toString() {
			appelsAToString++;
			return "un objet dont le toString coûte cher";
		}
	}

	public static void main(final String[] args) {
		final Logger log = Logger.getLogger("cpoo2");
		log.setLevel(Level.WARNING);

		final Couteux clazz = new Couteux();

		// Forme 1 : le message n'est construit que si le niveau le justifie.
		final Supplier<String> paresseux = () -> "process CtClass: " + clazz;
		log.log(Level.INFO, paresseux);
		System.out.println("après la forme paresseuse : " + appelsAToString + " appel(s) à toString()");

		// Forme 2 : la concaténation a lieu AVANT l'appel, quoi qu'il arrive.
		log.log(Level.INFO, "process CtClass: " + clazz);
		System.out.println("après la forme concaténée : " + appelsAToString + " appel(s) à toString()");
	}

	private Journalisation() {
		super();
	}
}
