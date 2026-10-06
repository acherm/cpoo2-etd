package chessball;

import java.util.EnumMap;
import java.util.Map;

/**
 * Douze pièces sur le plateau, quatre comportements de déplacement. Un motif ne contient
 * <em>aucune</em> donnée propre à une pièce (la case de départ lui est passée en paramètre) :
 * il est partageable. Une instance par type, toujours la même : c'est le <b>Poids-mouche</b>.
 */
public final class Motifs {

    private static final Map<TypePiece, Motif> PARTAGES = new EnumMap<>(TypePiece.class);

    static {
        Motif tour = new MotifGlissant(Direction.lignes());
        Motif fou = new MotifGlissant(Direction.diagonales());
        PARTAGES.put(TypePiece.TOUR, tour);
        PARTAGES.put(TypePiece.FOU, fou);
        PARTAGES.put(TypePiece.CAVALIER, MotifSauteur.cavalier());
        PARTAGES.put(TypePiece.DAME, new MotifCompose(tour, fou));
    }

    private Motifs() {}

    /** Toujours la même instance pour un type donné : l'état extrinsèque (la case) est un paramètre. */
    public static Motif pour(TypePiece type) {
        return PARTAGES.getOrDefault(type, MotifNul.INSTANCE);
    }

    /** Type lu dans un fichier de configuration : inconnu, la pièce existe mais ne bouge pas. */
    public static Motif pourNom(String nom) {
        for (TypePiece type : TypePiece.values()) {
            if (type.name().equalsIgnoreCase(nom)) return pour(type);
        }
        return MotifNul.INSTANCE;
    }

    public static Motif de(Piece piece) {
        return pour(piece.type());
    }

    /** Toutes les directions de glissement, lignes puis diagonales : pratique pour la dame. */
    static java.util.List<Direction> toutesLesDirections() {
        java.util.List<Direction> toutes = new java.util.ArrayList<>(Direction.lignes());
        toutes.addAll(Direction.diagonales());
        return java.util.List.copyOf(toutes);
    }
}
