package chessball;

import java.util.List;
import java.util.SortedSet;
import chessball.externe.ChessAttackEngine;

/**
 * S03, exercice 1 : l'<b>Adaptateur</b>. La bibliothèque {@link ChessAttackEngine} fait le
 * travail (elle sait quelles cases une tour, un fou, une dame ou un cavalier attaquent) mais
 * ne parle pas notre interface {@link Motif} : des cases numérotées 0..63 au lieu de
 * {@link Position}, un {@code long} d'occupation (un bit par case) au lieu d'un
 * {@link Plateau}, et la <b>sémantique des échecs</b> : la première pièce rencontrée est
 * attaquée, donc prenable, là où ChessBall interdit toute destination occupée (J6).
 *
 * <p>TODO Q1 : traduire les trois, sans toucher à la bibliothèque (elle est {@code final},
 * et elle n'est pas à vous). L'oracle est le motif maison : {@code Motifs.pour(type)} doit
 * répondre exactement la même chose.</p>
 */
public final class MotifExterne implements Motif {

    private final ChessAttackEngine moteur = new ChessAttackEngine();
    private final int genre;
    private final List<Direction> directions;

    private MotifExterne(int genre, List<Direction> directions) {
        this.genre = genre;
        this.directions = directions;
    }

    /** Le genre de pièce de la bibliothèque, et les directions de tir que le jeu attend. */
    public static Motif pour(TypePiece type) {
        return switch (type) {
            case TOUR -> new MotifExterne(ChessAttackEngine.ROOK, Direction.lignes());
            case FOU -> new MotifExterne(ChessAttackEngine.BISHOP, Direction.diagonales());
            case DAME -> new MotifExterne(ChessAttackEngine.QUEEN, Motifs.toutesLesDirections());
            case CAVALIER -> new MotifExterne(ChessAttackEngine.KNIGHT, List.of());
        };
    }

    @Override public SortedSet<Position> accessibles(Plateau plateau, Position depart) {
        throw new UnsupportedOperationException("TODO Q1");
    }

    @Override public boolean vise(Plateau plateau, Position depart, Position cible) {
        throw new UnsupportedOperationException("TODO Q1");
    }

    @Override public List<Direction> directions() { return directions; }
}
