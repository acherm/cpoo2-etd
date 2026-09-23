package chessball;

import java.util.ArrayList;
import java.util.List;

/** Où l'on écrit ce qui s'est passé. Une implémentation en mémoire, créée et injectée par celui qui monte la partie. */
public interface Journal {

    void ajouter(String ligne);

    List<String> lignes();

    final class EnMemoire implements Journal {
        private final List<String> lignes = new ArrayList<>();
        @Override public void ajouter(String ligne) { lignes.add(ligne); }
        @Override public List<String> lignes() { return List.copyOf(lignes); }
    }
}
