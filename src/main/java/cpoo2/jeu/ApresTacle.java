package cpoo2.jeu;

import java.util.List;

/** Juste après un tacle : la victime ne peut ni tacler son tacleur, ni sauter par-dessus lui. */
public record ApresTacle(Position tacleur, Position victime) implements EtatPartie {

    @Override public List<Coup> coupsLegaux(Partie partie) {
        throw new UnsupportedOperationException("TODO S05 Q3");
    }

}
