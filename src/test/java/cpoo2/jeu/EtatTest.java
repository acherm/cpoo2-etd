package cpoo2.jeu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

/**
 * S05, exercice 1 : le patron <b>État</b>. Tous rouges sur le squelette. {@code PartieTest}, lui,
 * doit rester vert pendant toute la refonte : c'est le filet.
 */
class EtatTest {

    static Coup c(String s) { return Coup.lire(s); }

    static final Plateau AVANT_LE_TACLE = Plateau.depuis(
            ".......",
            ".D.D.D.",
            "..a.aA.",
            "....Ao.",
            ".d.d.d.",
            ".......");

    static final Plateau BUT_DANS_LE_COIN = Plateau.depuis(
            ".D.D...",
            "..A.A..",
            ".......",
            "....D..",
            "..a.ao.",
            ".d.d.d.");

    @Test
    void lesQuatreEtatsDisentCeQuIlsPermettent() {
        Partie p = new Partie(AVANT_LE_TACLE, Couleur.BLEUS);
        p.jouer(c("tacle d5-c4"));
        Partie memePosition = new Partie(p.plateau(), Couleur.ROUGES);
        assertEquals(37, new EnJeu().coupsLegaux(memePosition).size());
        List<Coup> apresTacle = new ApresTacle(Position.of("c4"), Position.of("b3")).coupsLegaux(memePosition);
        assertEquals(36, apresTacle.size());
        assertFalse(apresTacle.contains(c("saut b3-d5")), "la victime ne saute pas par-dessus son tacleur");
        assertEquals(List.of(), new Gagnee(Couleur.BLEUS).coupsLegaux(memePosition));
        assertEquals(List.of(), new Nulle().coupsLegaux(memePosition));
        assertTrue(new Gagnee(Couleur.BLEUS).estTerminee());
        assertEquals(Optional.of(Couleur.BLEUS), new Gagnee(Couleur.BLEUS).vainqueur());
        assertTrue(new Nulle().estTerminee());
        assertEquals(Optional.empty(), new Nulle().vainqueur());
        assertFalse(new EnJeu().estTerminee());
    }

    @Test
    void unTacleMeneAApresTacleEtLeCoupSuivantEnSort() {
        Partie p = new Partie(AVANT_LE_TACLE, Couleur.BLEUS);
        assertEquals(new EnJeu(), p.etat());
        p.jouer(c("tacle d5-c4"));
        assertEquals(new ApresTacle(Position.of("c4"), Position.of("b3")), p.etat());
        p.jouer(c("déplacement b2-a1"));
        assertEquals(new EnJeu(), p.etat(), "le souvenir du tacle s'efface au coup suivant");
    }

    @Test
    void unButMeneAGagnee() {
        Partie p = new Partie(BUT_DANS_LE_COIN, Couleur.BLEUS);
        p.jouer(c("poussée e3-f2"));
        assertEquals(new Gagnee(Couleur.BLEUS), p.etat());
    }

    @Test
    void quatreVingtsDemiCoupsSansPousseeMenentANulle() {
        Partie p = Partie.officielle();
        String[] allerRetour = {"déplacement b6-b5", "déplacement b1-b2", "déplacement b5-b6", "déplacement b2-b1"};
        for (int i = 0; i < Partie.DEMI_COUPS_SANS_POUSSEE_MAX; i++) {
            p.jouer(c(allerRetour[i % 4]));
        }
        assertEquals(new Nulle(), p.etat());
    }

    @Test
    void laPartieNeGardeQuUnChampDEtat() {
        Set<Class<?>> types = Arrays.stream(Partie.class.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .map(Field::getType)
                .collect(Collectors.toSet());
        assertTrue(types.contains(EtatPartie.class), "la partie détient son état, typé par l'interface");
        assertFalse(types.contains(boolean.class), "plus de drapeau « terminée » : c'est l'état qui le sait");
        assertFalse(types.contains(Position.class), "plus de tacleur ni de victime : c'est ApresTacle qui s'en souvient");
    }
}
