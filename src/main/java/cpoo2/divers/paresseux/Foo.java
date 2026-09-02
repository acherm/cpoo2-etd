package cpoo2.divers.paresseux;

/**
 * Cahier de TD § 13 — l'objet paresseux.
 *
 * <p>Donnez le code Java correspondant à ce pseudo-Scala. Le mot-clé {@code val}
 * équivaut au {@code final} de Java ; {@code lazy} signifie que l'instanciation
 * n'a lieu qu'au premier accès.
 *
 * <pre>
 * class Foo {
 *   private lazy val bar : Object = new Object()
 *   def getBar() : Object { return bar }
 * }
 * </pre>
 *
 * <p>Deux pièges, dans l'ordre : (1) que se passe-t-il si la fabrique renvoie
 * légitimement {@code null} — votre code la rappellera-t-il à chaque accès ?
 * (2) que devient votre solution si deux fils d'exécution appellent
 * {@code getBar()} en même temps ?
 */
public class Foo {
	// TODO
}
