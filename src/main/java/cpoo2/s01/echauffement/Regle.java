package cpoo2.s01.echauffement;

/**
 * S01, partie 0 — exercice 3.3 : une interface <b>fonctionnelle</b>, c'est une
 * interface à une seule méthode abstraite. Le compilateur accepte alors une
 * lambda partout où une {@code Regle} est attendue : voir {@link Regles}.
 */
@FunctionalInterface
public interface Regle {
	boolean accepte(Coup c);
}
