package chessball;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** S09, Q11 ter : les coups d'une partie publiés en flux, deux abonnés, la contre-pression. */
class FluxDeCoupsTest {

    /** Quelques événements : un coup accepté, un refusé, un accepté. */
    static void publierTrois(FluxDeCoups flux) {
        flux.surCoupTente(new Coup.PoserBallon(Position.of("d4")), Verdict.ok());
        flux.surCoupTente(new Coup.Deplacement(Position.of("a4"), Position.of("a5")), Verdict.refus("aucune pièce en a4"));
        flux.surCoupTente(new Coup.Deplacement(Position.of("e2"), Position.of("d4")), Verdict.ok());
    }

    @Test
    void deuxAbonnesRecoiventChacunCeQuiLesInteresse() throws InterruptedException {
        FluxDeCoups.AbonneFiltrant tout = FluxDeCoups.AbonneFiltrant.tout();
        FluxDeCoups.AbonneFiltrant refus = new FluxDeCoups.AbonneFiltrant(e -> !e.verdict().accepte(), 16);
        try (FluxDeCoups flux = new FluxDeCoups()) {
            flux.abonner(tout);
            flux.abonner(refus);
            publierTrois(flux);
        }
        assertTrue(tout.attendreLaFin(2_000) && refus.attendreLaFin(2_000), "close() signale la fin à chacun");
        assertEquals(3, tout.recus().size());
        assertEquals(1, refus.recus().size());
        assertEquals("aucune pièce en a4", refus.recus().get(0).verdict().motif());
    }

    @Test
    void unAbonneQuiRedemandeUnParUnFinitParToutRecevoir() throws InterruptedException {
        FluxDeCoups.AbonneFiltrant lent = new FluxDeCoups.AbonneFiltrant(e -> true, 1);
        try (FluxDeCoups flux = new FluxDeCoups(2)) {       // tampon de deux : submit bloque si l'abonné traîne
            flux.abonner(lent);
            for (int i = 0; i < 10; i++) publierTrois(flux);
        }
        assertTrue(lent.attendreLaFin(2_000));
        assertEquals(30, lent.recus().size(), "la contre-pression régule, elle ne perd rien");
    }

    @Test
    void laPartieAlimenteLeFluxCommeNImporteQuelEcouteur() throws InterruptedException {
        FluxDeCoups.AbonneFiltrant acceptes = new FluxDeCoups.AbonneFiltrant(e -> e.verdict().accepte(), 8);
        Partie partie = Partie.standard();
        try (FluxDeCoups flux = new FluxDeCoups()) {
            flux.abonner(acceptes);
            partie.ajouterEcouteur(flux);                    // S04 : la partie ne voit qu'un écouteur de plus
            Main.jouer(partie, Adversaires.presse(1), Adversaires.auHasard(2), 20);
        }
        assertTrue(acceptes.attendreLaFin(2_000));
        List<Coup> journal = partie.journal();
        assertEquals(journal, acceptes.recus().stream().map(FluxDeCoups.Evenement::coup).toList());
    }
}
