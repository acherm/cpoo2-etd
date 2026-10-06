package cpoo2.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import arbitre.Arbitre;

/**
 * Une vraie partie de ChessBall : elle arbitre, applique, et sait quand elle est finie. Les coups
 * possibles et leur effet viennent de l'arbitre, livré compilé ({@code arbitre.Arbitre}, dans
 * {@code lib/}) : il ne connaît qu'une position, jamais l'histoire de la partie. Ce qui dépend de
 * l'histoire est ici :
 *
 * <ul>
 * <li>les <b>représailles</b> : juste après un tacle, la victime ne peut ni tacler son tacleur ni
 * sauter par-dessus lui ;</li>
 * <li>la <b>fin</b> : un but (le ballon sur une ligne de but), ou une partie nulle, quand
 * l'équipe au trait n'a aucun coup, ou après {@value #DEMI_COUPS_SANS_POUSSEE_MAX} demi-coups
 * sans poussée du ballon.</li>
 * </ul>
 *
 * <p>Classe à vous, qui grandit : la séance 4 y branche les abonnés (l'Observateur), la séance 5
 * remplace ses champs et ses {@code if} par une machine à états (le patron État, {@link EtatPartie})
 * et lui apprend à se sauvegarder (le Memento, {@link Sauvegarde}).</p>
 */
public class Partie implements Jeu {

    public static final int DEMI_COUPS_SANS_POUSSEE_MAX = 80;

    private Plateau plateau;
    private Couleur trait;
    private Position tacleur;        // non nuls juste après un tacle
    private Position victime;
    private boolean terminee;
    private Couleur vainqueur;       // null tant que personne n'a marqué
    private int demiCoupsSansPoussee;

    public Partie(Plateau plateau, Couleur trait) {
        this.plateau = plateau;
        this.trait = trait;
    }

    /** La position de départ de la boîte, les Bleus au trait. */
    public static Partie officielle() {
        return new Partie(Plateau.officiel(), Couleur.BLEUS);
    }

    @Override public Couleur trait() { return trait; }

    @Override public Plateau plateau() { return plateau; }

    public boolean estTerminee() { return terminee; }

    /** Les demi-coups joués depuis la dernière poussée du ballon. */
    public int demiCoupsSansPoussee() { return demiCoupsSansPoussee; }

    /** L'état de la partie : en jeu, juste après un tacle, gagnée, nulle. TODO S05 Q5. */
    public EtatPartie etat() {
        throw new UnsupportedOperationException("TODO S05 Q5");
    }

    /** Les coups que permettent les règles dans cette position, sans tenir compte de l'histoire. */
    public List<Coup> coupsPossibles() {
        return Arbitre.coupsPossibles(plateau.enLigne(), trait.name()).stream().map(Coup::lire).toList();
    }

    /** Le vainqueur, ou vide si la partie est en cours ou nulle. */
    public Optional<Couleur> vainqueur() { return Optional.ofNullable(vainqueur); }

    @Override public List<Coup> coupsLegaux() {
        if (terminee) {
            return List.of();
        }
        List<Coup> coups = new ArrayList<>();
        for (Coup coup : coupsPossibles()) {
            if (!represailles(coup)) {
                coups.add(coup);
            }
        }
        return List.copyOf(coups);
    }

    @Override public boolean jouer(Coup coup) {
        if (!coupsLegaux().contains(coup)) {
            return false;
        }
        Couleur joueur = trait;
        plateau = Plateau.depuisLigne(Arbitre.apres(plateau.enLigne(), coup.toString()));
        if (coup instanceof Coup.Tacle) {
            tacleur = coup.arrivee();
            victime = coup.arrivee().voisine(coup.direction()).orElseThrow();
        } else {
            tacleur = null;
            victime = null;
        }
        demiCoupsSansPoussee = coup instanceof Coup.Poussee ? 0 : demiCoupsSansPoussee + 1;
        trait = trait.adverse();
        int rangeeDuBallon = plateau.ballon().rangee();
        if (rangeeDuBallon == Couleur.BLEUS.rangeeOuElleMarque()) {
            terminee = true;
            vainqueur = Couleur.BLEUS;
        } else if (rangeeDuBallon == Couleur.ROUGES.rangeeOuElleMarque()) {
            terminee = true;
            vainqueur = Couleur.ROUGES;
        } else if (demiCoupsSansPoussee >= DEMI_COUPS_SANS_POUSSEE_MAX || coupsLegaux().isEmpty()) {
            terminee = true;
        }
        // TODO S04 Q15 : prévenir les abonnés du coup joué, puis de la fin de partie s'il y a lieu
        return true;
    }

    /** Juste après un tacle, la victime ne peut ni tacler son tacleur, ni sauter par-dessus lui. */
    private boolean represailles(Coup coup) {
        return victime != null
                && coup.origine().equals(victime)
                && (coup instanceof Coup.Tacle || coup instanceof Coup.Saut)
                && coup.origine().voisine(coup.direction()).orElseThrow().equals(tacleur);
    }

    // ---------- les abonnés (S04, exercice 5 : l'Observateur) ----------

    /** TODO S04 Q15 : la liste des abonnés, typée par l'interface. */
    public void ajouterEcouteur(EcouteurDePartie ecouteur) {
        throw new UnsupportedOperationException("TODO S04 Q15");
    }

    /** TODO S04 Q15. */
    public void retirerEcouteur(EcouteurDePartie ecouteur) {
        throw new UnsupportedOperationException("TODO S04 Q15");
    }

    // ---------- la sauvegarde (S05, exercice 2 : le Memento) ----------

    /**
     * La partie, figée à un instant. TODO S05 Q8 : opaque, constructeur privé, aucun accesseur.
     * Seule la {@link Partie} sait la lire, n'importe qui peut la garder.
     */
    public static final class Sauvegarde {
        private Sauvegarde() {}
    }

    /** TODO S05 Q8. */
    public Sauvegarde capturer() {
        throw new UnsupportedOperationException("TODO S05 Q8");
    }

    /** TODO S05 Q8. */
    public void restaurer(Sauvegarde sauvegarde) {
        throw new UnsupportedOperationException("TODO S05 Q8");
    }
}
