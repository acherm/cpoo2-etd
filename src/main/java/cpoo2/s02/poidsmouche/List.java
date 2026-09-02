package cpoo2.s02.poidsmouche;

import java.util.Optional;

/**
 * S03 — exercice 6. On réécrit un morceau de {@code java.util} : dans ce
 * paquet, {@code List}, {@code ArrayList} et {@code Collections} sont les
 * <b>nôtres</b>, pas ceux du JDK.
 */
public interface List<T> {
	Optional<T> get(int i);

	boolean contains(Object obj);

	void add(T elt);
}
