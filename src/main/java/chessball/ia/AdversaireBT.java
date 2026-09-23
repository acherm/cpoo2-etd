package chessball.ia;

import chessball.Coup;
import chessball.Couleur;
import chessball.Direction;
import chessball.Motifs;
import chessball.Partie;
import chessball.Piece;
import chessball.Plateau;
import chessball.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * L'attaquant : un behaviour tree qui choisit un coup pour le camp au trait.
 *
 * <pre>
 * Selecteur
 *   Sequence( ballonProcheBut, tirer )
 *   Sequence( peutPasser, passerAuMieux )
 *   avancerVersBallon
 * </pre>
 *
 * <p>TODO S05, Q12 : les deux conditions et les trois actions. Les outils du bas (le tir gagnant,
 * les coups légaux, la progression, la distance) sont fournis. Les conditions ne modifient rien. Les actions déposent le coup choisi sur le tableau noir
 * et répondent {@code SUCCES}, ou {@code ECHEC} si aucun coup de ce genre n'est légal : le
 * Selecteur passe alors à la branche suivante. Le jeu est au tour par tour, {@code EN_COURS}
 * ne sert pas ici.</p>
 */
public final class AdversaireBT {

    static final String COUP = "coup";

    private final Noeud arbre;

    public AdversaireBT() {
        this.arbre = new Noeud.Selecteur(
                new Noeud.Sequence(new Noeud.Condition("ballonProcheBut", AdversaireBT::ballonProcheBut),
                        new Noeud.Action("tirer", AdversaireBT::tirer)),
                new Noeud.Sequence(new Noeud.Condition("peutPasser", AdversaireBT::peutPasser),
                        new Noeud.Action("passerAuMieux", AdversaireBT::passerAuMieux)),
                new Noeud.Action("avancerVersBallon", AdversaireBT::avancerVersBallon));
    }

    /** L'arbre, pour l'afficher ou le tester. */
    public Noeud arbre() { return arbre; }

    /** Le coup choisi pour le camp au trait. */
    public Coup choisir(Partie partie) {
        Contexte ctx = new Contexte(partie, partie.trait());
        Statut statut = arbre.tick(ctx);
        Coup coup = ctx.lire(COUP, Coup.class);
        if (statut != Statut.SUCCES || coup == null) {
            throw new IllegalStateException("aucun coup légal pour " + partie.trait());
        }
        return coup;
    }

    // ---------- les feuilles ----------

    /** Ma porteuse a un tir légal dont la trajectoire est libre jusqu'à la ligne de fond. */
    static boolean ballonProcheBut(Contexte ctx) {
        throw new UnsupportedOperationException("TODO S05 Q12 : ballonProcheBut");
    }

    static Statut tirer(Contexte ctx) {
        throw new UnsupportedOperationException("TODO S05 Q12 : tirer");
    }

    static boolean peutPasser(Contexte ctx) {
        throw new UnsupportedOperationException("TODO S05 Q12 : peutPasser");
    }

    /** La passe qui rapproche le plus le ballon de la ligne de fond adverse. */
    static Statut passerAuMieux(Contexte ctx) {
        throw new UnsupportedOperationException("TODO S05 Q12 : passerAuMieux");
    }

    /** Le déplacement qui rapproche le plus une de mes pièces du ballon. */
    static Statut avancerVersBallon(Contexte ctx) {
        throw new UnsupportedOperationException("TODO S05 Q12 : avancerVersBallon");
    }

    // ---------- outils ----------

    private static Statut deposer(Contexte ctx, Optional<Coup> coup) {
        if (coup.isEmpty()) return Statut.ECHEC;
        ctx.noter(COUP, coup.get());
        return Statut.SUCCES;
    }

    private static Optional<Coup> tirGagnant(Contexte ctx) {
        Partie partie = ctx.partie();
        Plateau plateau = partie.plateau();
        Piece porteuse = plateau.porteuse().orElse(null);
        if (porteuse == null || porteuse.couleur() != ctx.camp()) return Optional.empty();
        Position depart = plateau.positionDe(porteuse).orElseThrow();
        for (Direction d : Motifs.de(porteuse).directions()) {
            Coup tir = new Coup.Tir(depart, d);
            if (partie.arbitrer(tir).accepte() && trajectoireLibreJusquAuFond(plateau, depart, d, ctx.camp())) {
                return Optional.of(tir);
            }
        }
        return Optional.empty();
    }

    private static boolean trajectoireLibreJusquAuFond(Plateau plateau, Position depart, Direction d, Couleur camp) {
        Position c = depart.decalee(d.dColonne(), d.dRangee());
        while (plateau.contient(c)) {
            if (!plateau.estVide(c)) return false;
            c = c.decalee(d.dColonne(), d.dRangee());
        }
        return camp == Couleur.BLEUS ? c.rangee() >= plateau.taille() : c.rangee() < 0;
    }

    /** Tous les coups que l'arbitre accepterait pour le camp au trait : déplacements, passes, tirs. */
    public static List<Coup> coupsLegaux(Partie partie) {
        Plateau plateau = partie.plateau();
        Couleur camp = partie.trait();
        List<Coup> candidats = new ArrayList<>();
        for (Map.Entry<Position, Piece> e : plateau.occupation().entrySet()) {
            Piece piece = e.getValue();
            if (piece.couleur() != camp) continue;
            Position depart = e.getKey();
            for (Position vers : Motifs.de(piece).accessibles(plateau, depart)) {
                candidats.add(new Coup.Deplacement(depart, vers));
            }
            if (plateau.porteuse().orElse(null) == piece) {
                for (Piece coequipiere : plateau.piecesDe(camp)) {
                    if (coequipiere != piece) {
                        candidats.add(new Coup.Passe(depart, plateau.positionDe(coequipiere).orElseThrow()));
                    }
                }
                for (Direction d : Motifs.de(piece).directions()) candidats.add(new Coup.Tir(depart, d));
            }
        }
        List<Coup> legaux = new ArrayList<>();
        for (Coup c : candidats) if (partie.arbitrer(c).accepte()) legaux.add(c);
        return legaux;
    }

    static int progression(Couleur camp, Plateau plateau, Position p) {
        return camp == Couleur.BLEUS ? p.rangee() : plateau.taille() - 1 - p.rangee();
    }

    static int distance(Position a, Position b) {
        return Math.max(Math.abs(a.colonne() - b.colonne()), Math.abs(a.rangee() - b.rangee()));
    }
}
