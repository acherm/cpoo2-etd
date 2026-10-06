package cpoo2.jeu;

import java.util.List;

/** La partie se joue, sans souvenir particulier : tous les coups possibles sont légaux. */
public record EnJeu() implements EtatPartie {

    @Override public List<Coup> coupsLegaux(Partie partie) {
        throw new UnsupportedOperationException("TODO S05 Q3");
    }
}
