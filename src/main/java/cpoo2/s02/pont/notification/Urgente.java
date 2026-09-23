package cpoo2.s02.pont.notification;

/**
 * Une notification urgente : le texte part préfixé de {@link #PREFIXE}, puis
 * une <b>relance</b> part aussitôt, préfixée de {@link #RELANCE}. Deux
 * transmissions, sur le même canal.
 */
public final class Urgente extends Notification {
	public static final String PREFIXE = "URGENT : ";
	public static final String RELANCE = "URGENT (relance) : ";

	public Urgente(final Canal canal) {
		super(canal);
	}

	@Override
	public void envoyer(final String destinataire, final String texte) {
		// TODO Q15 : deux transmissions, préfixées
	}
}
