package cpoo2.s01.injection;

/**
 * S01 — exercice 2, Q5. Cette annotation tient en trois lignes, et l'une
 * d'elles décide que tout le reste fonctionne.
 *
 * <p>Elle est ici <b>incomplète</b>. Telle quelle, l'injecteur le mieux écrit
 * du monde ne trouvera jamais un seul attribut à injecter — et sans message
 * d'erreur. À vous de trouver la ligne manquante et d'expliquer pourquoi son
 * oubli est <i>silencieux</i>.
 *
 * <p>Une annotation a trois faces. On la <b>pose</b> : {@code @Inject B b;}
 * dans les classes de {@code InjectorTest}, comme {@code @Test} sur une
 * méthode. Quelqu'un la <b>lit</b> : votre {@link Injector}, par
 * {@code f.isAnnotationPresent(Inject.class)}, comme JUnit lit {@code @Test}.
 * Et on la <b>déclare</b> : c'est ce fichier. Une annotation ne fait rien par
 * elle-même ; elle est inerte tant que personne ne la lit.
 *
 * <p>Indice : par défaut, une annotation Java n'est pas conservée pour être
 * lue à l'exécution.
 */
// TODO Q5 : il manque une ligne, ici même.
public @interface Inject {
}
