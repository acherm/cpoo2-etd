package chessball;

import java.util.Random;

/** La configuration de base du feature model : 8×8, jeu de base, IA heuristique. */
public final class VarianteStandard extends Variante {

    @Override public String nom() { return "standard 8x8"; }

    // TODO S06 Q14 : le placement standard, le jeu de base, l'IA heuristique

    @Override protected Plateau creerPlateau() { throw new UnsupportedOperationException("TODO S06 Q14"); }

    @Override protected Regle creerRegle() { throw new UnsupportedOperationException("TODO S06 Q14"); }

    @Override public StrategieAdversaire creerAdversaire(Random random) { throw new UnsupportedOperationException("TODO S06 Q14"); }
}
