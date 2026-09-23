package chessball;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/** S09, Q11 bis : l'Objet actif. Une IA lente sous délai, et le match qui continue. */
class AdversaireAsynchroneTest {

    static Partie partieEngagee() {
        Partie partie = Partie.standard();
        partie.jouer(new Coup.PoserBallon(Position.of("d4")));
        return partie;
    }

    @Test
    void uneIaRapideRendSonCoup() {
        Function<Partie, Coup> rapide = p -> GenerateurDeCoups.coupsLegaux(p).get(0);
        Function<Partie, Coup> repli = p -> GenerateurDeCoups.coupsLegaux(p).get(1);
        try (AdversaireAsynchrone ia = new AdversaireAsynchrone(rapide, repli, 500)) {
            Partie partie = partieEngagee();
            assertEquals(rapide.apply(partie), ia.apply(partie), "à temps, c'est le coup réfléchi");
        }
    }

    @Test
    void uneIaTropLenteEstRemplaceeParLeRepliDansLeDelai() {
        Function<Partie, Coup> reflechi = new AdversaireLent(p -> GenerateurDeCoups.coupsLegaux(p).get(0), 2_000);
        Function<Partie, Coup> repli = p -> GenerateurDeCoups.coupsLegaux(p).get(1);
        try (AdversaireAsynchrone ia = new AdversaireAsynchrone(reflechi, repli, 100)) {
            Partie partie = partieEngagee();
            Instant debut = Instant.now();
            Coup coup = ia.apply(partie);
            Duration duree = Duration.between(debut, Instant.now());
            assertEquals(repli.apply(partie), coup, "délai dépassé : le coup de repli");
            assertTrue(duree.toMillis() < 1_500, "l'appelant n'a pas attendu les deux secondes : " + duree.toMillis() + " ms");
        }
    }

    @Test
    void proposerRendUnJetonAvantLeResultat() throws Exception {
        Function<Partie, Coup> reflechi = new AdversaireLent(p -> GenerateurDeCoups.coupsLegaux(p).get(0), 300);
        try (AdversaireAsynchrone ia = new AdversaireAsynchrone(reflechi, reflechi, 5_000)) {
            Partie partie = partieEngagee();
            CompletableFuture<Coup> jeton = ia.proposer(partie);
            assertFalse(jeton.isDone(), "l'appel est revenu tout de suite, le résultat n'est pas là");
            assertNotNull(jeton.get(), "et il finit par arriver");
            assertTrue(jeton.isDone());
        }
    }

    @Test
    void unObjetActifFermeNePrendPlusDeRequete() {
        AdversaireAsynchrone ia = new AdversaireAsynchrone(p -> GenerateurDeCoups.coupsLegaux(p).get(0),
                p -> GenerateurDeCoups.coupsLegaux(p).get(0), 100);
        ia.close();
        assertThrows(RejectedExecutionException.class, () -> ia.proposer(partieEngagee()));
    }

    @Test
    void leMatchContinueAvecUneIaLenteSousDelai() {
        Function<Partie, Coup> lente = new AdversaireLent(Adversaires.presse(1), 50);
        try (AdversaireAsynchrone bleus = new AdversaireAsynchrone(lente, Adversaires.auHasard(1), 10)) {
            Instant debut = Instant.now();
            Partie partie = Main.jouerUnMatch(bleus, Adversaires.auHasard(2), 40);
            assertTrue(partie.journal().size() >= 40 || partie.score(Couleur.BLEUS) >= Main.BUTS_POUR_GAGNER
                    || partie.score(Couleur.ROUGES) >= Main.BUTS_POUR_GAGNER);
            assertTrue(Duration.between(debut, Instant.now()).toMillis() < 3_000, "vingt coups bleus à 10 ms de délai, pas à 50");
        }
    }
}
