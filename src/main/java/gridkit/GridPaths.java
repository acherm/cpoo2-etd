package gridkit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * gridkit 2.3: shortest paths on rectangular grids.
 *
 * <p>A grid is a {@code boolean[rows][cols]} where {@code true} means <em>blocked</em>. Cells are
 * addressed as {@code (row, col)}; <b>row 0 is the top row</b>, as on a screen or in a text
 * file. A path goes from a cell to one of its neighbours, one step at a time, and never enters a
 * blocked cell.</p>
 *
 * <pre>{@code
 * GridPaths paths = new GridPaths(GridPaths.Neighbourhood.EIGHT);
 * int steps = paths.distance(blocked, 5, 0, 0, 6);   // -1 if there is no path
 * }</pre>
 *
 * <p><i>Third-party library (MIT licence). Do not edit: this is not your code. Report bugs
 * upstream.</i></p>
 */
public final class GridPaths {

    /** Which cells count as neighbours. */
    public enum Neighbourhood {
        /** Up, down, left, right. */
        FOUR,
        /** The four above, plus the four diagonals. */
        EIGHT
    }

    private static final int[][] FOUR_STEPS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int[][] EIGHT_STEPS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    private final Neighbourhood neighbourhood;

    public GridPaths(Neighbourhood neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    /**
     * Length of a shortest path.
     *
     * @return the number of steps from {@code (fromRow, fromCol)} to {@code (toRow, toCol)},
     *         {@code 0} if both are the same cell, or <b>{@code -1}</b> if there is no path, if
     *         either cell is outside the grid, or if either cell is blocked
     */
    public int distance(boolean[][] blocked, int fromRow, int fromCol, int toRow, int toCol) {
        List<int[]> path = path(blocked, fromRow, fromCol, toRow, toCol);
        return path.isEmpty() ? -1 : path.size() - 1;
    }

    /**
     * A shortest path, both ends included, as {@code {row, col}} pairs; an empty list if there is
     * none (same conditions as {@link #distance}).
     */
    public List<int[]> path(boolean[][] blocked, int fromRow, int fromCol, int toRow, int toCol) {
        int rows = blocked.length;
        int cols = rows == 0 ? 0 : blocked[0].length;
        if (!open(blocked, rows, cols, fromRow, fromCol) || !open(blocked, rows, cols, toRow, toCol)) {
            return List.of();
        }
        int[][] previous = new int[rows * cols][];
        boolean[] seen = new boolean[rows * cols];
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{fromRow, fromCol});
        seen[fromRow * cols + fromCol] = true;
        int[][] steps = neighbourhood == Neighbourhood.FOUR ? FOUR_STEPS : EIGHT_STEPS;
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            if (cell[0] == toRow && cell[1] == toCol) {
                List<int[]> path = new ArrayList<>();
                for (int[] c = cell; c != null; c = previous[c[0] * cols + c[1]]) {
                    path.add(c);
                }
                Collections.reverse(path);
                return path;
            }
            for (int[] s : steps) {
                int r = cell[0] + s[0];
                int c = cell[1] + s[1];
                if (open(blocked, rows, cols, r, c) && !seen[r * cols + c]) {
                    seen[r * cols + c] = true;
                    previous[r * cols + c] = cell;
                    queue.add(new int[]{r, c});
                }
            }
        }
        return List.of();
    }

    private static boolean open(boolean[][] blocked, int rows, int cols, int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && !blocked[row][col];
    }
}
