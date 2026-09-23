package cpoo2.s02.pont.notification;

import java.util.List;

/**
 * Un canal de production : un SMS fait <b>160 caractères au plus</b>, le canal
 * tronque ce qui dépasse. Ce que l'opérateur a reçu se consulte avec
 * {@link #journalOperateur()} (c'est ce que le test lit).
 */
public final class CanalSms implements Canal {
	public static final int LIMITE = 160;

	@Override
	public void transmettre(final String destinataire, final String texte) {
		// TODO Q14 : tronquer à LIMITE, puis garder « destinataire : texte » dans le journal
	}

	/** Les SMS partis, tronqués, dans l'ordre. */
	public List<String> journalOperateur() {
		return List.of();   // TODO Q14
	}
}
