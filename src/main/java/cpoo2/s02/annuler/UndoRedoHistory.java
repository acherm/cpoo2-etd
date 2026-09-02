package cpoo2.s02.annuler;

import java.util.Optional;

/**
 * S03 — exercice 3. Collecte les actions annulables et pilote annuler / refaire.
 *
 * <p>Toute la spécification est dans {@code UndoRedoHistoryTest} : quatorze
 * tests, tous rouges. Lisez-les avant d'écrire une ligne — c'est l'énoncé.
 *
 * <p>Deux points que les tests imposent et qu'on oublie toujours :
 * <ul>
 *   <li>ajouter une action après une annulation <b>vide</b> la pile de refaire ;</li>
 *   <li>au-delà de {@code sizeMax}, la plus <b>ancienne</b> action est jetée.</li>
 * </ul>
 */
public class UndoRedoHistory {
	public void add(final Undoable undoable) {
	}

	public void undo() {
	}

	public void redo() {
	}

	public int getNbUndoables() {
		return 0;
	}

	public int getNbRedoables() {
		return 0;
	}

	public void setSizeMax(final int size) {
	}

	public Optional<Undoable> getLastUndo() {
		return Optional.empty();
	}
}
