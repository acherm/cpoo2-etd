package chessball;

import java.util.List;
import java.util.Random;

/** La plus simple des façons de jouer. Interchangeable avec n'importe quelle autre. */
public final class AdversaireAleatoire implements StrategieAdversaire {

    private final Random random;

    /** Le hasard est <b>reçu</b> : une graine fixée rend la manche rejouable. */
    public AdversaireAleatoire(Random random) { this.random = random; }

    @Override public Coup choisirCoup(Plateau plateau, Couleur trait) {
        // TODO S06 Q8 : un coup au hasard parmi CoupsPossibles.pour(plateau, trait)
        //   (une liste vide est une erreur : lever IllegalStateException)
        throw new UnsupportedOperationException("TODO S06 Q8");
    }

    @Override public String toString() { return "aléatoire"; }
}
