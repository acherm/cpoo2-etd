package cpoo2.s02.annuler;

/** Une action réalisée par l'utilisateur, et qui sait revenir en arrière. */
public interface Undoable {
	void undo();
	void redo();
}
