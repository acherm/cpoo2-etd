package chessball;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Tous les coups légaux du camp qui a le trait, dans l'état courant de la partie : le carburant
 * de toute IA, et le générateur de base des propriétés (séance 9).
 *
 * <p>On génère <b>depuis l'état</b>, jamais au hasard puis en filtrant : sur un plateau en cours
 * de partie, la proportion de coups légaux parmi tous les coups possibles tombe à quelques pour
 * cent, et un filtre tire, jette, retire, puis abandonne.</p>
 *
 * <p>L'ordre est déterministe (positions triées) : deux appels sur le même état rendent la même
 * liste, ce qui rend les tirages rejouables à graine égale.</p>
 */
public final class GenerateurDeCoups {

    private GenerateurDeCoups() {}

    /** Les coups légaux du camp au trait. Vide seulement si le camp ne peut plus rien faire. */
    public static List<Coup> coupsLegaux(Partie partie) {
        return coupsLegaux(partie.plateau(), partie.trait());
    }

    /** Les coups légaux d'un camp donné sur un plateau donné. */
    public static List<Coup> coupsLegaux(Plateau plateau, Couleur couleur) {
        List<Coup> coups = new ArrayList<>();
        Optional<Position> ballon = plateau.positionDuBallon();
        if (ballon.isEmpty()) {                          // J2 : il faut d'abord engager
            int milieu = plateau.taille() / 2;
            for (int colonne : new int[] {milieu - 1, milieu}) {
                for (int rangee : new int[] {milieu - 1, milieu}) {
                    Position centrale = new Position(colonne, rangee);
                    if (plateau.estVide(centrale)) coups.add(new Coup.PoserBallon(centrale));
                }
            }
            return coups;
        }
        List<Piece> pieces = new ArrayList<>(plateau.piecesDe(couleur));
        pieces.sort((a, b) -> plateau.positionDe(a).orElseThrow().compareTo(plateau.positionDe(b).orElseThrow()));
        for (Piece piece : pieces) {
            Position depart = plateau.positionDe(piece).orElseThrow();
            for (Position arrivee : Motifs.de(piece).accessibles(plateau, depart)) {
                coups.add(new Coup.Deplacement(depart, arrivee));
            }
        }
        Piece porteuse = plateau.porteuse().orElse(null);
        if (porteuse != null && porteuse.couleur() == couleur) {
            Position depart = plateau.positionDe(porteuse).orElseThrow();
            Motif motif = Motifs.de(porteuse);
            for (Piece coequipiere : pieces) {
                if (coequipiere == porteuse) continue;
                Position cible = plateau.positionDe(coequipiere).orElseThrow();
                if (motif.vise(plateau, depart, cible)) coups.add(new Coup.Passe(depart, cible));
            }
            for (Direction d : motif.directions()) {
                if (d.versLaRangeeDe(couleur)) coups.add(new Coup.Tir(depart, d));
            }
        }
        return coups;
    }
}
