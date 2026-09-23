package chessball;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TD6 ex. 2, Q9 ter : l'oracle de l'injection. Rouge sur le squelette.
 *
 * <p>Le hasard du tirage au sort est <b>reçu</b> par l'arbitre, jamais fabriqué dedans : un test le
 * truque, une graine le rejoue.</p>
 */
class InjectionTest {

    static final Function<Couleur, Partie> MI_TEMPS = e -> new Partie(Placements.standard(), Regles.duJeuDeBase(), e);

    static Couleur engageur(TirageAuSort tirage) {
        return new ArbitreEnUneMiTemps(MI_TEMPS, 1, tirage)
                .jouerLeMatch(new AdversaireAleatoire(new Random(1)), new AdversaireAleatoire(new Random(2)))
                .premierEngageur();
    }

    @Test
    void unTirageTruqueRendLeTestDeterministe() {
        assertEquals(Couleur.ROUGES, engageur(TirageAuSort.truque(Couleur.ROUGES)));
        assertEquals(Couleur.BLEUS, engageur(TirageAuSort.truque(Couleur.BLEUS)));
    }

    @Test
    void laMemeGraineEngageLaMemeEquipe() {
        assertEquals(engageur(TirageAuSort.aleatoire(new Random(3))), engageur(TirageAuSort.aleatoire(new Random(3))));
    }

    @Test
    void leTirageAleatoireDonneLesDeuxEquipes() {
        TirageAuSort tirage = TirageAuSort.aleatoire(new Random(3));
        EnumSet<Couleur> vues = EnumSet.noneOf(Couleur.class);
        for (int match = 0; match < 40; match++) vues.add(tirage.equipeQuiEngage());
        assertEquals(EnumSet.allOf(Couleur.class), vues, "quarante matchs : les deux équipes ont engagé");
    }

    @Test
    void lArbitreNeFabriqueAucunHasard() {
        boolean fabriqueDuHasard = Arrays.stream(Arbitre.class.getDeclaredFields())
                .anyMatch(f -> RandomGenerator.class.isAssignableFrom(f.getType()));
        assertTrue(!fabriqueDuHasard, "aucun Random dans l'arbitre : le hasard vient de l'extérieur");
        boolean recoitLeTirage = Arrays.stream(Arbitre.class.getDeclaredFields())
                .anyMatch(f -> f.getType() == TirageAuSort.class);
        assertTrue(recoitLeTirage, "l'arbitre détient le tirage qu'on lui a donné");
    }
}
