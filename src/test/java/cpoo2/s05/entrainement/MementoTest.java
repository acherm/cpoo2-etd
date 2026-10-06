package cpoo2.s05.entrainement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.Partie;
import cpoo2.jeu.Plateau;

/** S05, exercice 2 : le <b>Memento</b>. Le dernier test passe à vide sur le squelette. */
class MementoTest {

    static Coup c(String s) { return Coup.lire(s); }

    @Test
    void annulerRevientAuCoupPrecedent() {
        Partie p = Partie.officielle();
        Historique h = new Historique(p);
        assertTrue(h.jouer(c("poussée c5-d4")));
        assertTrue(h.jouer(c("déplacement c2-c3")));
        assertTrue(h.jouer(c("poussée d4-e3")));
        assertEquals(3, h.profondeur());
        assertTrue(h.annuler());
        assertTrue(h.annuler());
        assertTrue(h.annuler());
        assertEquals(Plateau.officiel(), p.plateau());
        assertEquals(Couleur.BLEUS, p.trait());
        assertEquals(25, p.coupsLegaux().size());
        assertFalse(h.annuler(), "plus rien à annuler");
    }

    @Test
    void annulerRendAussiLeSouvenirDuTacle() {
        Partie p = new Partie(Plateau.depuis(
                ".......",
                ".D.D.D.",
                "..a.aA.",
                "....Ao.",
                ".d.d.d.",
                "......."), Couleur.BLEUS);
        Historique h = new Historique(p);
        h.jouer(c("tacle d5-c4"));
        h.jouer(c("déplacement b2-a1"));
        h.annuler();
        assertEquals(36, p.coupsLegaux().size(), "revenu juste après le tacle : la riposte est de nouveau interdite");
        assertFalse(p.coupsLegaux().contains(c("saut b3-d5")));
    }

    @Test
    void annulerUnButRouvreLaPartie() {
        Partie p = new Partie(Plateau.depuis(
                ".D.D...",
                "..A.A..",
                ".......",
                "....D..",
                "..a.ao.",
                ".d.d.d."), Couleur.BLEUS);
        Historique h = new Historique(p);
        h.jouer(c("poussée e3-f2"));
        assertTrue(p.estTerminee());
        h.annuler();
        assertFalse(p.estTerminee());
        assertEquals(28, p.coupsLegaux().size());
    }

    @Test
    void unCoupRefuseNeSauvegardeRien() {
        Historique h = new Historique(Partie.officielle());
        assertFalse(h.jouer(c("déplacement c2-c3")), "ce sont les Bleus qui jouent");
        assertEquals(0, h.profondeur());
    }

    @Test
    void laSauvegardeEstOpaque() {
        Class<?> s = Partie.Sauvegarde.class;
        assertTrue(Arrays.stream(s.getDeclaredConstructors()).allMatch(k -> Modifier.isPrivate(k.getModifiers())),
                "seule la partie fabrique une sauvegarde");
        assertTrue(Arrays.stream(s.getDeclaredMethods()).noneMatch(m -> Modifier.isPublic(m.getModifiers())),
                "aucun accesseur : le gardien la détient sans pouvoir l'ouvrir");
        assertTrue(Arrays.stream(s.getDeclaredFields()).allMatch(f -> Modifier.isPrivate(f.getModifiers())));
    }
}
