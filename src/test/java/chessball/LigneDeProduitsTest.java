package chessball;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TD6 ex. 4, Q17 (au choix) : l'échantillon pairwise du feature model de l'application, restreint
 * aux axes implémentés (Taille, MiTemps, IA), et chaque configuration <b>se lance</b>.
 * Rouge tant que les variantes et les arbitres n'existent pas.
 */
class LigneDeProduitsTest {

    static Variante variante(String taille) {
        return switch (taille) {
            case "8x8" -> new VarianteStandard();
            case "10x10" -> new VarianteGrandTerrain();
            default -> throw new IllegalArgumentException(taille);
        };
    }

    static StrategieAdversaire ia(String nom, long graine) {
        return switch (nom) {
            case "Aleatoire" -> new AdversaireAleatoire(new Random(graine));
            case "Heuristique" -> new AdversaireHeuristique(new Random(graine));
            default -> throw new IllegalArgumentException(nom);
        };
    }

    @ParameterizedTest(name = "⟨{0}, {1} mi-temps, {2}⟩")
    @CsvSource({
            "8x8,   Deux, Heuristique",
            "8x8,   Une,  Aleatoire",
            "10x10, Deux, Aleatoire",
            "10x10, Une,  Heuristique",
    })
    void chaqueConfigurationDeLEchantillonJoueUnMatch(String taille, String miTemps, String ia) {
        Variante v = variante(taille);
        int k = 3;
        Arbitre arbitre = miTemps.equals("Une")
                ? new ArbitreEnUneMiTemps(v::nouvellePartie, k, TirageAuSort.truque(Couleur.BLEUS))
                : new ArbitreStandard(v::nouvellePartie, k, TirageAuSort.truque(Couleur.BLEUS));
        Arbitre.Resultat r = arbitre.jouerLeMatch(ia(ia, 1), ia(ia, 2));
        assertEquals(miTemps.equals("Une") ? 1 : 2, r.miTemps().size());
        for (Partie p : r.miTemps()) {
            assertEquals(2 * k, p.journal().stream().filter(c -> !(c instanceof Coup.PoserBallon)).count());
        }
    }
}
