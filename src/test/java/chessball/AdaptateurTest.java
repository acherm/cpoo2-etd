package chessball;

import chessball.externe.ChessAttackEngine;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S03 ex. 1, Q2 : l'oracle de l'Adaptateur. Rouge sur le squelette.
 *
 * <p>L'oracle est le motif maison : {@code Motifs.pour(type)} et {@code MotifExterne.pour(type)}
 * doivent répondre la même chose, pour les douze pièces du placement standard et pour un
 * chemin bloqué. Les trois traductions sont isolées : les coordonnées, l'occupation, et la
 * sémantique (la bibliothèque attaque une case occupée, chez nous elle n'est jamais accessible).
 * Le dernier test regarde la structure : la bibliothèque n'a pas bougé, l'adaptateur est un Motif.</p>
 */
class AdaptateurTest {

    static final Position A1 = Position.of("a1"), A3 = Position.of("a3"), A5 = Position.of("a5"), D4 = Position.of("d4");

    static Plateau tourEn(String case_) {
        Plateau p = new Plateau(8);
        p.placer(new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT"), Position.of(case_));
        return p;
    }

    @Test
    void lesCoordonneesSontTraduites() {
        Plateau p = tourEn("a1");
        assertEquals(Motifs.pour(TypePiece.TOUR).accessibles(p, A1), MotifExterne.pour(TypePiece.TOUR).accessibles(p, A1),
                "une tour en a1 sur un plateau vide : 14 cases, les mêmes que le motif maison");
    }

    @Test
    void lOccupationEstTraduite() {
        Plateau p = tourEn("a1");
        p.placer(new Piece(Couleur.ROUGES, TypePiece.FOU, "rF"), A3);
        assertFalse(MotifExterne.pour(TypePiece.TOUR).accessibles(p, A1).contains(A5), "a3 bloque la colonne");
        assertEquals(Motifs.pour(TypePiece.TOUR).accessibles(p, A1), MotifExterne.pour(TypePiece.TOUR).accessibles(p, A1));
    }

    @Test
    void laBibliothequeAttaqueLaCaseOccupeeMaisPasNous() {
        Plateau p = tourEn("a1");
        p.placer(new Piece(Couleur.ROUGES, TypePiece.FOU, "rF"), A3);
        long attaquees = new ChessAttackEngine().attacks(ChessAttackEngine.ROOK, ChessAttackEngine.square(0, 0),
                1L << ChessAttackEngine.square(0, 0) | 1L << ChessAttackEngine.square(0, 2));
        assertTrue((attaquees & (1L << ChessAttackEngine.square(0, 2))) != 0L, "aux échecs, a3 est attaquée : prenable");
        assertFalse(MotifExterne.pour(TypePiece.TOUR).accessibles(p, A1).contains(A3),
                "chez nous, une case occupée n'est jamais une destination (J6)");
        assertTrue(MotifExterne.pour(TypePiece.TOUR).vise(p, A1, A3), "mais elle est visée : c'est ce que les passes utilisent");
    }

    @Test
    void lesDouzePiecesDuPlacementStandardSontDAccord() {
        Plateau p = Placements.standard();
        int desaccords = 0;
        for (var e : p.occupation().entrySet()) {
            Motif maison = Motifs.de(e.getValue());
            Motif externe = MotifExterne.pour(e.getValue().type());
            if (!maison.accessibles(p, e.getKey()).equals(externe.accessibles(p, e.getKey()))) desaccords++;
            for (int col = 0; col < 8; col++) {
                for (int rang = 0; rang < 8; rang++) {
                    Position cible = new Position(col, rang);
                    if (maison.vise(p, e.getKey(), cible) != externe.vise(p, e.getKey(), cible)) desaccords++;
                }
            }
        }
        assertEquals(0, desaccords, "l'adaptateur et le motif maison donnent les mêmes réponses");
        assertEquals(Motifs.pour(TypePiece.DAME).accessibles(new Plateau(8), D4).size(),
                MotifExterne.pour(TypePiece.DAME).accessibles(new Plateau(8), D4).size(), "la dame en d4 : 27 cases");
    }

    @Test
    void unPlateauQueLaBibliothequeNeConnaitPasEstRefuseNettement() {
        Plateau dix = new Plateau(10);
        dix.placer(new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT"), A1);
        assertThrows(IllegalStateException.class, () -> MotifExterne.pour(TypePiece.TOUR).accessibles(dix, A1),
                "la limite 8x8 est celle de la bibliothèque : l'adaptateur la dit, il ne la cache pas");
    }

    @Test
    void laBibliothequeNAPasBougeEtLAdaptateurEstUnMotif() {
        assertTrue(Modifier.isFinal(ChessAttackEngine.class.getModifiers()), "la bibliothèque est finale : on ne la sous-classe pas");
        assertFalse(Motif.class.isAssignableFrom(ChessAttackEngine.class), "la bibliothèque ignore notre interface");
        assertTrue(Motif.class.isAssignableFrom(MotifExterne.class), "l'adaptateur implémente la cible");
        assertTrue(Arrays.stream(MotifExterne.class.getDeclaredFields()).anyMatch(f -> f.getType() == ChessAttackEngine.class),
                "l'adaptateur détient l'adapté");
    }
}
