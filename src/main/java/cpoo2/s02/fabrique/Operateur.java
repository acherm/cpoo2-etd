package cpoo2.s02.fabrique;

import java.util.Optional;

/**
 * S02, exercice 2. Le domaine est <b>fermé</b> : quatre opérateurs, connus à la
 * compilation. Une chaîne ne devrait le traverser qu'à la frontière.
 *
 * <p>Q6 : écrivez {@code de(String)}, la conversion à la frontière. C'est le
 * même geste qu'à l'exercice 8, hors séance ({@code ColourCard.get}).
 *
 * <p>Q7 : décidez <b>qui</b> connaît la classe concrète : cette énumération
 * (chaque constante porte son constructeur), un {@code switch} dans
 * {@code ExpFactory}, ou une table {@code Map}. Les trois se défendent.
 */
public enum Operateur {
	MULT, PLUS, MIN, MOD;

	/** Q6 : {@code "mult"} donne {@code MULT} ; un nom inconnu ou {@code null} donne vide. */
	public static Optional<Operateur> de(final String nom) {
		return Optional.empty();
	}
}
