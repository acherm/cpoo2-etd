package cpoo2.s03.ia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.OptionalInt;

import org.junit.jupiter.api.Test;

import cpoo2.jeu.Coup;
import cpoo2.jeu.PartieDEssai;
import cpoo2.jeu.Plateau;
import cpoo2.jeu.Position;
import gridkit.GridPaths;

/**
 * S03 ex. 1 : l'oracle de l'Adaptateur. Sept tests, tous rouges sur le squelette. Chacun vise une
 * traduction : son nom dit laquelle.
 */
class AdaptateurTest {

    static final Distances GRIDKIT = new DistancesGridkit();
    static final Distances VOL_D_OISEAU = new DistancesAVolDOiseau();

    static Position p(String s) { return Position.of(s); }

    /** Personne sur le terrain, le ballon dans le coin a1. */
    static final Plateau DEGAGE = Plateau.depuis(
            ".......",
            ".......",
            ".......",
            ".......",
            ".......",
            "o......");

    @Test
    void surUnTerrainDegageCommeAVolDOiseau() {
        for (Position de : Position.toutes()) {
            for (Position vers : Position.toutes()) {
                if (DEGAGE.estLibre(de) && DEGAGE.estLibre(vers)) {
                    assertEquals(VOL_D_OISEAU.pas(DEGAGE, de, vers), GRIDKIT.pas(DEGAGE, de, vers), "de " + de + " à " + vers);
                }
            }
        }
    }

    @Test
    void huitDirectionsCommeNosPieces() {
        assertEquals(OptionalInt.of(5), GRIDKIT.pas(DEGAGE, p("b1"), p("g6")),
                "cinq pas en diagonale : en quatre directions, la bibliothèque en compterait dix");
    }

    @Test
    void laPieceQuiMarcheNeSeBarrePasLaRoute() {
        Plateau p = Plateau.depuis(
                ".......",
                ".......",
                ".......",
                ".......",
                ".......",
                "D.....o");
        assertEquals(OptionalInt.of(3), GRIDKIT.pas(p, p("a1"), p("d1")),
                "a1 est occupée par la pièce qui marche : pour la bibliothèque, un départ bloqué ne mène nulle part");
    }

    @Test
    void leBallonEstUnObstacle() {
        Plateau p = Plateau.depuis(
                ".......",
                ".......",
                ".......",
                ".......",   // rang 3 : on veut aller en d3
                "..doa..",   // rang 2 : le ballon en d2, entre deux pièces
                ".......");  // rang 1 : on part de d1
        assertEquals(OptionalInt.of(2), VOL_D_OISEAU.pas(p, p("d1"), p("d3")), "à vol d'oiseau, on passe sur le ballon");
        assertEquals(OptionalInt.of(4), GRIDKIT.pas(p, p("d1"), p("d3")),
                "on n'entre sur la case du ballon qu'en le poussant : il faut le contourner");
    }

    @Test
    void inaccessibleDonneUnResultatVide() {
        Plateau officiel = Plateau.officiel();
        assertEquals(OptionalInt.empty(), GRIDKIT.pas(officiel, p("a1"), p("b1")), "b1 est occupée");
        assertEquals(OptionalInt.empty(), GRIDKIT.pas(officiel, p("a1"), p("d4")), "d4 est la case du ballon");
        Plateau enferme = Plateau.depuis(
                ".d.....",   // a6, enfermée par b6, a5, b5
                "dd.....",
                ".......",
                ".......",
                ".......",
                "...o...");
        assertEquals(OptionalInt.of(6), VOL_D_OISEAU.pas(enferme, p("g1"), p("a6")));
        assertEquals(OptionalInt.empty(), GRIDKIT.pas(enferme, p("g1"), p("a6")), "a6 est coupée du reste : vide, pas -1");
    }

    @Test
    void laGloutonneContourneLeMur() {
        PartieDEssai mur = Exemples.leMur();
        assertEquals(Coup.lire("déplacement f6-f5"), new IAGloutonne(VOL_D_OISEAU).choisirCoup(mur),
                "à vol d'oiseau, le défenseur de f6 est à deux pas de d6 : elle l'envoie buter contre le mur");
        assertEquals(Coup.lire("déplacement a2-a3"), new IAGloutonne(GRIDKIT).choisirCoup(mur),
                "avec les vraies distances, c'est l'attaquant de a2 qui arrive le premier : l'IA n'a pas changé d'une ligne");
    }

    @Test
    void structureDUnAdaptateurDObjet() {
        assertTrue(Distances.class.isAssignableFrom(DistancesGridkit.class), "l'adaptateur EST une Distances : la cible");
        assertEquals(Object.class, DistancesGridkit.class.getSuperclass(), "par composition, pas par héritage");
        assertTrue(Arrays.stream(DistancesGridkit.class.getDeclaredFields())
                        .anyMatch(f -> f.getType() == GridPaths.class && !Modifier.isStatic(f.getModifiers())),
                "l'adaptateur DÉTIENT l'adapté, dans un champ");
    }
}
