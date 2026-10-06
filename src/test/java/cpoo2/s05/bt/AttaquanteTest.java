package cpoo2.s05.bt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.Partie;
import cpoo2.jeu.Plateau;
import cpoo2.jeu.Position;
import cpoo2.s03.ia.Exemples;
import cpoo2.s03.ia.IA;
import cpoo2.s03.ia.IAAleatoire;

/** S05, exercice 3, Q13 et Q14 : l'attaquante en behaviour tree, et son arbre partagé. Tous rouges sur le squelette. */
class AttaquanteTest {

    static Coup c(String s) { return Coup.lire(s); }

    @Test
    void priorite1MarquerQuandOnPeut() {
        Partie p = new Partie(Plateau.depuis(
                ".D.D...",
                "..A.A..",
                ".......",
                "....D..",
                "..a.ao.",
                ".d.d.d."), Couleur.BLEUS);
        assertEquals(c("poussée e3-f2"), new IAAttaquante(new Random(1)).choisirCoup(p));
    }

    @Test
    void priorite2FaireAvancerLeBallon() {
        assertEquals(c("poussée c5-d4"), new IAAttaquante(new Random(1)).choisirCoup(Partie.officielle()),
                "la première poussée qui rapproche le ballon du rang 1");
    }

    @Test
    void priorite3SinonSeRapprocherDuBallon() {
        Partie p = new Partie(Plateau.depuis(
                "D......",
                ".......",
                "...o...",
                ".......",
                ".......",
                "......d"), Couleur.BLEUS);
        Coup coup = new IAAttaquante(new Random(1)).choisirCoup(p);
        assertEquals(Position.of("a6"), coup.origine());
        assertTrue(coup instanceof Coup.Deplacement);
        assertEquals(2, Math.max(Math.abs(coup.arrivee().colonne() - 3), Math.abs(coup.arrivee().rangee() - 3)),
                "de 3 cases du ballon à 2");
    }

    @Test
    void jamaisDeButContreSonCamp() {
        for (int graine = 0; graine < 100; graine++) {
            assertNotEquals(c("poussée d4-d5"), new IAAttaquante(new Random(graine)).choisirCoup(Exemples.leBallonDevantSonBut()));
        }
    }

    @Test
    void unSeulArbrePourToutesLesAttaquantes() {
        new IAAttaquante(new Random(1));
        new IAAttaquante(new Random(2));
        assertSame(IAAttaquante.arbre(), IAAttaquante.arbre(), "l'arbre est partagé : un poids-mouche");
        for (Class<?> k : List.of(Sequence.class, Selecteur.class, Inverseur.class)) {
            assertTrue(Arrays.stream(k.getDeclaredFields()).allMatch(f -> Modifier.isFinal(f.getModifiers())),
                    k.getSimpleName() + " : un nœud partagé ne change jamais");
        }
        assertTrue(Arrays.stream(IAAttaquante.class.getDeclaredFields())
                        .anyMatch(f -> f.getType() == Random.class && !Modifier.isStatic(f.getModifiers())),
                "le hasard appartient à chaque IA, pas à l'arbre partagé");
    }

    @Test
    void lAttaquanteBatLeHasard() {
        int gagnees = 0;
        for (int i = 0; i < 100; i++) {
            IA attaquante = new IAAttaquante(new Random(i));
            IA hasard = new IAAleatoire(new Random(1000 + i));
            Couleur camp = i % 2 == 0 ? Couleur.BLEUS : Couleur.ROUGES;
            Partie p = Partie.officielle();
            while (!p.estTerminee()) {
                assertTrue(p.jouer((p.trait() == camp ? attaquante : hasard).choisirCoup(p)), "un coup légal, toujours");
            }
            if (p.vainqueur().filter(v -> v == camp).isPresent()) gagnees++;
        }
        assertTrue(gagnees >= 90, gagnees + " victoires sur 100");
    }
}
