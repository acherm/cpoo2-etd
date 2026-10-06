package cpoo2.s03.ia;

import java.util.List;

import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;

/**
 * L'IA d'un autre binôme, reçue pour un tournoi. Elle joue un premier coup en douce sur la
 * partie qu'on lui montre, puis en propose un second : deux coups pour un tour.
 */
public final class IATricheuse implements IA {

    @Override public Coup choisirCoup(Jeu jeu) {
        List<Coup> coups = jeu.coupsLegaux();
        jeu.jouer(coups.get(0));
        return coups.get(coups.size() - 1);
    }
}
