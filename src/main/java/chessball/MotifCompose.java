package chessball;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/** La dame : ni un troisième motif écrit à la main, ni une sous-classe — l'union de deux motifs. */
public final class MotifCompose implements Motif {

    private final List<Motif> parties;

    public MotifCompose(Motif... parties) {
        this.parties = List.of(parties);
    }

    @Override public SortedSet<Position> accessibles(Plateau plateau, Position depart) {
        SortedSet<Position> resultat = new TreeSet<>();
        for (Motif m : parties) resultat.addAll(m.accessibles(plateau, depart));
        return resultat;
    }

    @Override public boolean vise(Plateau plateau, Position depart, Position cible) {
        for (Motif m : parties) {
            if (m.vise(plateau, depart, cible)) return true;
        }
        return false;
    }

    @Override public List<Direction> directions() {
        List<Direction> toutes = new ArrayList<>();
        for (Motif m : parties) toutes.addAll(m.directions());
        return List.copyOf(toutes);
    }
}
