package cpoo2.s04.scenario;

import java.util.ArrayList;
import java.util.List;

import cpoo2.jeu.Partie;

/**
 * S04, Q10 : jouer le scénario sur une vraie partie. Un coup est joué s'il est à l'équipe au trait
 * et si la partie l'accepte, une vérification est évaluée (l'Interpréteur de l'exercice 1). Chaque
 * échec est noté, avec le numéro de l'instruction, et l'exécution continue.
 */
public final class Executeur implements VisiteurDeScenario {

    private final Partie partie;
    private final List<String> echecs = new ArrayList<>();
    private int numero;   // le numéro de l'instruction en cours

    public Executeur(Partie partie) {
        this.partie = partie;
    }

    /** Joue le scénario depuis sa position de départ. */
    public static Executeur executer(Scenario scenario) {
        Executeur e = new Executeur(new Partie(scenario.depart(), scenario.trait()));
        scenario.parcourir(e);
        return e;
    }

    public Partie partie() { return partie; }

    /** Les échecs, « instruction 3 (rouges saut c2-c4) : coup refusé ». Vide si tout s'est bien passé. */
    public List<String> echecs() { return List.copyOf(echecs); }

    // TODO S04 Q10 : un coup est joué s'il est à l'équipe au trait et si la partie l'accepte,
    // une vérification est évaluée (l'Interpréteur de l'exercice 1). Chaque échec est noté par
    // echec(...), avec le numéro de l'instruction (à incrémenter à chaque visite).

    @Override public void visiterDeplacer(Deplacer d) { throw new UnsupportedOperationException("TODO S04 Q10"); }

    @Override public void visiterPousser(Pousser p) { throw new UnsupportedOperationException("TODO S04 Q10"); }

    @Override public void visiterTacler(Tacler t) { throw new UnsupportedOperationException("TODO S04 Q10"); }

    @Override public void visiterSauter(Sauter s) { throw new UnsupportedOperationException("TODO S04 Q10"); }

    @Override public void visiterVerifier(Verifier v) { throw new UnsupportedOperationException("TODO S04 Q10"); }

    private void echec(Instruction i, String pourquoi) {
        echecs.add("instruction " + numero + " (" + i + ") : " + pourquoi);
    }
}
