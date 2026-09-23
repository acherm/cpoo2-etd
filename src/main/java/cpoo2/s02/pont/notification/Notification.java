package cpoo2.s02.pont.notification;

/**
 * S02, exercice 4 : l'<b>abstraction</b> du Pont. Une notification décide
 * <b>quoi</b> dire (le texte, un préfixe, une relance) et confie le
 * <b>comment</b> à un {@link Canal} qu'elle <b>détient</b>.
 *
 * <p>TODO Q15 : le champ qui tient le canal (typé par l'interface, jamais par
 * une classe concrète), et le constructeur qui le reçoit. Puis les deux
 * sous-classes {@link Standard} et {@link Urgente}.
 */
public abstract class Notification {

	protected Notification(final Canal canal) {
		super();
		// TODO Q15
	}

	/** Prépare le texte selon le type de notification, puis le confie au canal. */
	public abstract void envoyer(String destinataire, String texte);
}
