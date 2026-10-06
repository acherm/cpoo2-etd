package cpoo2.jeu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * La partie fournie, avec son arbitre : ces tests passent déjà. Ils disent ce qu'une partie doit
 * faire, et ils resteront le filet quand la séance 5 la réécrira en machine à états.
 */
class PartieTest {

    static Coup c(String s) { return Coup.lire(s); }

    /** Le nombre de suites de {@code profondeur} coups depuis le départ (les buts ne se prolongent pas). */
    static long perft(List<Coup> joues, int profondeur) {
        Partie p = Partie.officielle();
        joues.forEach(p::jouer);
        if (profondeur == 0 || p.estTerminee()) return 1;
        long n = 0;
        for (Coup coup : p.coupsLegaux()) {
            List<Coup> suite = new java.util.ArrayList<>(joues);
            suite.add(coup);
            n += perft(suite, profondeur - 1);
        }
        return n;
    }

    @Test
    void auDepartVingtCinqCoupsCeuxDeLaBoite() {
        assertEquals(new HashSet<>(PartieDEssai.depart().coupsLegaux()), new HashSet<>(Partie.officielle().coupsLegaux()));
        assertEquals(577, perft(List.of(), 2));
        assertEquals(15_224, perft(List.of(), 3));
    }

    @Test
    void justeApresUnTacleLaVictimeNeRipostePas() {
        Plateau avant = Plateau.depuis(
                ".......",
                ".D.D.D.",
                "..a.aA.",
                "....Ao.",
                ".d.d.d.",
                ".......");
        Partie p = new Partie(avant, Couleur.BLEUS);
        assertTrue(p.jouer(c("tacle d5-c4")), "le défenseur de d5 tacle l'attaquant rouge de c4, repoussé en b3");
        assertEquals(36, p.coupsLegaux().size());
        assertFalse(p.coupsLegaux().contains(c("saut b3-d5")), "la victime ne saute pas par-dessus son tacleur");
        Partie sansMemoire = new Partie(p.plateau(), Couleur.ROUGES);
        assertEquals(37, sansMemoire.coupsLegaux().size());
        assertTrue(sansMemoire.coupsLegaux().contains(c("saut b3-d5")));
    }

    @Test
    void jamaisLeBallonEnZoneDeTouche() {
        Partie p = new Partie(Plateau.depuis(
                ".D.D...",
                "..A.A..",
                ".......",
                ".oD....",
                "..a.a..",
                ".d.d.d."), Couleur.BLEUS);
        assertEquals(27, p.coupsLegaux().size());
        assertFalse(p.jouer(c("poussée c3-b3")), "le ballon irait en a3, une zone de touche");
    }

    @Test
    void unButDansLeCoinTermineLaPartie() {
        Partie p = new Partie(Plateau.depuis(
                ".D.D...",
                "..A.A..",
                ".......",
                "....D..",
                "..a.ao.",
                ".d.d.d."), Couleur.BLEUS);
        assertEquals(28, p.coupsLegaux().size());
        assertTrue(p.jouer(c("poussée e3-f2")));
        assertTrue(p.estTerminee());
        assertEquals(Optional.of(Couleur.BLEUS), p.vainqueur(), "le ballon est en g1, sur la ligne où marquent les Bleus");
        assertEquals(List.of(), p.coupsLegaux());
        assertFalse(p.jouer(c("déplacement b1-b2")), "une partie finie ne se joue plus");
    }

    @Test
    void quatreVingtsDemiCoupsSansPousseeLaPartieEstNulle() {
        Partie p = Partie.officielle();
        String[] allerRetour = {"déplacement b6-b5", "déplacement b1-b2", "déplacement b5-b6", "déplacement b2-b1"};
        for (int i = 0; i < Partie.DEMI_COUPS_SANS_POUSSEE_MAX; i++) {
            assertFalse(p.estTerminee(), "demi-coup " + i);
            assertTrue(p.jouer(c(allerRetour[i % 4])));
        }
        assertTrue(p.estTerminee());
        assertEquals(Optional.empty(), p.vainqueur(), "nulle : personne n'a gagné");
    }

    @Test
    void unCoupRefuseNeChangeRien() {
        Partie p = Partie.officielle();
        Plateau avant = p.plateau();
        assertFalse(p.jouer(c("déplacement c2-c3")), "ce sont les Bleus qui jouent");
        assertFalse(p.jouer(c("déplacement c5-d4")), "d4 est la case du ballon");
        assertEquals(avant, p.plateau());
        assertEquals(Couleur.BLEUS, p.trait());
    }
}
