package cpoo2.s03.ia;

import java.util.EnumMap;
import java.util.Map;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;

/**
 * Deux IA s'affrontent sur une partie, un tour à la fois : l'IA au trait propose un coup, la
 * rencontre le joue. C'est le client du Proxy de l'exercice 3.
 */
public final class Rencontre {

    private final Jeu partie;
    private final Map<Couleur, IA> ias = new EnumMap<>(Couleur.class);

    public Rencontre(Jeu partie, IA bleus, IA rouges) {
        this.partie = partie;
        ias.put(Couleur.BLEUS, bleus);
        ias.put(Couleur.ROUGES, rouges);
    }

    /** Un tour : demander son coup à l'IA au trait, puis le jouer. Rend le coup joué. */
    public Coup tourSuivant() {
        IA ia = ias.get(partie.trait());
        // TODO S03 Q15 : l'IA ne reçoit plus la partie elle-même, seulement une vue en lecture seule
        Coup coup = ia.choisirCoup(partie);
        if (!partie.jouer(coup)) {
            throw new IllegalStateException("coup refusé par l'arbitre : " + coup);
        }
        return coup;
    }
}
