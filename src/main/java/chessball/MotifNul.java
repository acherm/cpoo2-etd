package chessball;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Le motif d'une pièce qui ne peut rien faire (pièce blessée, variante « gardien cloué »,
 * type inconnu chargé depuis un fichier). Aucun appelant n'a besoin de tester quoi que ce soit.
 */
public final class MotifNul implements Motif {

    public static final MotifNul INSTANCE = new MotifNul();

    private MotifNul() {}

    @Override public SortedSet<Position> accessibles(Plateau plateau, Position depart) {
        return Collections.unmodifiableSortedSet(new TreeSet<>());
    }

    @Override public boolean vise(Plateau plateau, Position depart, Position cible) { return false; }
}
