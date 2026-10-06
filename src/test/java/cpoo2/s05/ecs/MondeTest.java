package cpoo2.s05.ecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Plateau;
import cpoo2.jeu.TypePiece;

/** S05, exercice 4 : l'oracle du mini-ECS. Tous rouges sur le squelette. */
class MondeTest {

    @Test
    void uneEntiteNEstQuUnIdentifiantEtSesComposantsSontDesDonnees() {
        Monde monde = new Monde();
        int attaquant = monde.creerEntite();
        int ballon = monde.creerEntite();
        assertTrue(attaquant != ballon);
        monde.ajouter(attaquant, new Monde.Case(2, 4));
        monde.ajouter(attaquant, new Monde.Equipe(Couleur.BLEUS));
        monde.ajouter(attaquant, new Monde.Role(TypePiece.ATTAQUANT));
        assertEquals(new Monde.Case(2, 4), monde.composant(attaquant, Monde.Case.class).orElseThrow());
        assertTrue(monde.composant(ballon, Monde.Case.class).isEmpty(), "l'absence est un Optional vide, pas un null");
        monde.retirer(attaquant, Monde.Role.class);
        assertTrue(monde.composant(attaquant, Monde.Role.class).isEmpty());
    }

    @Test
    void entitesAvecEstUneIntersection() {
        Monde monde = new Monde();
        int a = monde.creerEntite(), b = monde.creerEntite(), c = monde.creerEntite();
        monde.ajouter(a, new Monde.Case(3, 3));
        monde.ajouter(a, new Monde.Ballon());
        monde.ajouter(b, new Monde.Case(1, 1));
        monde.ajouter(c, new Monde.Ballon());
        assertEquals(Set.of(a, b), monde.entitesAvec(Monde.Case.class));
        assertEquals(Set.of(a), monde.entitesAvec(Monde.Case.class, Monde.Ballon.class));
        assertEquals(Set.of(), monde.entitesAvec(Monde.Case.class, Monde.Role.class));
    }

    @Test
    void laPositionDeDepartFaitOnzeEntites() {
        Monde monde = Monde.depuis(Plateau.officiel());
        assertEquals(11, monde.entitesAvec(Monde.Case.class).size());
        assertEquals(10, monde.entitesAvec(Monde.Case.class, Monde.Role.class).size());
        assertEquals(1, monde.entitesAvec(Monde.Ballon.class).size());
    }

    @Test
    void leSystemeDeDeplacementAppliqueLaRegleEtConsommeLIntention() {
        Monde monde = Monde.depuis(Plateau.officiel());
        int c5 = monde.entitesAvec(Monde.Case.class, Monde.Role.class).stream()
                .filter(e -> monde.composant(e, Monde.Case.class).orElseThrow().equals(new Monde.Case(2, 4)))
                .findFirst().orElseThrow();
        Monde.Systeme systeme = Monde.systemeDeplacement(Monde.unPasVersUneCaseLibre());

        monde.ajouter(c5, new Monde.VeutSeDeplacer(new Monde.Case(3, 3)));     // d4 : la case du ballon
        systeme.mettreAJour(monde);
        assertEquals(new Monde.Case(2, 4), monde.composant(c5, Monde.Case.class).orElseThrow(), "refusé : rien ne bouge");
        assertTrue(monde.composant(c5, Monde.VeutSeDeplacer.class).isEmpty(), "et l'intention est consommée");

        monde.ajouter(c5, new Monde.VeutSeDeplacer(new Monde.Case(2, 3)));     // c4 : libre
        systeme.mettreAJour(monde);
        assertEquals(new Monde.Case(2, 3), monde.composant(c5, Monde.Case.class).orElseThrow(), "légal : appliqué");
        assertTrue(monde.composant(c5, Monde.VeutSeDeplacer.class).isEmpty(), "l'intention est consommée");
    }
}
