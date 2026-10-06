package cpoo2.jeu;

import java.util.List;
import java.util.Optional;

/** Un but a été marqué : la partie est finie, plus aucun coup n'est légal. */
public record Gagnee(Couleur gagnant) implements EtatPartie {

    @Override public List<Coup> coupsLegaux(Partie partie) { throw new UnsupportedOperationException("TODO S05 Q3"); }

    @Override public boolean estTerminee() { throw new UnsupportedOperationException("TODO S05 Q3"); }

    @Override public Optional<Couleur> vainqueur() { throw new UnsupportedOperationException("TODO S05 Q3"); }

    @Override public EtatPartie apres(Coup coup, Partie partie) {
        throw new IllegalStateException("la partie est finie");
    }
}
