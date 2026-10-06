package cpoo2.s03.ia;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.Direction;
import cpoo2.jeu.Jeu;
import cpoo2.jeu.Plateau;
import cpoo2.jeu.Position;

/**
 * Une IA qui ne voit qu'un coup devant elle. Si une poussée rapproche le ballon de la ligne où son
 * équipe marque, elle la joue. Sinon, elle rapproche une pièce de la <b>case de poussée</b>, la
 * case derrière le ballon (pour les Bleus, qui marquent au sud, celle au nord du ballon).
 *
 * <p>C'est le <b>client</b> de {@link Distances} : elle les reçoit à la construction et ne sait
 * pas comment on les calcule. Changer de calcul ne change pas une ligne ici.</p>
 */
public final class IAGloutonne implements IA {

    private final Distances distances;

    public IAGloutonne(Distances distances) {
        this.distances = distances;
    }

    @Override public Coup choisirCoup(Jeu jeu) {
        List<Coup> coups = jeu.coupsLegaux();
        if (coups.isEmpty()) {
            throw new IllegalStateException("aucun coup légal");
        }
        Couleur trait = jeu.trait();
        Plateau plateau = jeu.plateau();

        int ecartDuBallon = ecart(plateau.ballon(), trait);
        for (Coup coup : coups) {
            if (coup instanceof Coup.Poussee p
                    && p.arriveeDuBallon().map(q2 -> ecart(q2, trait) < ecartDuBallon).orElse(false)) {
                return coup;
            }
        }

        Optional<Position> cible = caseDePoussee(plateau, trait);
        if (cible.isEmpty()) {
            return coups.get(0);
        }
        Coup meilleur = coups.get(0);
        int record = Integer.MAX_VALUE;
        for (Coup coup : coups) {
            if (coup instanceof Coup.Deplacement d) {
                OptionalInt n = distances.pas(plateau, d.arrivee(), cible.get());
                if (n.isPresent() && n.getAsInt() < record) {
                    record = n.getAsInt();
                    meilleur = coup;
                }
            }
        }
        return meilleur;
    }

    /** Combien de rangées séparent cette case de la ligne où l'équipe marque. */
    private static int ecart(Position p, Couleur equipe) {
        return Math.abs(p.rangee() - equipe.rangeeOuElleMarque());
    }

    private static Optional<Position> caseDePoussee(Plateau plateau, Couleur trait) {
        Direction derriere = trait == Couleur.BLEUS ? Direction.NORD : Direction.SUD;
        return plateau.ballon().voisine(derriere).filter(plateau::estLibre);
    }
}
