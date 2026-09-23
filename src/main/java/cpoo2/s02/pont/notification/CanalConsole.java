package cpoo2.s02.pont.notification;

/** Un canal de production : la console tient lieu de serveur de courriel. */
public final class CanalConsole implements Canal {
	@Override
	public void transmettre(final String destinataire, final String texte) {
		// TODO Q14 : System.out, préfixé du destinataire
	}
}
