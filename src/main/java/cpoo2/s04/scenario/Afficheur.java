package cpoo2.s04.scenario;

import java.util.ArrayList;
import java.util.List;

/** S04, Q8 : le scénario, ligne par ligne, tel qu'on l'écrirait. */
public final class Afficheur implements VisiteurDeScenario {

    private final List<String> lignes = new ArrayList<>();

    /** Les lignes des instructions visitées, une par ligne. */
    public String texte() { return String.join("\n", lignes); }

    @Override public void visiterDeplacer(Deplacer d) { throw new UnsupportedOperationException("TODO S04 Q8"); }

    @Override public void visiterPousser(Pousser p) { throw new UnsupportedOperationException("TODO S04 Q8"); }

    @Override public void visiterTacler(Tacler t) { throw new UnsupportedOperationException("TODO S04 Q8"); }

    @Override public void visiterSauter(Sauter s) { throw new UnsupportedOperationException("TODO S04 Q8"); }

    @Override public void visiterVerifier(Verifier v) { throw new UnsupportedOperationException("TODO S04 Q8"); }
}
