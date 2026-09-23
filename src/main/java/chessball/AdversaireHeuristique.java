package chessball;

import java.util.List;
import java.util.Random;

/** Même interface, autre politique : tirer si ça marque, sinon passer vers l'avant, sinon se rapprocher du ballon. */
public final class AdversaireHeuristique implements StrategieAdversaire {

    private final Random random;

    public AdversaireHeuristique(Random random) { this.random = random; }

    /**
     * TODO S06 Q8 : noter chaque coup possible et rendre le mieux noté, ou un coup au hasard si
     * aucune note n'est positive. Barème suggéré : un tir qui marque 100, un tir qui sort sur le
     * côté 1, un tir reçu par une coéquipière 5, intercepté par l'adversaire moins 10 (simuler la
     * trajectoire sur le plateau reçu, c'est une copie) ; une passe 10 plus l'avancée vers le but
     * adverse ; un déplacement, moins la distance à la case du ballon.
     */
    @Override public Coup choisirCoup(Plateau plateau, Couleur trait) {
        throw new UnsupportedOperationException("TODO S06 Q8");
    }

    @Override public String toString() { return "heuristique"; }
}
