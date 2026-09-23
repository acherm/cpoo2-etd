package chessball;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S05, Q2 bis : l'oracle du Composite des règles. Des règles élémentaires, chacune dans son
 * coin, et deux assemblages, {@code toutesLes} (le ET) et {@code lUneDes} (le OU), qui rendent
 * une {@link Regle} : une règle composée est une règle.
 */
class CompositeReglesTest {

    static final Regle OUI = (partie, coup) -> Verdict.ok();
    static final Regle NON_A = (partie, coup) -> Verdict.refus("A");
    static final Regle NON_B = (partie, coup) -> Verdict.refus("B");
    static final Coup A1_A4 = new Coup.Deplacement(Position.of("a1"), Position.of("a4"));

    @Test
    void traitRefuseLaPieceAdverse() {
        Partie partie = Partie.standard();                       // les Bleus ont le trait
        Verdict v = Regles.trait().verifier(partie, new Coup.Deplacement(Position.of("a8"), Position.of("a5")));
        assertFalse(v.accepte());
        assertEquals("ce n'est pas votre pièce", v.motif());
        assertTrue(Regles.trait().verifier(partie, A1_A4).accepte());
    }

    @Test
    void motifRespecteRefuseUneTourEnDiagonaleAvecSonMotif() {
        Partie partie = Partie.standard();
        Verdict v = Regles.motifRespecte().verifier(partie, new Coup.Deplacement(Position.of("a1"), Position.of("c3")));
        assertFalse(v.accepte());
        assertTrue(v.motif().contains("TOUR"), "le motif du refus nomme la pièce : " + v.motif());
        assertTrue(Regles.motifRespecte().verifier(partie, A1_A4).accepte());
        assertTrue(Regles.motifRespecte().verifier(partie, new Coup.PoserBallon(Position.of("d4"))).accepte(),
                "une règle élémentaire laisse passer les coups qui ne la concernent pas");
    }

    @Test
    void toutesLesEstLeEtOuLePremierRefusLEmporteAvecSonMotif() {
        Partie partie = Partie.standard();
        List<String> ordre = new ArrayList<>();
        Regle traceA = (p, c) -> { ordre.add("A"); return Verdict.refus("A"); };
        Regle traceB = (p, c) -> { ordre.add("B"); return Verdict.refus("B"); };
        Verdict v = Regles.toutesLes(OUI, traceA, traceB).verifier(partie, A1_A4);
        assertFalse(v.accepte());
        assertEquals("A", v.motif(), "le premier refus porte le motif");
        assertEquals(List.of("A"), ordre, "et l'on ne va pas plus loin");
        assertTrue(Regles.toutesLes(OUI, OUI).verifier(partie, A1_A4).accepte());
        assertTrue(Regles.toutesLes().verifier(partie, A1_A4).accepte(), "le ET vide accepte");
    }

    @Test
    void lUneDesEstLeOuLaPremiereAcceptationLEmporte() {
        Partie partie = Partie.standard();
        assertTrue(Regles.lUneDes(NON_A, OUI, NON_B).verifier(partie, A1_A4).accepte());
        Verdict v = Regles.lUneDes(NON_A, NON_B).verifier(partie, A1_A4);
        assertFalse(v.accepte());
        assertEquals("A", v.motif(), "aucune n'accepte : le premier refus est rendu");
    }

    @Test
    void uneRegleComposeeEstUneRegleQueLaPartieRecoitTelleQuelle() {
        Regle composee = Regles.toutesLes(Regles.trait(), Regles.motifRespecte(), Regles.passeValide(),
                Regles.tirValide(), Regles.engagementAuCentre());
        Partie parComposition = new Partie(Placements.standard(), composee, Couleur.BLEUS);
        Partie deReference = Partie.standard();
        Coup illegal = new Coup.Deplacement(Position.of("a1"), Position.of("c3"));
        Coup adverse = new Coup.Deplacement(Position.of("a8"), Position.of("a5"));
        for (Coup c : List.of(illegal, adverse, A1_A4)) {
            assertEquals(deReference.arbitrer(c).accepte(), parComposition.arbitrer(c).accepte(), c.toString());
        }
        assertTrue(parComposition.jouer(A1_A4).accepte());
        assertEquals(Couleur.ROUGES, parComposition.trait());
    }
}
