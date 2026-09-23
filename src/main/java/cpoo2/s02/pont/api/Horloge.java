package cpoo2.s02.pont.api;

import java.time.Instant;

/** Ce qu'il faut savoir de l'heure. En test, elle ne doit pas avancer toute seule. */
public interface Horloge {
	Instant maintenant();
}
