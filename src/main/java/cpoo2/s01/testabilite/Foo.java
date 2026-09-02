package cpoo2.s01.testabilite;

/** Voici la classe à tester. Le problème n'est pas dans ce fichier. */
public class Foo {
	private int f;

	public Foo() {
		super();
		f = 10;
	}

	public int getFooFoo() {
		return RandomGenerator.INSTANCE.nextInt() * f;
	}

	public int getF() {
		return f;
	}

	public void setF(final int f) {
		this.f = f;
	}
}
