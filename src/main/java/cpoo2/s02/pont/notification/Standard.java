package cpoo2.s02.pont.notification;

/** Une notification ordinaire : le texte part tel quel, une fois. */
public final class Standard extends Notification {
	public Standard(final Canal canal) {
		super(canal);
	}

	@Override
	public void envoyer(final String destinataire, final String texte) {
		// TODO Q15 : déléguer au canal
	}
}
