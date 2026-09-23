package chessball;

/**
 * Les huit directions d'une ligne du plateau — un tir suit une direction.
 * Mêmes noms et mêmes accesseurs que le {@code Direction} du moteur de CPOO1.
 */
public enum Direction {
    NORD(0, 1), SUD(0, -1), EST(1, 0), OUEST(-1, 0),
    NORD_EST(1, 1), NORD_OUEST(-1, 1), SUD_EST(1, -1), SUD_OUEST(-1, -1);

    private final int dc, dr;

    Direction(int dc, int dr) { this.dc = dc; this.dr = dr; }

    public int dColonne() { return dc; }

    public int dRangee() { return dr; }

    /** Les quatre directions de la tour. */
    public static java.util.List<Direction> lignes() {
        return java.util.List.of(NORD, SUD, EST, OUEST);
    }

    /** Les quatre directions du fou. */
    public static java.util.List<Direction> diagonales() {
        return java.util.List.of(NORD_EST, NORD_OUEST, SUD_EST, SUD_OUEST);
    }

    /** Vrai si un tir dans cette direction progresse vers la rangée de fond de {@code couleur}. */
    public boolean versLaRangeeDe(Couleur couleur) {
        return couleur == Couleur.BLEUS ? dr > 0 : dr < 0;
    }
}
