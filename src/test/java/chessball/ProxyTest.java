package chessball;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * S03 ex. 3, Q8 : l'oracle du Proxy. Rouge sur le squelette.
 *
 * <p>Le spectateur a la même interface que le joueur ({@code Jeu}), il ne peut pas jouer, la
 * partie réelle n'est pas touchée par ses tentatives, et ce qu'il a le droit de voir lui est
 * délégué tel quel. Le client ne fait aucun {@code instanceof}.</p>
 */
class ProxyTest {

    static final Coup LEGAL = new Coup.Deplacement(Position.of("a1"), Position.of("a4"));

    /** Le client : il tient un Jeu, il ne sait pas lequel. */
    static int coupsJoues(Jeu jeu) {
        return jeu.journal().size();
    }

    @Test
    void unSpectateurNePeutPasJouer() {
        Partie reelle = Partie.standard();
        Jeu vue = new VueSpectateur(reelle);
        Verdict v = vue.jouer(LEGAL);
        assertFalse(v.accepte(), "le coup était légal, mais pas pour un spectateur");
        assertFalse(v.motif().isBlank());
        assertEquals(0, reelle.journal().size(), "la partie réelle n'a rien vu passer");
        assertEquals(Couleur.BLEUS, reelle.trait(), "et le trait n'a pas bougé");
    }

    @Test
    void laLectureEstDelegueeTelleQuelle() {
        Partie reelle = Partie.standard();
        Jeu vue = new VueSpectateur(reelle);
        assertTrue(reelle.jouer(LEGAL).accepte());
        assertEquals(reelle.journal(), vue.journal());
        assertEquals(reelle.trait(), vue.trait());
        assertEquals(reelle.plateauEnTexte(), vue.plateauEnTexte());
        assertEquals(1, coupsJoues(vue));
    }

    @Test
    void laPartieResteJouableApresLesTentativesDuSpectateur() {
        Partie reelle = Partie.standard();
        Jeu vue = new VueSpectateur(reelle);
        vue.jouer(LEGAL);
        vue.jouer(new Coup.Deplacement(Position.of("h1"), Position.of("h4")));
        assertTrue(reelle.jouer(LEGAL).accepte(), "la tour est toujours en a1 : le proxy n'a rien exécuté");
        assertEquals(1, coupsJoues(reelle));
    }

    @Test
    void memeInterfaceEtLaPartieTenueParSonInterface() {
        assertTrue(Jeu.class.isAssignableFrom(VueSpectateur.class), "le spectateur EST un Jeu : le client ne voit pas la différence");
        assertTrue(Arrays.stream(VueSpectateur.class.getDeclaredFields())
                        .anyMatch(f -> f.getType() == Jeu.class && !Modifier.isStatic(f.getModifiers())),
                "le proxy détient un Jeu, typé par l'interface : un proxy peut en envelopper un autre");
        assertFalse(Arrays.stream(VueSpectateur.class.getDeclaredFields()).anyMatch(f -> f.getType() == Partie.class),
                "jamais la classe concrète");
    }
}
