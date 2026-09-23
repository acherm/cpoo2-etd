package chessball;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** S09 : le générateur de coups légaux, fourni. Il est le carburant des IA et des propriétés. */
class GenerateurDeCoupsTest {

    @Test
    void avantLEngagementSeulesLesQuatreCasesCentralesSontJouables() {
        Partie partie = Partie.standard();
        List<Coup> coups = GenerateurDeCoups.coupsLegaux(partie);
        assertEquals(4, coups.size(), "d4, e4, d5, e5");
        assertTrue(coups.stream().allMatch(c -> c instanceof Coup.PoserBallon));
    }

    @Test
    void toutCoupGenereEstAccepteParLArbitre() {
        Partie partie = Partie.standard();
        assertTrue(partie.jouer(new Coup.PoserBallon(Position.of("d4"))).accepte());
        for (Coup coup : GenerateurDeCoups.coupsLegaux(partie)) {
            Partie neuve = rejouer(partie);
            Verdict verdict = neuve.jouer(coup);
            assertTrue(verdict.accepte(), coup + " : " + verdict.motif());
        }
    }

    @Test
    void lOrdreEstDeterministe() {
        Partie a = Partie.standard();
        Partie b = Partie.standard();
        a.jouer(new Coup.PoserBallon(Position.of("d4")));
        b.jouer(new Coup.PoserBallon(Position.of("d4")));
        assertEquals(GenerateurDeCoups.coupsLegaux(a), GenerateurDeCoups.coupsLegaux(b));
    }

    /** Une partie neuve amenée au même état, en rejouant le journal. */
    static Partie rejouer(Partie modele) {
        Partie neuve = Partie.standard();
        for (Coup c : modele.journal()) neuve.jouer(c);
        return neuve;
    }
}
