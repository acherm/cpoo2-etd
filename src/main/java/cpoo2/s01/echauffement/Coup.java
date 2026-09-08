package cpoo2.s01.echauffement;

/**
 * S01, partie 0 — exercice 2 : une hiérarchie <b>scellée</b>.
 *
 * <p>{@code permits} énumère les seules implémentations autorisées : le
 * compilateur sait que la liste est complète, et peut donc vérifier qu'un
 * {@code switch} les couvre toutes (voir {@link Coups#decrire}).
 *
 * <p>2.3 — Ajoutez ici un quatrième cas {@code Roque(boolean petit)} sans
 * toucher à {@code decrire}, compilez, et recopiez le message d'erreur exact.
 * Puis retirez-le (ou complétez {@code decrire}) pour que les tests compilent.
 */
public sealed interface Coup permits Deplacement, Passe, Tir { }
