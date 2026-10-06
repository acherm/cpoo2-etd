package cpoo2.s04.observateur;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import cpoo2.jeu.Coup;
import cpoo2.jeu.Partie;
import cpoo2.jeu.Plateau;
import cpoo2.jeu.Couleur;

/** S04, exercice 5 : l'<b>Observateur</b>. Tous rouges sur le squelette. */
class ObservateurTest {

    static Coup c(String s) { return Coup.lire(s); }

    @Test
    void deuxAbonnesSontPrevenusDuMemeCoup() {
        Partie p = Partie.officielle();
        JournalDesCoups journal = new JournalDesCoups();
        CompteurDeCoups compteur = new CompteurDeCoups();
        p.ajouterEcouteur(journal);
        p.ajouterEcouteur(compteur);
        p.jouer(c("poussée c5-d4"));
        p.jouer(c("déplacement c2-c3"));
        assertEquals(List.of("BLEUS : poussée c5-d4", "ROUGES : déplacement c2-c3"), journal.lignes());
        assertEquals(2, compteur.coups());
        assertEquals(1, compteur.poussees());
    }

    @Test
    void unCoupRefuseNEstPasAnnonce() {
        Partie p = Partie.officielle();
        JournalDesCoups journal = new JournalDesCoups();
        p.ajouterEcouteur(journal);
        p.jouer(c("déplacement c2-c3"));
        assertEquals(List.of(), journal.lignes(), "ce sont les Bleus qui jouent : rien ne s'est passé");
    }

    @Test
    void unAbonneRetireNeRecoitPlusRien() {
        Partie p = Partie.officielle();
        CompteurDeCoups compteur = new CompteurDeCoups();
        p.ajouterEcouteur(compteur);
        p.jouer(c("poussée c5-d4"));
        p.retirerEcouteur(compteur);
        p.jouer(c("déplacement c2-c3"));
        assertEquals(1, compteur.coups());
    }

    @Test
    void laFinEstUnSecondEvenement() {
        Partie p = new Partie(Plateau.depuis(
                ".D.D...",
                "..A.A..",
                ".......",
                "....D..",
                "..a.ao.",
                ".d.d.d."), Couleur.BLEUS);
        JournalDesCoups journal = new JournalDesCoups();
        p.ajouterEcouteur(journal);
        p.jouer(c("poussée e3-f2"));
        assertEquals(List.of("BLEUS : poussée e3-f2", "fin : vainqueur BLEUS"), journal.lignes());
    }

    @Test
    void uneLambdaEstUnAbonneCommeUnAutre() {
        Partie p = Partie.officielle();
        List<Coup> vus = new ArrayList<>();
        p.ajouterEcouteur((equipe, coup) -> vus.add(coup));
        p.jouer(c("poussée c5-d4"));
        assertEquals(List.of(c("poussée c5-d4")), vus);
    }
}
