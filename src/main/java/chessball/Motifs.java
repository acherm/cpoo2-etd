package chessball;

import java.util.ArrayList;
import java.util.List;

/**
 * Le motif de déplacement d'un type de pièce : la tour glisse en ligne, le fou en diagonale,
 * le cavalier saute, la dame fait les deux.
 *
 * <p><b>Version naïve, volontairement.</b> Chaque appel construit un motif <em>neuf</em> :
 * douze pièces sur le plateau, douze objets identiques deux à deux. Les motifs n'ont pourtant
 * aucun état propre à une pièce (la case de départ leur est passée en paramètre). La séance 5
 * en fera des objets partagés : c'est le patron Poids-mouche (S05, Q1 bis : une {@code EnumMap}
 * remplie une fois, {@code pour} rend toujours la même instance, {@code PoidsMoucheTest}).</p>
 */
public final class Motifs {

    private Motifs() {}

    /** Un motif pour ce type. Aujourd'hui : une nouvelle instance à chaque appel. */
    public static Motif pour(TypePiece type) {
        return switch (type) {
            case TOUR -> new MotifGlissant(Direction.lignes());
            case FOU -> new MotifGlissant(Direction.diagonales());
            case CAVALIER -> MotifSauteur.cavalier();
            case DAME -> new MotifCompose(new MotifGlissant(Direction.lignes()),
                    new MotifGlissant(Direction.diagonales()));
        };
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
    static List<Direction> toutesLesDirections() {
        List<Direction> toutes = new ArrayList<>(Direction.lignes());
        toutes.addAll(Direction.diagonales());
        return List.copyOf(toutes);
    }
}
