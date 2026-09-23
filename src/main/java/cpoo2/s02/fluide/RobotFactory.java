package cpoo2.s02.fluide;

/**
 * Ancien exercice hors séance de S02, rattaché à S07 (le Monteur et sa descendance). API fluide à états.
 *
 * <p>Ici, <b>le compilateur est le test</b> : il n'y a pas de suite JUnit. Votre
 * solution est correcte quand les usages légitimes compilent et que les trois
 * usages interdits de {@link Exemples} refusent de compiler.
 *
 * <p>À vous de définir les types renvoyés. Indice : {@code newRobot()} ne peut
 * pas renvoyer le même type que {@code press(...)}.
 */
public interface RobotFactory {
	// TODO Q22' : quel type de retour ?
	Object newRobot();
}
