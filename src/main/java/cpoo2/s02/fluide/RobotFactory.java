package cpoo2.s02.fluide;

/**
 * S03 — exercice 5, option B. API fluide à états.
 *
 * <p>Ici, <b>le compilateur est le test</b> : il n'y a pas de suite JUnit. Votre
 * solution est correcte quand les usages légitimes compilent et que les trois
 * usages interdits de {@link Exemples} refusent de compiler.
 *
 * <p>À vous de définir les types renvoyés. Indice : {@code newRobot()} ne peut
 * pas renvoyer le même type que {@code press(...)}.
 */
public interface RobotFactory {
	// TODO Q13' : quel type de retour ?
	Object newRobot();
}
