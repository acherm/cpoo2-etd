package cpoo2.divers.questions;

import java.util.HashMap;
import java.util.Map;

/**
 * Cahier de TD § 15, question 2 — {@code getOrDefault} peut-il lever un
 * {@code NullPointerException} quand la clé est absente ?
 *
 * <p>Lancez, observez, puis répondez. Attention : la réponse évidente n'est pas
 * celle qui compte. Il existe un second cas, que ce {@code main} ne teste pas
 * encore — trouvez-le et ajoutez-le.
 */
public class Q1 {

	interface MyCmd {
		Object myOperation();
	}

	public static void main(final String[] args) {
		final Map<String, MyCmd> map = new HashMap<>();
		map.put("connu", () -> "resultat");

		System.out.println("clé connue  : " + map.getOrDefault("connu", () -> null).myOperation());
		System.out.println("clé absente : " + map.getOrDefault("inconnu", () -> null).myOperation());

		// TODO : et si une clé PRÉSENTE est associée à null ?
	}
}
