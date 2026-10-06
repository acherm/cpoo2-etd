package cpoo2.s03.ia;

import java.util.List;
import java.util.Random;

import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;

/**
 * Un coup légal au hasard. Mesuré sur 2000 parties aléatoire contre aléatoire : un but sur deux
 * est marqué contre son camp.
 */
public final class IAAleatoire implements IA {

    private final Random hasard;

    public IAAleatoire(Random hasard) {
        this.hasard = hasard;
    }

    @Override public Coup choisirCoup(Jeu jeu) {
        List<Coup> coups = jeu.coupsLegaux();
        if (coups.isEmpty()) {
            throw new IllegalStateException("aucun coup légal");
        }
        return coups.get(hasard.nextInt(coups.size()));
    }
}
