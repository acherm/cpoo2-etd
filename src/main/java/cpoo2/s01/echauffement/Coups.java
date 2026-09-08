package cpoo2.s01.echauffement;

/**
 * S01, partie 0 — exercice 2 : {@code switch} sur une hiérarchie scellée.
 *
 * <p>2.2 — Écrivez {@link #decrire} avec un {@code switch} <b>expression</b>
 * et <b>sans {@code default}</b>. Contrat vérifié par {@link CoupsTest} : la
 * description commence par {@code "déplacement"}, {@code "passe"} ou
 * {@code "tir"} selon le cas.
 *
 * <p>2.4 — Remplacez le {@code switch} par une version avec {@code default},
 * ajoutez un cinquième cas à la hiérarchie : que se passe-t-il ? Qu'avez-vous
 * perdu ? Revenez ensuite à la version sans {@code default}.
 *
 * <p>2.5 — Réécrivez {@code decrire} avec la <b>déconstruction</b> des records
 * ({@code case Deplacement(Position de, Position vers) -> ...}) et une garde
 * {@code when} qui traite à part le déplacement sur place (départ = arrivée) :
 * sa description contient alors {@code "sur place"}.
 */
public final class Coups {

	private Coups() { }

	/** TODO 2.2 puis 2.5. */
	public static String decrire(final Coup c) {
		throw new UnsupportedOperationException("à vous (2.2)");
	}
}
