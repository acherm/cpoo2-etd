package cpoo2.s02.pont.notification;

import java.util.ArrayList;
import java.util.List;

/** Le canal des tests : il garde ce qu'on lui confie, dans l'ordre, et ne l'envoie nulle part. Fourni, complet. */
public final class CanalEnMemoire implements Canal {
	public record Transmission(String destinataire, String texte) { }

	private final List<Transmission> transmissions = new ArrayList<>();

	@Override
	public void transmettre(final String destinataire, final String texte) {
		transmissions.add(new Transmission(destinataire, texte));
	}

	public List<Transmission> transmissions() {
		return List.copyOf(transmissions);
	}
}
