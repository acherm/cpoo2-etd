package chessball;

import java.util.LinkedHashMap;
import java.util.Map;

/** Un placement initial de référence, conforme au cahier des charges (J10 : libre sur ses deux rangées). */
public final class Placements {

    private Placements() {}

    /** Le plateau 8×8 de départ, ballon non encore engagé. */
    public static Plateau standard() {
        Plateau plateau = new Plateau(8);
        for (Map.Entry<String, Piece> e : standardBleus().entrySet()) plateau.placer(e.getValue(), Position.of(e.getKey()));
        for (Map.Entry<String, Piece> e : standardRouges().entrySet()) plateau.placer(e.getValue(), Position.of(e.getKey()));
        return plateau;
    }

    public static Map<String, Piece> standardBleus() {
        Map<String, Piece> m = new LinkedHashMap<>();
        m.put("a1", new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT1"));
        m.put("c1", new Piece(Couleur.BLEUS, TypePiece.FOU, "bF1"));
        m.put("d1", new Piece(Couleur.BLEUS, TypePiece.DAME, "bD"));
        m.put("f1", new Piece(Couleur.BLEUS, TypePiece.FOU, "bF2"));
        m.put("h1", new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT2"));
        m.put("e2", new Piece(Couleur.BLEUS, TypePiece.CAVALIER, "bC"));
        return m;
    }

    public static Map<String, Piece> standardRouges() {
        Map<String, Piece> m = new LinkedHashMap<>();
        m.put("a8", new Piece(Couleur.ROUGES, TypePiece.TOUR, "rT1"));
        m.put("c8", new Piece(Couleur.ROUGES, TypePiece.FOU, "rF1"));
        m.put("d8", new Piece(Couleur.ROUGES, TypePiece.DAME, "rD"));
        m.put("f8", new Piece(Couleur.ROUGES, TypePiece.FOU, "rF2"));
        m.put("h8", new Piece(Couleur.ROUGES, TypePiece.TOUR, "rT2"));
        m.put("e7", new Piece(Couleur.ROUGES, TypePiece.CAVALIER, "rC"));
        return m;
    }
}
