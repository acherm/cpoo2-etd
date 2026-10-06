package cpoo2.s05.bt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/** S05, exercice 3, Q12 : le <b>Composite</b> et le <b>Décorateur</b> du behaviour tree. Tous rouges sur le squelette. */
class BehaviourTreeTest {

    final List<String> ticks = new ArrayList<>();

    /** Une feuille témoin : elle note son passage et rend ce qu'on lui dit. */
    Noeud feuille(String nom, Statut resultat) {
        return ctx -> {
            ticks.add(nom);
            return resultat;
        };
    }

    @Test
    void laSequenceSArreteAuPremierEchec() {
        Noeud s = Sequence.de(feuille("a", Statut.SUCCES), feuille("b", Statut.ECHEC), feuille("c", Statut.SUCCES));
        assertEquals(Statut.ECHEC, s.tick(null));
        assertEquals(List.of("a", "b"), ticks, "c n'est jamais essayé");
    }

    @Test
    void laSequenceReussitSiTousReussissent() {
        assertEquals(Statut.SUCCES, Sequence.de(feuille("a", Statut.SUCCES), feuille("b", Statut.SUCCES)).tick(null));
        assertEquals(Statut.SUCCES, Sequence.de().tick(null), "rien à faire : réussi");
    }

    @Test
    void leSelecteurSArreteAuPremierSucces() {
        Noeud s = Selecteur.de(feuille("a", Statut.ECHEC), feuille("b", Statut.SUCCES), feuille("c", Statut.SUCCES));
        assertEquals(Statut.SUCCES, s.tick(null));
        assertEquals(List.of("a", "b"), ticks, "l'ordre des enfants est la priorité");
        assertEquals(Statut.ECHEC, Selecteur.de().tick(null), "rien à essayer : échoué");
    }

    @Test
    void lInverseurInverse() {
        assertEquals(Statut.ECHEC, new Inverseur(feuille("a", Statut.SUCCES)).tick(null));
        assertEquals(Statut.SUCCES, new Inverseur(feuille("b", Statut.ECHEC)).tick(null));
    }

    @Test
    void unSousArbreEstUnNoeudCommeUnAutre() {
        Noeud arbre = Selecteur.de(
                Sequence.de(feuille("a", Statut.SUCCES), new Inverseur(feuille("b", Statut.SUCCES))),
                Sequence.de(feuille("c", Statut.SUCCES), feuille("d", Statut.SUCCES)));
        assertEquals(Statut.SUCCES, arbre.tick(null));
        assertEquals(List.of("a", "b", "c", "d"), ticks);
    }
}
