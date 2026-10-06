package cpoo2.s03.ia;

import static cpoo2.jeu.Couleur.BLEUS;
import static cpoo2.jeu.Couleur.ROUGES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import cpoo2.jeu.Coup;
import cpoo2.jeu.PartieDEssai;

/**
 * S03 ex. 2 : l'oracle des deux Décorateurs. Tous rouges sur le squelette. La partie d'essai est
 * {@link Exemples#leBallonDevantSonBut()} : un seul coup y marque contre son camp,
 * {@code poussée d4-d5}.
 */
class DecorateurTest {

    static final Coup CONTRE_SON_CAMP = Coup.lire("poussée d4-d5");
    static final Coup SANS_DANGER = Coup.lire("déplacement c2-c3");
    static final PartieDEssai PARTIE = Exemples.leBallonDevantSonBut();

    /** Une IA de test, qui propose toujours le même coup. */
    static IA toujours(Coup coup) {
        return jeu -> coup;
    }

    // ---------- SansButContreSonCamp ----------

    @Test
    void reconnaitUnButContreSonCamp() {
        assertTrue(SansButContreSonCamp.contreSonCamp(CONTRE_SON_CAMP, BLEUS), "le ballon s'arrête en d6, sur la ligne des Bleus");
        assertFalse(SansButContreSonCamp.contreSonCamp(CONTRE_SON_CAMP, ROUGES), "pour les Rouges, le rang 6 est la ligne où ils marquent");
        assertFalse(SansButContreSonCamp.contreSonCamp(SANS_DANGER, BLEUS));
        assertFalse(SansButContreSonCamp.contreSonCamp(Coup.lire("saut d4-d6"), BLEUS), "un saut ne pousse pas le ballon");
    }

    @Test
    void laisseTelQuelUnCoupSansDanger() {
        assertEquals(SANS_DANGER, new SansButContreSonCamp(toujours(SANS_DANGER)).choisirCoup(PARTIE));
    }

    @Test
    void remplaceUnButContreSonCamp() {
        assertEquals(Coup.lire("déplacement b6-b5"), new SansButContreSonCamp(toujours(CONTRE_SON_CAMP)).choisirCoup(PARTIE),
                "le premier coup légal, dans l'ordre de coupsLegaux(), qui n'est pas un but contre son camp");
    }

    @Test
    void gardeLeCoupSIlNYARienDAutre() {
        PartieDEssai pasLeChoix = PartieDEssai.de(PARTIE.plateau(), BLEUS, "poussée d4-d5");   // une partie d'essai qui n'offre qu'un coup
        assertEquals(CONTRE_SON_CAMP, new SansButContreSonCamp(toujours(CONTRE_SON_CAMP)).choisirCoup(pasLeChoix));
    }

    @Test
    void protegeNImporteQuelleIA() {
        int butsContreSonCamp = 0;
        for (int graine = 0; graine < 300; graine++) {
            if (new IAAleatoire(new Random(graine)).choisirCoup(PARTIE).equals(CONTRE_SON_CAMP)) {
                butsContreSonCamp++;
            }
            assertNotEquals(CONTRE_SON_CAMP, new SansButContreSonCamp(new IAAleatoire(new Random(graine))).choisirCoup(PARTIE),
                    "graine " + graine);
        }
        assertTrue(butsContreSonCamp > 0, "nue, l'IA aléatoire finit par marquer contre son camp");
    }

    // ---------- IAJournalisee ----------

    @Test
    void journaliseEtRendLeCoupTelQuel() {
        List<String> journal = new ArrayList<>();
        assertEquals(SANS_DANGER, new IAJournalisee(toujours(SANS_DANGER), journal::add).choisirCoup(PARTIE));
        assertEquals(List.of("BLEUS : déplacement c2-c3"), journal);
    }

    @Test
    void deuxJournauxSEmpilent() {
        List<String> dedans = new ArrayList<>();
        List<String> dehors = new ArrayList<>();
        IA ia = new IAJournalisee(new IAJournalisee(toujours(SANS_DANGER), dedans::add), dehors::add);
        assertEquals(SANS_DANGER, ia.choisirCoup(PARTIE));
        assertEquals(List.of("BLEUS : déplacement c2-c3"), dedans);
        assertEquals(List.of("BLEUS : déplacement c2-c3"), dehors);
    }

    // ---------- les deux ensemble ----------

    @Test
    void lOrdreDEmpilementChangeCeQueDitLeJournal() {
        List<String> journalDehors = new ArrayList<>();
        List<String> journalDedans = new ArrayList<>();
        IA a = new IAJournalisee(new SansButContreSonCamp(toujours(CONTRE_SON_CAMP)), journalDehors::add);
        IA b = new SansButContreSonCamp(new IAJournalisee(toujours(CONTRE_SON_CAMP), journalDedans::add));
        assertEquals(a.choisirCoup(PARTIE), b.choisirCoup(PARTIE), "le coup joué est le même");
        assertEquals(List.of("BLEUS : déplacement b6-b5"), journalDehors, "dehors, le journal voit le coup corrigé");
        assertEquals(List.of("BLEUS : poussée d4-d5"), journalDedans, "dedans, il voit ce que l'IA avait proposé");
    }

    @Test
    void structureDesDecorateurs() {
        for (Class<?> c : List.of(SansButContreSonCamp.class, IAJournalisee.class)) {
            assertTrue(IA.class.isAssignableFrom(c), c.getSimpleName() + " EST une IA : même interface que ce qu'il enveloppe");
            assertTrue(Arrays.stream(c.getDeclaredFields())
                            .anyMatch(f -> f.getType() == IA.class && !Modifier.isStatic(f.getModifiers())),
                    c.getSimpleName() + " DÉTIENT une IA, typée par l'interface : c'est ce qui permet d'empiler");
        }
    }
}
