package cpoo2.s02.pont.impl;

import cpoo2.s02.pont.api.Horloge;
import cpoo2.s02.pont.api.Journal;

import java.util.ArrayList;
import java.util.List;

/** Famille « système » : journal écrit sur la console, daté par l'horloge qu'on lui donne. */
public final class JournalConsole implements Journal {
	private final Horloge horloge;
	private final List<Entree> entrees = new ArrayList<>();

	public JournalConsole(final Horloge horloge) {
		super();
		this.horloge = horloge;
	}

	@Override
	public void noter(final String evenement) {
		final Entree e = new Entree(horloge.maintenant(), evenement);
		entrees.add(e);
		System.out.println("[JOURNAL] " + e.quand() + " " + e.quoi());
	}

	@Override
	public List<Entree> entrees() {
		return List.copyOf(entrees);
	}
}
