package cpoo2.s02.poidsmouche;

import java.util.Arrays;
import java.util.Optional;

/** Une implémentation ordinaire, donnée pour le contexte. */
public class ArrayList<T> implements List<T> {
	T[] data;

	@SuppressWarnings("unchecked")
	public ArrayList() {
		data = (T[]) new Object[0];
	}

	@Override
	public Optional<T> get(final int i) {
		return i >= 0 && i < data.length ? Optional.ofNullable(data[i]) : Optional.empty();
	}

	@Override
	public boolean contains(final Object obj) {
		return Arrays.stream(data).anyMatch(elt -> elt.equals(obj));
	}

	@Override
	public void add(final T elt) {
		data = Arrays.copyOf(data, data.length + 1);
		data[data.length - 1] = elt;
	}
}
