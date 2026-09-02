package cpoo2.s01.injection;

import java.util.Optional;

/**
 * S01 — exercice 2. Une fabrique qui instancie une classe et remplit
 * automatiquement les attributs annotés {@link Inject}, récursivement.
 *
 * <p>Squelette imposé : ne changez pas la signature de {@code createInstance}.
 *
 * <p>API de réflexion utile :
 * <table border="1">
 *   <tr><td>{@code cl.getDeclaredConstructor().newInstance()}</td><td>crée une instance</td></tr>
 *   <tr><td>{@code cl.getDeclaredFields()}</td><td>les attributs déclarés dans cette classe</td></tr>
 *   <tr><td>{@code f.isAnnotationPresent(Inject.class)}</td><td>l'attribut porte-t-il l'annotation ?</td></tr>
 *   <tr><td>{@code f.getType()}</td><td>le type de l'attribut</td></tr>
 *   <tr><td>{@code f.setAccessible(true)}</td><td>franchir la visibilité</td></tr>
 *   <tr><td>{@code f.set(instance, valeur)}</td><td>écrire dans l'attribut</td></tr>
 *   <tr><td>{@code cl.isPrimitive()}</td><td>type primitif ?</td></tr>
 * </table>
 */
public class Injector {

	/**
	 * @return une instance de {@code cl} avec ses dépendances injectées, ou
	 *         {@code Optional.empty()} si {@code cl} ne peut pas être construite.
	 */
	public <T> Optional<T> createInstance(final Class<T> cl) {
		// TODO Q6 (sans les cycles), puis Q9 (avec).
		return Optional.empty();
	}
}
