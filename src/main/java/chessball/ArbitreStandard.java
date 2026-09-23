package chessball;

import java.util.function.Function;

/** Le match du cahier des charges : deux mi-temps, l'engagement alterne (J12). Non finale : un espion de test la sous-classe. */
public class ArbitreStandard extends Arbitre {

    public ArbitreStandard(Function<Couleur, Partie> nouvelleMiTemps, int toursParMiTemps, TirageAuSort tirage) {
        super(nouvelleMiTemps, toursParMiTemps, tirage);
    }

    @Override protected Couleur engageurDeLaMiTemps(int miTemps, Couleur premierEngageur) {
        // TODO S06 Q9 bis : J12, l'engagement alterne d'une mi-temps à l'autre
        throw new UnsupportedOperationException("TODO S06 Q9 bis");
    }
}
