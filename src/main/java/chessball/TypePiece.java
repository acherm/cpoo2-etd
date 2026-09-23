package chessball;

/** Les quatre types de pièces : une dame, deux tours, deux fous, un cavalier par équipe. */
public enum TypePiece {
    DAME('D'), TOUR('T'), FOU('F'), CAVALIER('C');

    private final char lettre;

    TypePiece(char lettre) { this.lettre = lettre; }

    public char lettre() { return lettre; }
}
