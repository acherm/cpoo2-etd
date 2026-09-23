package chessball;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S07 ex. 8, Q13 : l'oracle du Monteur. Rouge sur le squelette.
 *
 * <p>Ce que les tests fixent : chaque option a un nom et une valeur par défaut, les invariants ne
 * sont vérifiés que dans {@code construire()}, le produit est immuable et sert autant de parties
 * qu'on veut, indépendantes les unes des autres.</p>
 */
class ConfigurationTest {

    static final Coup A1_A4 = new Coup.Deplacement(Position.of("a1"), Position.of("a4"));

    @Test
    void parDefautCestLaPartieStandard() {
        Configuration c = Configuration.parDefaut();
        Partie p = c.nouvellePartie();
        assertEquals(Couleur.BLEUS, p.trait());
        assertEquals(Partie.standard().plateauEnTexte(), p.plateauEnTexte());
        assertEquals(Configuration.TOURS_PAR_MI_TEMPS_PAR_DEFAUT, c.toursParMiTemps());
        assertEquals(Configuration.GRAINE_PAR_DEFAUT, c.graine());
    }

    @Test
    void chaqueOptionAUnNomEtLOrdreDesAppelsEstLibre() {
        Configuration c = Configuration.nouvelle().graine(7L).premierTrait(Couleur.ROUGES).toursParMiTemps(5).construire();
        assertEquals(7L, c.graine());
        assertEquals(5, c.toursParMiTemps());
        assertEquals(Couleur.ROUGES, c.nouvellePartie().trait());
    }

    @Test
    void lesInvariantsSontVerifiesDansConstruireEtNullePartAilleurs() {
        Configuration.Monteur m = Configuration.nouvelle().toursParMiTemps(0);   // ne lève pas : rien n'est encore décidé
        assertThrows(IllegalStateException.class, m::construire);
        Configuration.Monteur vide = Configuration.nouvelle().plateau(new Plateau(8));
        assertThrows(IllegalStateException.class, vide::construire, "un plateau sans pièces n'est pas une partie");
    }

    @Test
    void deuxPartiesDeLaMemeConfigurationSontIndependantes() {
        Configuration c = Configuration.parDefaut();
        Partie p1 = c.nouvellePartie();
        Partie p2 = c.nouvellePartie();
        assertTrue(p1.jouer(A1_A4).accepte());
        assertEquals(0, p2.journal().size(), "p2 n'a pas bougé");
        assertNotEquals(p1.plateauEnTexte(), p2.plateauEnTexte());
        assertEquals(Partie.standard().plateauEnTexte(), c.nouvellePartie().plateauEnTexte(), "la configuration non plus");
    }

    @Test
    void lesAbonnesSontEnPlaceDesLePremierCoup() {
        int[] vus = {0};
        EcouteurDePartie compteur = (coup, verdict) -> vus[0]++;
        Partie p = Configuration.nouvelle().ecouteur(compteur).construire().nouvellePartie();
        p.jouer(A1_A4);
        assertEquals(1, vus[0]);
    }

    @Test
    void leProduitEstImmuableEtNeSeConstruitQueParLeMonteur() {
        assertEquals(0, Configuration.class.getConstructors().length, "aucun constructeur public : tout passe par nouvelle()");
        boolean unSetter = Arrays.stream(Configuration.class.getMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .map(Method::getName)
                .anyMatch(n -> n.startsWith("set"));
        assertTrue(!unSetter, "une configuration ne se modifie pas après construire()");
    }
}
