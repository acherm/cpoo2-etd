package cpoo2.s02.pont.impl;

import cpoo2.s02.pont.api.Horloge;

import java.time.Instant;

/** Famille « système » : l'heure de la machine. */
public final class HorlogeSysteme implements Horloge {
	@Override
	public Instant maintenant() {
		return Instant.now();
	}
}
