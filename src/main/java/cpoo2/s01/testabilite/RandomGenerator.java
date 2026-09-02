package cpoo2.s01.testabilite;

import java.util.concurrent.ThreadLocalRandom;

/** Une classe utilitaire <b>à refactorer</b>. En l'état, elle rend {@link Foo} intestable. */
public final class RandomGenerator {
	public static final RandomGenerator INSTANCE = new RandomGenerator();

	private RandomGenerator() {
		super();
	}

	public int nextInt() {
		return ThreadLocalRandom.current().nextInt();
	}
}
