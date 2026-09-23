package cpoo2.s05.ecs;

import chessball.Couleur;
import chessball.TypePiece;
import chessball.Verdict;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** TD5, partie A, Q1 et Q3 : l'oracle du mini-ECS. */
class MondeTest {

    @Test
    void uneEntiteNEstQuUnIdentifiantEtSesComposantsSontDesDonnees() {
        Monde monde = new Monde();
        int tour = monde.creerEntite();
        int fou = monde.creerEntite();
        assertTrue(tour != fou);
        monde.ajouter(tour, new Monde.Case(0, 0));
        monde.ajouter(tour, new Monde.Equipe(Couleur.BLEUS));
        monde.ajouter(tour, new Monde.Motif(TypePiece.TOUR));
        assertEquals(new Monde.Case(0, 0), monde.composant(tour, Monde.Case.class).orElseThrow());
        assertTrue(monde.composant(fou, Monde.Case.class).isEmpty(), "l'absence est un Optional vide, pas un null");
        monde.retirer(tour, Monde.Motif.class);
        assertTrue(monde.composant(tour, Monde.Motif.class).isEmpty());
    }

    @Test
    void entitesAvecEstUneIntersection() {
        Monde monde = new Monde();
        int a = monde.creerEntite(), b = monde.creerEntite(), c = monde.creerEntite();
        monde.ajouter(a, new Monde.Case(0, 0));
        monde.ajouter(a, new Monde.PorteurBallon());
        monde.ajouter(b, new Monde.Case(1, 1));
        monde.ajouter(c, new Monde.PorteurBallon());
        assertEquals(Set.of(a, b), monde.entitesAvec(Monde.Case.class));
        assertEquals(Set.of(a), monde.entitesAvec(Monde.Case.class, Monde.PorteurBallon.class));
        assertEquals(Set.of(), monde.entitesAvec(Monde.Case.class, Monde.Motif.class));
    }

    @Test
    void leSystemeDeDeplacementAppliqueLaRegleEtConsommeLIntention() {
        Monde monde = new Monde();
        int tour = monde.creerEntite();
        monde.ajouter(tour, new Monde.Case(0, 0));
        monde.ajouter(tour, new Monde.Motif(TypePiece.TOUR));
        Monde.RegleDuMonde enLigne = (m, e, cible) -> {
            Monde.Case depart = m.composant(e, Monde.Case.class).orElseThrow();
            return depart.colonne() == cible.colonne() || depart.rangee() == cible.rangee()
                    ? Verdict.ok() : Verdict.refus("une tour glisse en ligne");
        };
        Monde.Systeme systeme = Monde.systemeDeplacement(enLigne);

        monde.ajouter(tour, new Monde.VeutSeDeplacer(new Monde.Case(0, 5)));
        systeme.mettreAJour(monde);
        assertEquals(new Monde.Case(0, 5), monde.composant(tour, Monde.Case.class).orElseThrow(), "légal : appliqué");
        assertTrue(monde.composant(tour, Monde.VeutSeDeplacer.class).isEmpty(), "l'intention est consommée");

        monde.ajouter(tour, new Monde.VeutSeDeplacer(new Monde.Case(3, 2)));
        systeme.mettreAJour(monde);
        assertEquals(new Monde.Case(0, 5), monde.composant(tour, Monde.Case.class).orElseThrow(), "illégal : rien ne bouge");
        assertTrue(monde.composant(tour, Monde.VeutSeDeplacer.class).isEmpty(), "et l'intention est consommée aussi");
    }
}
