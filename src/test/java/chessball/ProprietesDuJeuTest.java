package chessball;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * S09, partie A : des propriétés sur l'application, écrites en JUnit avec une boucle à graine.
 * Ce sont les mêmes énoncés que vous rejouerez avec jqwik puis avec votre MiniCheck : ici on les
 * lit, on les fait tourner, et on note que chaque essai repart d'une partie neuve.
 */
class ProprietesDuJeuTest {

    static final int PARTIES = 30;
    static final int DEMI_COUPS = 40;

    /** Une partie jouée au hasard depuis la position standard, rejouable à graine égale. */
    static Partie partieAuHasard(long graine, int demiCoups) {
        Partie partie = Partie.standard();
        Random random = new Random(graine);
        for (int i = 0; i < demiCoups; i++) {
            var coups = GenerateurDeCoups.coupsLegaux(partie);
            if (coups.isEmpty()) break;
            partie.jouer(coups.get(random.nextInt(coups.size())));
        }
        return partie;
    }

    @Test
    void unCoupRefuseNeChangeJamaisLEtat() {
        for (long graine = 1; graine <= PARTIES; graine++) {
            Partie partie = partieAuHasard(graine, DEMI_COUPS);
            String plateauAvant = partie.plateauEnTexte();
            Couleur traitAvant = partie.trait();
            int bleus = partie.score(Couleur.BLEUS), rouges = partie.score(Couleur.ROUGES);
            int coupsAvant = partie.journal().size();

            Verdict verdict = partie.jouer(new Coup.Deplacement(Position.of("a4"), Position.of("a5")));   // a4 est vide au départ, ou occupée par n'importe qui

            if (!verdict.accepte()) {
                assertEquals(plateauAvant, partie.plateauEnTexte(), "graine " + graine);
                assertEquals(traitAvant, partie.trait());
                assertEquals(bleus, partie.score(Couleur.BLEUS));
                assertEquals(rouges, partie.score(Couleur.ROUGES));
                assertEquals(coupsAvant, partie.journal().size());
            }
        }
    }

    @Test
    void leScoreNeDiminueJamais() {
        for (long graine = 1; graine <= PARTIES; graine++) {
            Partie partie = Partie.standard();
            Random random = new Random(graine);
            int bleus = 0, rouges = 0;
            for (int i = 0; i < DEMI_COUPS; i++) {
                var coups = GenerateurDeCoups.coupsLegaux(partie);
                if (coups.isEmpty()) break;
                partie.jouer(coups.get(random.nextInt(coups.size())));
                assertTrue(partie.score(Couleur.BLEUS) >= bleus && partie.score(Couleur.ROUGES) >= rouges, "graine " + graine);
                bleus = partie.score(Couleur.BLEUS);
                rouges = partie.score(Couleur.ROUGES);
            }
        }
    }

    @Test
    void leBallonPorteEstToujoursSousSaPorteuse() {
        for (long graine = 1; graine <= PARTIES; graine++) {
            Partie partie = partieAuHasard(graine, DEMI_COUPS);
            Plateau plateau = partie.plateau();
            final long g = graine;
            plateau.porteuse().ifPresent(porteuse ->
                    assertEquals(plateau.positionDe(porteuse).orElseThrow(), plateau.positionDuBallon().orElseThrow(), "graine " + g));
        }
    }

    /** Différentielle : l'arbitre et le générateur doivent être d'accord, coup par coup. */
    @Test
    void lArbitreEtLeGenerateurSontDAccordSurLesDeplacements() {
        for (long graine = 1; graine <= PARTIES; graine++) {
            Partie partie = partieAuHasard(graine, DEMI_COUPS);
            if (partie.plateau().positionDuBallon().isEmpty()) continue;   // avant l'engagement, seul PoserBallon est légal
            var generes = new java.util.HashSet<Coup>(GenerateurDeCoups.coupsLegaux(partie));
            int taille = partie.plateau().taille();
            for (int c1 = 0; c1 < taille; c1++) for (int r1 = 0; r1 < taille; r1++)
                for (int c2 = 0; c2 < taille; c2++) for (int r2 = 0; r2 < taille; r2++) {
                    Coup coup = new Coup.Deplacement(new Position(c1, r1), new Position(c2, r2));
                    boolean accepte = GenerateurDeCoupsTest.rejouer(partie).jouer(coup).accepte();
                    assertEquals(generes.contains(coup), accepte,
                            "graine " + graine + ", " + coup + " : générateur et arbitre ne sont pas d'accord");
                }
        }
    }

    @Test
    void rejouerLeJournalReproduitLaPartie() {
        for (long graine = 1; graine <= PARTIES; graine++) {
            Partie partie = partieAuHasard(graine, DEMI_COUPS);
            Partie rejouee = GenerateurDeCoupsTest.rejouer(partie);
            assertEquals(partie.plateauEnTexte(), rejouee.plateauEnTexte(), "graine " + graine);
            assertEquals(partie.journal(), rejouee.journal());
            assertEquals(partie.score(Couleur.BLEUS), rejouee.score(Couleur.BLEUS));
            assertEquals(partie.score(Couleur.ROUGES), rejouee.score(Couleur.ROUGES));
        }
    }
}
