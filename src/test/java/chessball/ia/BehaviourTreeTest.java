package chessball.ia;

import chessball.Partie;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** S05, Q10 : l'oracle du moteur de behaviour tree, avec des feuilles en lambda. */
class BehaviourTreeTest {

    static Contexte ctx() { return new Contexte(Partie.standard(), chessball.Couleur.BLEUS); }

    static Noeud.Action trace(List<String> journal, String nom, Statut statut) {
        return new Noeud.Action(nom, c -> { journal.add(nom); return statut; });
    }

    @Test
    void uneSequenceSArreteAuPremierEnfantQuiNeReussitPas() {
        List<String> journal = new ArrayList<>();
        Noeud seq = new Noeud.Sequence(trace(journal, "a", Statut.SUCCES), trace(journal, "b", Statut.ECHEC),
                trace(journal, "c", Statut.SUCCES));
        assertEquals(Statut.ECHEC, seq.tick(ctx()));
        assertEquals(List.of("a", "b"), journal, "c n'est jamais évalué");
        journal.clear();
        assertEquals(Statut.SUCCES, new Noeud.Sequence(trace(journal, "a", Statut.SUCCES)).tick(ctx()));
    }

    @Test
    void unSelecteurSArreteAuPremierEnfantQuiNEchouePas() {
        List<String> journal = new ArrayList<>();
        Noeud sel = new Noeud.Selecteur(trace(journal, "a", Statut.ECHEC), trace(journal, "b", Statut.SUCCES),
                trace(journal, "c", Statut.SUCCES));
        assertEquals(Statut.SUCCES, sel.tick(ctx()));
        assertEquals(List.of("a", "b"), journal);
        assertEquals(Statut.ECHEC, new Noeud.Selecteur(trace(journal, "x", Statut.ECHEC)).tick(ctx()));
    }

    @Test
    void enCoursRemonteSansEtreConfonduAvecUnEchec() {
        List<String> journal = new ArrayList<>();
        Noeud seq = new Noeud.Sequence(trace(journal, "longue", Statut.EN_COURS), trace(journal, "b", Statut.SUCCES));
        assertEquals(Statut.EN_COURS, seq.tick(ctx()));
        Noeud sel = new Noeud.Selecteur(trace(journal, "longue", Statut.EN_COURS), trace(journal, "b", Statut.SUCCES));
        assertEquals(Statut.EN_COURS, sel.tick(ctx()), "le sélecteur n'essaie pas la branche suivante pendant une action longue");
    }

    @Test
    void lInverseurRetourneLeStatutEtLaisseEnCours() {
        Noeud vrai = new Noeud.Condition("vrai", c -> true);
        Noeud faux = new Noeud.Condition("faux", c -> false);
        assertEquals(Statut.ECHEC, new Noeud.Inverseur(vrai).tick(ctx()));
        assertEquals(Statut.SUCCES, new Noeud.Inverseur(faux).tick(ctx()));
        assertEquals(Statut.EN_COURS, new Noeud.Inverseur(new Noeud.Action("longue", c -> Statut.EN_COURS)).tick(ctx()));
    }

    @Test
    void repeterSArreteAuPremierEchec() {
        int[] compteur = {0};
        Noeud compte = new Noeud.Action("compte", c -> { compteur[0]++; return compteur[0] < 3 ? Statut.SUCCES : Statut.ECHEC; });
        assertEquals(Statut.ECHEC, new Noeud.Repeter(compte, 5).tick(ctx()));
        assertEquals(3, compteur[0], "deux succès puis l'échec : trois ticks, pas cinq");
        compteur[0] = 0;
        assertEquals(Statut.SUCCES, new Noeud.Repeter(compte, 2).tick(ctx()));
    }
}
