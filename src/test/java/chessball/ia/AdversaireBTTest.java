package chessball.ia;

import chessball.Coup;
import chessball.Couleur;
import chessball.Direction;
import chessball.Partie;
import chessball.Piece;
import chessball.Plateau;
import chessball.Position;
import chessball.Regles;
import chessball.TypePiece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** S05, Q12 : l'oracle de l'attaquant. Il tire quand la voie est libre, passe sinon, et ne joue jamais un coup illégal. */
class AdversaireBTTest {

    @Test
    void ilTireQuandLaTrajectoireEstLibreJusquAuFond() {
        Plateau plateau = new Plateau(8);
        Piece tour = new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT");
        plateau.placer(tour, Position.of("d4"));
        plateau.donnerBallon(tour);
        plateau.placer(new Piece(Couleur.ROUGES, TypePiece.FOU, "rF"), Position.of("a8"));
        Partie partie = new Partie(plateau, Regles.duJeuDeBase(), Couleur.BLEUS);
        Coup choisi = new AdversaireBT().choisir(partie);
        assertEquals(new Coup.Tir(Position.of("d4"), Direction.NORD), choisi);
        assertTrue(partie.jouer(choisi).accepte());
        assertEquals(1, partie.score(Couleur.BLEUS));
    }

    @Test
    void ilPassePlutotQueDAvancerQuandUneCoequipiereEstAlignee() {
        Plateau plateau = new Plateau(8);
        Piece tour = new Piece(Couleur.BLEUS, TypePiece.TOUR, "bT");
        Piece fou = new Piece(Couleur.BLEUS, TypePiece.FOU, "bF");
        plateau.placer(tour, Position.of("d2"));
        plateau.placer(fou, Position.of("d5"));
        plateau.placer(new Piece(Couleur.ROUGES, TypePiece.TOUR, "rT"), Position.of("d7"));   // bloque le tir de la tour
        plateau.donnerBallon(tour);
        Partie partie = new Partie(plateau, Regles.duJeuDeBase(), Couleur.BLEUS);
        Coup choisi = new AdversaireBT().choisir(partie);
        assertEquals(new Coup.Passe(Position.of("d2"), Position.of("d5")), choisi);
    }

    @Test
    void surLaPositionStandardIlJoueToujoursUnCoupLegal() {
        Partie partie = Partie.standard();
        assertTrue(partie.jouer(new Coup.PoserBallon(Position.of("d4"))).accepte());
        AdversaireBT ia = new AdversaireBT();
        for (int tour = 0; tour < 10; tour++) {
            Coup choisi = ia.choisir(partie);
            assertTrue(partie.arbitrer(choisi).accepte(), "tour " + tour + " : " + choisi);
            partie.jouer(choisi);
        }
        assertEquals(10, partie.journal().size() - 1);
    }
}
