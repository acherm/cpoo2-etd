package cpoo2.s02.fluide;

/** Les usages que votre API doit accepter — et ceux qu'elle doit refuser. */
public final class Exemples {

	private Exemples() {
		super();
	}

	public static void doitCompiler(final RobotFactory factory) {
		// TODO décommentez au fur et à mesure que votre API prend forme.
		// factory.newRobot()
		//        .click(10, 2)
		//        .type("yolo");
		//
		// factory.newRobot()
		//        .press(10, 2)
		//        .release()
		//        .execute(() -> System.out.println("coucou"))
		//        .press(10, 2)
		//        .release();
	}

	public static void neDoitPasCompiler(final RobotFactory factory) {
		// Ces trois lignes doivent rester des erreurs de compilation.
		// Q14' : dites lequel des trois est refusé par quel type.
		//
		// factory.newRobot().press(10, 20).press(30, 10);
		// factory.newRobot().press(10, 20).click(30, 10);
		// factory.newRobot().release();
	}
}
