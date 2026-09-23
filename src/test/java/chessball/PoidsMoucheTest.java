package chessball;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S05, Q1 bis : l'oracle du Poids-mouche. Douze pièces, quatre motifs : {@code Motifs.pour}
 * rend <b>la même instance</b> pour un type donné (identité, pas égalité).
 */
class PoidsMoucheTest {

    @Test
    void deuxAppelsPourLeMemeTypeRendentLeMemeObjet() {
        assertSame(Motifs.pour(TypePiece.TOUR), Motifs.pour(TypePiece.TOUR), "== attendu, pas equals");
        assertSame(Motifs.pour(TypePiece.CAVALIER), Motifs.pour(TypePiece.CAVALIER));
    }

    @Test
    void lesDeuxToursDuPlacementStandardPartagentLeurMotif() {
        Plateau plateau = Placements.standard();
        Piece t1 = plateau.pieceEn(Position.of("a1")).orElseThrow();
        Piece t2 = plateau.pieceEn(Position.of("h1")).orElseThrow();
        assertNotSame(t1, t2, "deux pièces distinctes");
        assertSame(Motifs.de(t1), Motifs.de(t2), "un seul motif de tour pour tout le plateau");
    }

    @Test
    void deuxTypesDifferentsNePartagentPas() {
        assertNotSame(Motifs.pour(TypePiece.TOUR), Motifs.pour(TypePiece.FOU));
    }

    @Test
    void unMotifPartageNAAucunEtatMutable() {
        for (Class<?> c : new Class<?>[] {MotifGlissant.class, MotifSauteur.class, MotifCompose.class}) {
            assertTrue(Arrays.stream(c.getDeclaredFields())
                            .filter(f -> !Modifier.isStatic(f.getModifiers()))
                            .allMatch(f -> Modifier.isFinal(f.getModifiers())),
                    c.getSimpleName() + " : tous les champs doivent être final, le partage n'est sûr que sans état mutable");
        }
        assertEquals(27, Motifs.pour(TypePiece.DAME).accessibles(new Plateau(8), Position.of("d4")).size(),
                "la dame partagée vaut toujours tour ou fou : 27 cases depuis d4 sur un plateau vide");
    }
}
