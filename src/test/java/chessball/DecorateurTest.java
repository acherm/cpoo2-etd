package chessball;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S03 ex. 2, Q5 : l'oracle du Décorateur. Rouge sur le squelette.
 *
 * <p>Un décorateur <b>délègue</b> (même verdict que la partie enveloppée, même lecture),
 * <b>ajoute</b> (une ligne de journal par coup tenté), <b>s'empile</b> (deux traces imbriquées
 * reçoivent chacune leur ligne) et reste <b>substituable</b> (le client tient un {@code Jeu}).</p>
 */
class DecorateurTest {

    static final Coup LEGAL = new Coup.Deplacement(Position.of("a1"), Position.of("a4"));
    static final Coup ILLEGAL = new Coup.Deplacement(Position.of("a1"), Position.of("c3"));   // une tour en diagonale

    @Test
    void leVerdictEstCeluiDeLaPartieEnveloppee() {
        Partie reelle = Partie.standard();
        Jeu trace = new JeuTrace(reelle, new Journal.EnMemoire());
        assertFalse(trace.jouer(ILLEGAL).accepte());
        assertTrue(trace.jouer(LEGAL).accepte());
        assertEquals(1, reelle.journal().size(), "le coup a bien été joué sur la partie réelle");
        assertEquals(Couleur.ROUGES, trace.trait(), "la lecture est déléguée telle quelle");
    }

    @Test
    void chaqueCoupTenteEcritUneLigneQuiDitLeCoupEtLeVerdict() {
        Journal journal = new Journal.EnMemoire();
        Jeu trace = new JeuTrace(Partie.standard(), journal);
        trace.jouer(LEGAL);
        trace.jouer(ILLEGAL);
        assertEquals(2, journal.lignes().size(), "une ligne par coup tenté, accepté ou non");
        assertTrue(journal.lignes().get(0).contains("a1-a4"), journal.lignes().get(0));
        assertTrue(journal.lignes().get(1).contains("a1-c3") && journal.lignes().get(1).contains("refus"),
                "le coup refusé et son motif sont journalisés : " + journal.lignes().get(1));
    }

    @Test
    void deuxTracesSEmpilent() {
        Journal premier = new Journal.EnMemoire(), second = new Journal.EnMemoire();
        Jeu empile = new JeuTrace(new JeuTrace(Partie.standard(), premier), second);
        assertTrue(empile.jouer(LEGAL).accepte(), "le verdict traverse les deux couches");
        assertEquals(1, premier.lignes().size());
        assertEquals(1, second.lignes().size());
    }

    @Test
    void laPartieReelleNeSaitPasQuElleEstTracee() {
        Partie reelle = Partie.standard();
        Journal journal = new Journal.EnMemoire();
        Jeu trace = new JeuTrace(reelle, journal);
        trace.jouer(LEGAL);
        assertEquals(reelle.journal(), trace.journal(), "le journal des coups est celui de la partie");
        assertEquals(reelle.plateauEnTexte(), trace.plateauEnTexte());
        assertEquals(1, journal.lignes().size(), "et la trace est ailleurs : dans le journal injecté");
    }

    @Test
    void leDecorateurTientUnJeuParSonInterface() {
        assertTrue(Jeu.class.isAssignableFrom(JeuTrace.class));
        assertTrue(Arrays.stream(JeuTrace.class.getDeclaredFields())
                        .anyMatch(f -> f.getType() == Jeu.class && !Modifier.isStatic(f.getModifiers())),
                "le champ enveloppé est typé Jeu : c'est ce qui permet d'empiler");
        assertFalse(Arrays.stream(JeuTrace.class.getDeclaredFields()).anyMatch(f -> f.getType() == Partie.class),
                "jamais la classe concrète");
    }
}
