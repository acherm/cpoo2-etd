package cpoo2.jeu;

import java.util.List;

/** La partie est nulle : l'équipe au trait n'avait aucun coup, ou elle durait trop sans poussée. */
public record Nulle() implements EtatPartie {

    @Override public List<Coup> coupsLegaux(Partie partie) { throw new UnsupportedOperationException("TODO S05 Q3"); }

    @Override public boolean estTerminee() { throw new UnsupportedOperationException("TODO S05 Q3"); }

    @Override public EtatPartie apres(Coup coup, Partie partie) {
        throw new IllegalStateException("la partie est finie");
    }
}
