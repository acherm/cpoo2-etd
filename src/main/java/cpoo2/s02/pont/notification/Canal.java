package cpoo2.s02.pont.notification;

/**
 * S02, exercice 4 : l'<b>implémenteur</b> du Pont. Un canal sait transmettre
 * un texte à quelqu'un, et rien d'autre. Il ne sait pas ce qu'est une
 * notification standard ou urgente : c'est l'affaire de l'autre hiérarchie.
 */
public interface Canal {
	void transmettre(String destinataire, String texte);
}
