package chessball;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/** Tour et fou : on avance de proche en proche jusqu'à sortir du plateau ou buter sur une pièce. */
public final class MotifGlissant implements Motif {

    private final List<Direction> directions;

    public MotifGlissant(List<Direction> directions) {
        this.directions = List.copyOf(directions);
    }

    @Override public SortedSet<Position> accessibles(Plateau plateau, Position depart) {
        SortedSet<Position> resultat = new TreeSet<>();
        for (Direction d : directions) {
            Position c = depart.decalee(d.dColonne(), d.dRangee());
            while (plateau.contient(c) && plateau.estVide(c)) {
                resultat.add(c);
                c = c.decalee(d.dColonne(), d.dRangee());
            }
        }
        return resultat;
    }

    @Override public boolean vise(Plateau plateau, Position depart, Position cible) {
        if (!plateau.contient(cible) || depart.equals(cible)) return false;
        for (Direction d : directions) {
            Position c = depart.decalee(d.dColonne(), d.dRangee());
            while (plateau.contient(c)) {
                if (c.equals(cible)) return true;
                if (!plateau.estVide(c)) break;          // obstacle : la trajectoire s'arrête là
                c = c.decalee(d.dColonne(), d.dRangee());
            }
        }
        return false;
    }

    @Override public List<Direction> directions() { return directions; }
}
