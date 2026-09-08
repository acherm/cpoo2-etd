package cpoo2.s01.echauffement;

import java.util.List;

/**
 * S01, partie 0 — exercice 1.3 : l'immuabilité d'un {@code record} est
 * <b>superficielle</b>.
 *
 * <p>Tel quel, ce record compile et semble immuable. {@link EquipeTest}
 * montre deux façons de le modifier après coup. Corrigez-le pour que les deux
 * échouent. Deux corrections sont acceptables : donnez-les toutes les deux
 * (dans un commentaire pour la seconde) et dites laquelle vous préférez.
 */
public record Equipe(String nom, List<String> pieces) {

	// TODO 1.3 : d'abord constater la fuite (lancez EquipeTest), puis corriger.

}
