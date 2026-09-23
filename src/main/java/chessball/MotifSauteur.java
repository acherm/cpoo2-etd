package chessball;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/** Le cavalier : il saute, donc rien n'est jamais « entre » son départ et son arrivée (J6). */
public final class MotifSauteur implements Motif {

    private final List<Saut> sauts;

    public MotifSauteur(List<Saut> sauts) {
        this.sauts = List.copyOf(sauts);
    }

    public static MotifSauteur cavalier() {
        return new MotifSauteur(List.of(
                new Saut(1, 2), new Saut(2, 1), new Saut(2, -1), new Saut(1, -2),
                new Saut(-1, -2), new Saut(-2, -1), new Saut(-2, 1), new Saut(-1, 2)));
    }

    @Override public SortedSet<Position> accessibles(Plateau plateau, Position depart) {
        SortedSet<Position> resultat = new TreeSet<>();
        for (Saut s : sauts) {
            Position c = depart.decalee(s.dColonne(), s.dRangee());
            if (plateau.contient(c) && plateau.estVide(c)) resultat.add(c);
        }
        return resultat;
    }

    @Override public boolean vise(Plateau plateau, Position depart, Position cible) {
        if (!plateau.contient(cible)) return false;
        for (Saut s : sauts) {
            if (depart.decalee(s.dColonne(), s.dRangee()).equals(cible)) return true;
        }
        return false;
    }
}
