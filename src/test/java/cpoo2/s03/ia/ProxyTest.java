package cpoo2.s03.ia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;
import cpoo2.jeu.PartieDEssai;

/** S03 ex. 3 : l'oracle du Proxy. Tous rouges sur le squelette. */
class ProxyTest {

    @Test
    void laLectureEstDelegueeTelleQuelle() {
        PartieDEssai reelle = PartieDEssai.depart();
        Jeu vue = new JeuEnLectureSeule(reelle);
        assertEquals(reelle.trait(), vue.trait());
        assertSame(reelle.plateau(), vue.plateau());
        assertEquals(reelle.coupsLegaux(), vue.coupsLegaux());
    }

    @Test
    void jouerEstRefuseEtLaPartieNEnSaitRien() {
        PartieDEssai reelle = PartieDEssai.depart();
        Jeu vue = new JeuEnLectureSeule(reelle);
        assertThrows(UnsupportedOperationException.class, () -> vue.jouer(Coup.lire("poussée c5-d4")),
                "refuser bruyamment : un faux silencieux laisserait l'IA continuer comme si de rien n'était");
        assertEquals(List.of(), reelle.coupsJoues(), "la partie réelle n'a rien vu passer");
    }

    @Test
    void lIANeTientJamaisLaPartieElleMeme() {
        PartieDEssai reelle = PartieDEssai.depart();
        List<Jeu> recus = new ArrayList<>();
        IA espionne = jeu -> {
            recus.add(jeu);
            return jeu.coupsLegaux().get(0);
        };
        Coup joue = new Rencontre(reelle, espionne, espionne).tourSuivant();
        assertNotSame(reelle, recus.get(0), "la rencontre remet une vue à l'IA, jamais la partie");
        assertEquals(List.of(joue), reelle.coupsJoues(), "et le coup proposé est bien joué, une fois");
    }

    @Test
    void laTricheuseEstPriseLaMainDansLeSac() {
        PartieDEssai reelle = PartieDEssai.depart();
        Rencontre rencontre = new Rencontre(reelle, new IATricheuse(), new IAAleatoire(new Random(1)));
        UnsupportedOperationException e = assertThrows(UnsupportedOperationException.class, rencontre::tourSuivant,
                "la tricheuse joue en douce : la vue doit l'arrêter");
        assertFalse(String.valueOf(e.getMessage()).startsWith("TODO"), "c'est encore le TODO du squelette qui a levé l'exception");
        assertEquals(List.of(), reelle.coupsJoues(), "pas un seul de ses coups n'a atteint la partie");
    }

    @Test
    void structureDUnProxy() {
        assertTrue(Jeu.class.isAssignableFrom(JeuEnLectureSeule.class), "le proxy EST un Jeu : l'IA ne voit pas la différence");
        assertTrue(Arrays.stream(JeuEnLectureSeule.class.getDeclaredFields())
                        .anyMatch(f -> f.getType() == Jeu.class && !Modifier.isStatic(f.getModifiers())),
                "le proxy DÉTIENT le sujet réel, typé par l'interface");
        assertFalse(Arrays.stream(JeuEnLectureSeule.class.getDeclaredFields()).anyMatch(f -> f.getType() == PartieDEssai.class),
                "jamais la classe concrète : il protège n'importe quel Jeu");
    }
}
