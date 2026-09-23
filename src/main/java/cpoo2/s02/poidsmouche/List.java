package cpoo2.s02.poidsmouche;

import java.util.Optional;

/**
 * Ancien exercice hors séance de S02, rattaché à S05 (Poids-mouche). On réécrit un morceau de {@code java.util} : dans ce
 * paquet, {@code List}, {@code ArrayList} et {@code Collections} sont les
 * <b>nôtres</b>, pas ceux du JDK.
 */
public interface List<T> {
	Optional<T> get(int i);

	boolean contains(Object obj);

	void add(T elt);
}
