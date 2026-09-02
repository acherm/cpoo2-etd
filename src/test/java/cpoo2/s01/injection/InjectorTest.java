package cpoo2.s01.injection;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S01 ex. 2 — l'oracle de {@link Injector}.
 *
 * <p><b>Q12.</b> Avant d'écrire quoi que ce soit, lancez {@code mvn test}.
 * Une partie de ces douze tests est déjà <i>verte</i> sur un squelette qui ne
 * fait rien. Lesquels, et pourquoi ? Que vaut un test vert qui ne prouve rien ?
 *
 * <p><b>Q13.</b> Un de ces tests passera au vert dès votre première
 * implémentation <i>sans vérifier ce que son nom annonce</i>. Trouvez-le,
 * dites pourquoi, et réparez-le.
 */
class InjectorTest {
	Injector injector;

	@BeforeEach
	void setUp() {
		injector = new Injector();
	}

	@Test
	void testNull() {
		assertTrue(injector.createInstance(null).isEmpty());
	}

	@Test
	void testPrimitive() {
		assertTrue(injector.createInstance(int.class).isEmpty());
	}

	@Test
	void testPrimitive2() {
		assertTrue(injector.createInstance(Integer.class).isEmpty());
	}

	@Test
	void testNoDependency() {
		assertTrue(injector.createInstance(B.class).isPresent());
	}

	@Test
	void testNotSingleInstance() {
		assertNotSame(injector.createInstance(B.class), injector.createInstance(B.class));
	}

	@Test
	void testWithDependency() {
		final Optional<A> opta = injector.createInstance(A.class);
		assertTrue(opta.isPresent());
		final A a = opta.get();
		assertNull(a.c);              // c n'est pas annoté : il reste null
		assertNotNull(a.b1);
		assertNotNull(a.b2);
		assertNotSame(a.b1, a.b2);    // Q10 : deux points d'injection, deux instances
	}

	@Test
	void testWithDeepDependency() {
		final Optional<C> optc = injector.createInstance(C.class);
		assertTrue(optc.isPresent());
		final C c = optc.get();
		assertNotNull(c.a);
		assertNotNull(c.a.b1);
		assertNotNull(c.a.b2);
		assertNull(c.a.c);
	}

	@Test
	void testWithCyclicDependency() {
		// Q9 : au cycle, on n'injecte pas — on ne boucle pas et on ne lève pas.
		final Optional<E> opte = injector.createInstance(E.class);
		assertTrue(opte.isPresent());
		final E e = opte.get();
		assertNotNull(e.f);
		assertNull(e.f.e);
	}

	@Test
	void testArray() {
		assertTrue(injector.createInstance(Object[].class).isEmpty());
	}

	@Test
	void testNoDefaultConstructor() {
		assertTrue(injector.createInstance(I.class).isEmpty());
	}

	@Test
	void primitiveField() {
		// Q7 : un int ne s'injecte pas — il garde sa valeur par défaut.
		final Optional<G> optg = injector.createInstance(G.class);
		assertTrue(optg.isPresent());
		assertEquals(0, optg.get().i);
	}

	@Test
	void abstractClass() {
		assertTrue(injector.createInstance(H.class).isEmpty());
	}
}

// ---- Les classes de test. Ne les modifiez pas. ----------------------------

class A {
	@Inject B b1;
	@Inject B b2;
	C c;
	public A() { }
	@Override public String toString() { return "A{b1=" + b1 + ", b2=" + b2 + ", c=" + c + '}'; }
}

class B {
	public B() { }
}

class C {
	@Inject A a;
	public C() { }
	@Override public String toString() { return "C{a=" + a + '}'; }
}

class E {
	@Inject F f;
	public E() { }
	@Override public String toString() { return "E{f=" + f + '}'; }
}

class F {
	@Inject E e;
	public F() { }
	@Override public String toString() { return "F{e=" + e + '}'; }
}

class G {
	@Inject int i;
	public G() { }
}

abstract class H {
	public H() { }
}

class I {
	public I(final int foo) { }
}
