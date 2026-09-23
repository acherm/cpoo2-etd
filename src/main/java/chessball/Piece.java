package chessball;

/**
 * Une pièce du jeu. Son <em>identité</em> compte (deux tours bleues sont deux pièces distinctes) :
 * pas de {@code record}, pas de {@code equals} redéfini. Sa position, elle, vit dans le {@link Plateau}.
 */
public final class Piece {
    private final Couleur couleur;
    private final TypePiece type;
    private final String nom;

    public Piece(Couleur couleur, TypePiece type, String nom) {
        this.couleur = couleur;
        this.type = type;
        this.nom = nom;
    }

    public Couleur couleur() { return couleur; }
    public TypePiece type()  { return type; }
    public String nom()      { return nom; }

    @Override public String toString() { return nom; }
}
