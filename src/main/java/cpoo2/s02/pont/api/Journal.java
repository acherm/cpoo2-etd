package cpoo2.s02.pont.api;

import java.time.Instant;
import java.util.List;

/** Un journal d'événements horodatés. La date vient de l'{@link Horloge} de la même famille. */
public interface Journal {
	record Entree(Instant quand, String quoi) { }

	void noter(String evenement);

	List<Entree> entrees();
}
