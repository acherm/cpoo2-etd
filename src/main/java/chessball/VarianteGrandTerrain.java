package chessball;

import java.util.Map;
import java.util.Random;

/**
 * La feature « 10×10 » : un plateau plus grand, donc un placement adapté, et l'IA simple, parce
 * que l'heuristique n'a pas été réglée pour ce terrain. Les trois vont ensemble.
 */
public final class VarianteGrandTerrain extends Variante {

    @Override public String nom() { return "grand terrain 10x10"; }

    // TODO S06 Q14 : le plateau grand() ci-dessous, le jeu de base, et l'IA que ce terrain supporte

    @Override protected Plateau creerPlateau() { throw new UnsupportedOperationException("TODO S06 Q14"); }

    @Override protected Regle creerRegle() { throw new UnsupportedOperationException("TODO S06 Q14"); }

    @Override public StrategieAdversaire creerAdversaire(Random random) { throw new UnsupportedOperationException("TODO S06 Q14"); }

    /** Les six pièces de chaque équipe, écartées sur dix colonnes. */
    static Plateau grand() {
        Plateau plateau = new Plateau(10);
        for (Map.Entry<String, Piece> e : Placements.standardBleus().entrySet()) {
            plateau.placer(e.getValue(), Position.of(e.getKey()));
        }
        Map<String, Piece> rouges = Placements.standardRouges();
        plateau.placer(rouges.get("a8"), Position.of("a10"));
        plateau.placer(rouges.get("c8"), Position.of("c10"));
        plateau.placer(rouges.get("d8"), Position.of("d10"));
        plateau.placer(rouges.get("f8"), Position.of("f10"));
        plateau.placer(rouges.get("h8"), Position.of("j10"));
        plateau.placer(rouges.get("e7"), Position.of("e9"));
        Piece tourBleue = plateau.pieceEn(Position.of("h1")).orElseThrow();
        plateau.deplacer(Position.of("h1"), Position.of("j1"));
        assert plateau.pieceEn(Position.of("j1")).orElseThrow() == tourBleue;
        return plateau;
    }
}
