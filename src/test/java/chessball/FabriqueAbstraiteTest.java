package chessball;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TD6 ex. 4, Q14 : l'oracle de la Fabrique abstraite. Rouge sur le squelette.
 *
 * <p>Une variante livre une <b>famille</b> : le terrain et son placement, l'arbitrage, l'IA réglée
 * pour ce terrain. Le montage d'une partie est le même pour toutes.</p>
 */
class FabriqueAbstraiteTest {

    @Test
    void laVarianteStandardEstLeJeuDeBase() {
        Variante v = new VarianteStandard();
        Partie partie = v.nouvellePartie(Couleur.BLEUS);
        assertEquals(8, partie.plateau().taille());
        assertEquals(6, partie.plateau().piecesDe(Couleur.ROUGES).size());
        assertInstanceOf(AdversaireHeuristique.class, v.creerAdversaire(new Random(1)));
        assertEquals(Couleur.BLEUS, partie.trait(), "l'engageur demandé est respecté");
    }

    @Test
    void leGrandTerrainEstUneFamilleCoherente() {
        Variante v = new VarianteGrandTerrain();
        Partie partie = v.nouvellePartie(Couleur.ROUGES);
        Plateau plateau = partie.plateau();
        assertEquals(10, plateau.taille());
        for (Map.Entry<Position, Piece> e : plateau.occupation().entrySet()) {
            int rangee = e.getKey().rangee();
            if (e.getValue().couleur() == Couleur.ROUGES) assertTrue(rangee >= 8, "les Rouges partent du fond du 10×10 : " + e.getKey());
            else assertTrue(rangee <= 1, "les Bleus partent de leurs deux rangées : " + e.getKey());
        }
        assertInstanceOf(AdversaireAleatoire.class, v.creerAdversaire(new Random(1)), "l'heuristique n'est pas réglée pour ce terrain");
        assertEquals(Couleur.ROUGES, partie.trait());
    }

    @Test
    void laFamilleSeJoueTelleQuelle() {
        Variante v = new VarianteGrandTerrain();
        Arbitre arbitre = new ArbitreEnUneMiTemps(v::nouvellePartie, 5, TirageAuSort.truque(Couleur.BLEUS));
        Arbitre.Resultat r = arbitre.jouerLeMatch(v.creerAdversaire(new Random(1)), v.creerAdversaire(new Random(2)));
        assertEquals(1, r.miTemps().size());
        assertEquals(10, r.miTemps().get(0).plateau().taille());
        assertEquals(10, r.miTemps().get(0).journal().stream().filter(c -> !(c instanceof Coup.PoserBallon)).count());
    }

    @Test
    void leMontageEstFinalEtLesProduitsAbstraits() throws NoSuchMethodException {
        Method montage = Variante.class.getMethod("nouvellePartie", Couleur.class);
        assertTrue(Modifier.isFinal(montage.getModifiers()), "le montage ne varie pas : final");
        for (String produit : new String[]{"creerPlateau", "creerRegle"}) {
            Method m = Variante.class.getDeclaredMethod(produit);
            assertTrue(Modifier.isAbstract(m.getModifiers()) && Modifier.isProtected(m.getModifiers()),
                    produit + " : une méthode de création par produit, abstraite, que le client n'appelle pas");
        }
        Method ia = Variante.class.getDeclaredMethod("creerAdversaire", Random.class);
        assertTrue(Modifier.isAbstract(ia.getModifiers()));
    }
}
