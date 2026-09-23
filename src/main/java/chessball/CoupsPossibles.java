package chessball;

import java.util.ArrayList;
import java.util.List;

/**
 * Tous les coups que le jeu de base accepte pour une équipe dans une position : le carburant de
 * toute IA. <b>Fourni</b> : ce n'est pas un patron, c'est de la plomberie. La liste est exactement
 * celle que {@link Regles#duJeuDeBase()} accepte, dans l'ordre : engagement s'il faut poser le
 * ballon, sinon déplacements, passes, tirs.
 */
public final class CoupsPossibles {

    private CoupsPossibles() {}

    public static List<Coup> pour(Plateau plateau, Couleur trait) {
        List<Coup> coups = new ArrayList<>();
        if (plateau.positionDuBallon().isEmpty()) {          // J2 : il faut d'abord engager
            int milieu = plateau.taille() / 2;
            for (int colonne : new int[]{milieu - 1, milieu}) {
                for (int rangee : new int[]{milieu - 1, milieu}) {
                    Position c = new Position(colonne, rangee);
                    if (plateau.estVide(c)) coups.add(new Coup.PoserBallon(c));
                }
            }
            return coups;
        }
        for (Piece piece : plateau.piecesDe(trait)) {
            Position depart = plateau.positionDe(piece).orElseThrow();
            for (Position arrivee : Motifs.de(piece).accessibles(plateau, depart)) {
                coups.add(new Coup.Deplacement(depart, arrivee));
            }
        }
        Piece porteuse = plateau.porteuse().orElse(null);
        if (porteuse != null && porteuse.couleur() == trait) {
            Position depart = plateau.positionDe(porteuse).orElseThrow();
            Motif motif = Motifs.de(porteuse);
            for (Piece coequipiere : plateau.piecesDe(trait)) {
                if (coequipiere == porteuse) continue;
                Position cible = plateau.positionDe(coequipiere).orElseThrow();
                if (motif.vise(plateau, depart, cible)) coups.add(new Coup.Passe(depart, cible));
            }
            for (Direction d : motif.directions()) {
                if (d.versLaRangeeDe(trait)) coups.add(new Coup.Tir(depart, d));
            }
        }
        return coups;
    }
}
