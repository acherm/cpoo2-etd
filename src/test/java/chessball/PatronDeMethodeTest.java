package chessball;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TD6 ex. 2, Q9 bis : l'oracle du Patron de méthode. Rouge sur le squelette.
 *
 * <p>L'algorithme du match est écrit une fois, {@code final}, dans {@code Arbitre} ; les variantes
 * remplissent des trous. Un espion de test redéfinit le crochet {@code avantMiTemps} pour observer
 * qui engage chaque mi-temps.</p>
 */
class PatronDeMethodeTest {

    static final Function<Couleur, Partie> MI_TEMPS_STANDARD =
            engageur -> new Partie(Placements.standard(), Regles.duJeuDeBase(), engageur);

    static long toursJoues(Partie partie) {
        return partie.journal().stream().filter(c -> !(c instanceof Coup.PoserBallon)).count();
    }

    /** L'espion : le crochet sert à observer, le squelette ne bouge pas. */
    static final class ArbitreEspion extends ArbitreStandard {
        final List<Couleur> engageurs = new ArrayList<>();
        ArbitreEspion(TirageAuSort tirage) { super(MI_TEMPS_STANDARD, 3, tirage); }
        @Override protected void avantMiTemps(int miTemps, Partie partie) { engageurs.add(partie.trait()); }
    }

    @Test
    void deuxMiTempsEtLEngagementAlterne() {
        ArbitreEspion arbitre = new ArbitreEspion(TirageAuSort.truque(Couleur.BLEUS));
        Arbitre.Resultat resultat = arbitre.jouerLeMatch(new AdversaireAleatoire(new Random(1)), new AdversaireAleatoire(new Random(2)));
        assertEquals(2, resultat.miTemps().size());
        assertEquals(List.of(Couleur.BLEUS, Couleur.ROUGES), arbitre.engageurs, "J12 : l'autre équipe engage la seconde mi-temps");
        assertEquals(Couleur.BLEUS, resultat.premierEngageur());
    }

    @Test
    void chaqueMiTempsJoueKToursParEquipe() {
        Arbitre arbitre = new ArbitreStandard(MI_TEMPS_STANDARD, 4, TirageAuSort.truque(Couleur.ROUGES));
        Arbitre.Resultat resultat = arbitre.jouerLeMatch(new AdversaireAleatoire(new Random(5)), new AdversaireAleatoire(new Random(6)));
        for (Partie miTemps : resultat.miTemps()) assertEquals(8, toursJoues(miTemps), "K tours pour chaque équipe");
    }

    @Test
    void leScoreEstLaSommeDesMiTemps() {
        Arbitre arbitre = new ArbitreStandard(MI_TEMPS_STANDARD, 12, TirageAuSort.truque(Couleur.BLEUS));
        Arbitre.Resultat resultat = arbitre.jouerLeMatch(new AdversaireHeuristique(new Random(8)), new AdversaireHeuristique(new Random(9)));
        int bleus = resultat.miTemps().stream().mapToInt(p -> p.score(Couleur.BLEUS)).sum();
        int rouges = resultat.miTemps().stream().mapToInt(p -> p.score(Couleur.ROUGES)).sum();
        assertEquals(bleus, resultat.butsBleus());
        assertEquals(rouges, resultat.butsRouges());
    }

    @Test
    void laVarianteUneMiTempsTientEnDeuxMethodes() {
        Arbitre arbitre = new ArbitreEnUneMiTemps(MI_TEMPS_STANDARD, 3, TirageAuSort.truque(Couleur.ROUGES));
        Arbitre.Resultat resultat = arbitre.jouerLeMatch(new AdversaireAleatoire(new Random(1)), new AdversaireAleatoire(new Random(2)));
        assertEquals(1, resultat.miTemps().size());
        assertEquals(6, toursJoues(resultat.miTemps().get(0)));
    }

    @Test
    void leSqueletteEstFinalEtLesTrousProteges() throws NoSuchMethodException {
        Method squelette = Arbitre.class.getMethod("jouerLeMatch", StrategieAdversaire.class, StrategieAdversaire.class);
        assertTrue(Modifier.isFinal(squelette.getModifiers()), "l'algorithme n'est pas une suggestion : final");
        Method trou = Arbitre.class.getDeclaredMethod("engageurDeLaMiTemps", int.class, Couleur.class);
        assertTrue(Modifier.isAbstract(trou.getModifiers()) && Modifier.isProtected(trou.getModifiers()),
                "le trou obligatoire est abstrait et protégé : ni public, ni rempli par défaut");
        Method crochet = Arbitre.class.getDeclaredMethod("nombreDeMiTemps");
        assertTrue(!Modifier.isAbstract(crochet.getModifiers()) && Modifier.isProtected(crochet.getModifiers()),
                "le crochet facultatif a une valeur par défaut");
    }
}
