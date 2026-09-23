package chessball.externe;

/**
 * Simule une bibliothèque tierce d'échecs : API anglaise, cases numérotées 0..63,
 * occupation passée en {@code long} (bitboard), aucune notion d'équipe ni de ballon,
 * et la sémantique des échecs — la première pièce rencontrée est <em>attaquée</em>
 * (on peut la prendre). Vous ne pouvez pas la modifier.
 */
public final class ChessAttackEngine {

    public static final int ROOK = 0;
    public static final int BISHOP = 1;
    public static final int KNIGHT = 2;
    public static final int QUEEN = 3;

    private static final int[][] KNIGHT_DELTAS =
            {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};

    public static int square(int file, int rank) { return rank * 8 + file; }

    public static int fileOf(int square) { return square % 8; }

    public static int rankOf(int square) { return square / 8; }

    public long attacks(int pieceKind, int square, long occupancy) {
        return switch (pieceKind) {
            case ROOK -> slide(square, occupancy, new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}});
            case BISHOP -> slide(square, occupancy, new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}});
            case QUEEN -> attacks(ROOK, square, occupancy) | attacks(BISHOP, square, occupancy);
            case KNIGHT -> jumps(square);
            default -> throw new IllegalArgumentException("unknown piece kind: " + pieceKind);
        };
    }

    private long slide(int square, long occupancy, int[][] deltas) {
        long bb = 0L;
        for (int[] d : deltas) {
            int file = fileOf(square) + d[0];
            int rank = rankOf(square) + d[1];
            while (file >= 0 && file < 8 && rank >= 0 && rank < 8) {
                int target = square(file, rank);
                bb |= 1L << target;
                if ((occupancy & (1L << target)) != 0L) break;   // blocker: attacked, then stop
                file += d[0];
                rank += d[1];
            }
        }
        return bb;
    }

    private long jumps(int square) {
        long bb = 0L;
        for (int[] d : KNIGHT_DELTAS) {
            int file = fileOf(square) + d[0];
            int rank = rankOf(square) + d[1];
            if (file >= 0 && file < 8 && rank >= 0 && rank < 8) bb |= 1L << square(file, rank);
        }
        return bb;
    }
}
