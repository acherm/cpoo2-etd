package chessball;

import java.util.function.Function;

/** La variante « une mi-temps » du feature model : deux méthodes, quatre lignes. */
public class ArbitreEnUneMiTemps extends Arbitre {

    public ArbitreEnUneMiTemps(Function<Couleur, Partie> nouvelleMiTemps, int toursParMiTemps, TirageAuSort tirage) {
        super(nouvelleMiTemps, toursParMiTemps, tirage);
    }

    // TODO S06 Q9 bis : redéfinir les deux trous, et rien d'autre (combien de lignes ?)

    @Override protected Couleur engageurDeLaMiTemps(int miTemps, Couleur premierEngageur) {
        throw new UnsupportedOperationException("TODO S06 Q9 bis");
    }
}
