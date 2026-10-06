package cpoo2.s04.scenario;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Partie;

/** Terminal : {@code vainqueur bleus}. */
public record Vainqueur(Couleur equipe) implements Condition {

    @Override public boolean evaluer(Partie partie) {
        throw new UnsupportedOperationException("TODO S04 Q2");
    }

    @Override public String toString() { return "vainqueur " + Scenario.nom(equipe); }
}
