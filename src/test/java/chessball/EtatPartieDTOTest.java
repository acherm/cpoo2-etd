package chessball;

import org.junit.jupiter.api.Test;

import java.lang.reflect.RecordComponent;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S07 ex. 9, Q15 : l'oracle du DTO. Rouge sur le squelette.
 *
 * <p>Ce que les tests fixent : l'état est une copie figée au moment où on la prend, il ne contient
 * que des données lisibles sans nos classes, il se construit depuis l'interface {@link Jeu} et
 * d'elle seule, et il ne transporte que les derniers coups.</p>
 */
class EtatPartieDTOTest {

    static final Coup A1_A4 = new Coup.Deplacement(Position.of("a1"), Position.of("a4"));

    @Test
    void lEtatEstUneCopieFigee() {
        Partie partie = Partie.standard();
        EtatPartieDTO avant = EtatPartieDTO.de(partie);
        assertTrue(partie.jouer(A1_A4).accepte());
        assertEquals(0, avant.coupsJoues(), "l'état pris avant le coup n'a pas bougé");
        assertEquals(Couleur.BLEUS, avant.trait());
        assertEquals(Couleur.ROUGES, partie.trait());
        assertEquals(1, EtatPartieDTO.de(partie).coupsJoues());
    }

    @Test
    void lesCoupsVoyagentEnNotationPasEnObjets() {
        Partie partie = Partie.standard();
        partie.jouer(A1_A4);
        assertEquals(List.of("a1-a4"), EtatPartieDTO.de(partie).derniersCoups());
    }

    @Test
    void seulsLesDerniersCoupsVoyagent() {
        List<Coup> sept = List.of(
                new Coup.Deplacement(Position.of("a1"), Position.of("a2")),
                new Coup.Deplacement(Position.of("a2"), Position.of("a3")),
                new Coup.Deplacement(Position.of("a3"), Position.of("a4")),
                new Coup.Deplacement(Position.of("a4"), Position.of("a5")),
                new Coup.Deplacement(Position.of("a5"), Position.of("a6")),
                new Coup.Deplacement(Position.of("a6"), Position.of("a7")),
                new Coup.Deplacement(Position.of("a7"), Position.of("a8")));
        Jeu faux = new Jeu() {   // le DTO ne connaît que l'interface : n'importe quel Jeu convient
            @Override public Verdict jouer(Coup coup) { return Verdict.refus("figé"); }
            @Override public Couleur trait() { return Couleur.ROUGES; }
            @Override public int score(Couleur couleur) { return couleur == Couleur.BLEUS ? 2 : 1; }
            @Override public List<Coup> journal() { return sept; }
            @Override public String plateauEnTexte() { return "(plateau)"; }
        };
        EtatPartieDTO etat = EtatPartieDTO.de(faux);
        assertEquals(7, etat.coupsJoues());
        assertEquals(EtatPartieDTO.DERNIERS_COUPS, etat.derniersCoups().size());
        assertEquals("a7-a8", etat.derniersCoups().get(etat.derniersCoups().size() - 1));
        assertEquals(2, etat.scoreBleus());
        assertEquals(1, etat.scoreRouges());
    }

    @Test
    void leJsonSeLitSansNosClasses() {
        Partie partie = Partie.standard();
        partie.jouer(A1_A4);
        String json = EtatPartieDTO.de(partie).enJson();
        assertTrue(json.contains("\"trait\":\"ROUGES\""), json);
        assertTrue(json.contains("\"scoreBleus\":0"), json);
        assertTrue(json.contains("\"derniersCoups\":[\"a1-a4\"]"), json);
        assertFalse(json.contains("chessball."), "aucun nom de classe ne traverse : " + json);
        assertFalse(json.contains("@"), "aucun Object.toString() ne traverse : " + json);
    }

    @Test
    void leTexteCommenceParLeScore() {
        String texte = EtatPartieDTO.de(Partie.standard()).enTexte();
        assertTrue(texte.startsWith("Bleus 0 - 0 Rouges"), texte);
        assertTrue(texte.contains("trait aux BLEUS"), texte);
    }

    @Test
    void aucuneReferenceVersLeModele() {
        Set<Class<?>> admis = Set.of(int.class, String.class, Couleur.class, List.class);
        for (RecordComponent c : EtatPartieDTO.class.getRecordComponents()) {
            assertTrue(admis.contains(c.getType()), c.getName() + " est un " + c.getType().getSimpleName()
                    + " : le DTO ne doit tenir que des données plates");
        }
    }
}
