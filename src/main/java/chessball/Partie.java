package chessball;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Le match : l'état du jeu et l'arbitrage d'un coup. Version de départ, que les séances font
 * grandir : les abonnés (séance 4), les coups qui s'exécutent, l'annulation par sauvegarde et
 * les règles composées (séance 5), la configuration d'une partie (séance 7).
 *
 * <p>Classe volontairement <b>non finale</b> et à vous : la couche jeu s'étend, le noyau
 * ({@link Plateau}, {@link Piece}, {@link Position}) ne se touche pas.</p>
 */
public class Partie implements Jeu {

    private final Plateau plateau;
    private final Regle regle;
    private final List<Coup> journal = new ArrayList<>();
    private Map<Couleur, Integer> score = new EnumMap<>(Couleur.class);
    private Couleur trait;

    public Partie(Plateau plateau, Regle regle, Couleur premierTrait) {
        this.plateau = plateau;
        this.regle = regle;
        this.trait = premierTrait;
        score.put(Couleur.BLEUS, 0);
        score.put(Couleur.ROUGES, 0);
    }

    /** Le placement standard, le jeu de base, les Bleus engagent. */
    public static Partie standard() {
        return new Partie(Placements.standard(), Regles.duJeuDeBase(), Couleur.BLEUS);
    }

    // ---------- jouer ----------

    /** L'arbitrage seul : le verdict qu'aurait ce coup, sans rien changer. */
    public Verdict arbitrer(Coup coup) {
        return regle.verifier(this, coup);
    }

    @Override public Verdict jouer(Coup coup) {
        Verdict verdict = regle.verifier(this, coup);
        if (verdict.accepte()) {
            appliquer(coup);
            journal.add(coup);
            if (!(coup instanceof Coup.PoserBallon)) {   // J3 : poser le ballon et jouer, un seul tour
                trait = trait.adverse();
            }
        }
        // TODO S04, Q14 : prévenir les abonnés du coup tenté, avec son verdict
        return verdict;
    }

    /**
     * Rejoue un carnet, coup par coup, à travers l'arbitrage : un match se reconstruit depuis
     * ses coups. TODO S05, Q4 bis.
     */
    public void rejouer(List<Coup> carnet) {
        throw new UnsupportedOperationException("TODO S05 Q4 bis");
    }

    /**
     * L'effet du coup, une fois l'arbitrage rendu. Ici on ne vérifie plus rien.
     * TODO S05, Q4 bis : ce {@code switch} disparaît, chaque coup sait s'exécuter
     * ({@code coup.executer(this)}) et la partie ne connaît plus les sortes de coups.
     */
    private void appliquer(Coup coup) {
        switch (coup) {
            case Coup.Deplacement d -> plateau.deplacer(d.de(), d.vers());
            case Coup.Passe p -> plateau.donnerBallon(plateau.pieceEn(p.cible()).orElseThrow());
            case Coup.Tir t -> tirer(t);
            case Coup.PoserBallon p -> plateau.poserBallonLibre(p.ou());
            case Coup.Macro m -> throw new UnsupportedOperationException("TODO S05 Q5 ter : " + m);
        }
    }

    private void tirer(Coup.Tir t) {
        Couleur tireur = plateau.porteuse().orElseThrow().couleur();
        Position courante = t.porteuse();
        while (true) {
            Position suivante = courante.decalee(t.direction().dColonne(), t.direction().dRangee());
            if (!plateau.contient(suivante)) {
                boolean franchitLaRangeeDeFond = tireur == Couleur.BLEUS
                        ? suivante.rangee() >= plateau.taille()
                        : suivante.rangee() < 0;
                if (franchitLaRangeeDeFond) {
                    plateau.retirerLeBallon();          // le ballon sort du terrain : on réengagera
                    but(tireur);
                } else {
                    plateau.poserBallonLibre(courante);  // sortie latérale (J8)
                }
                return;
            }
            Piece rencontree = plateau.pieceEn(suivante).orElse(null);
            if (rencontree != null) {
                plateau.donnerBallon(rencontree);       // adverse : interception, amie : réception
                return;
            }
            courante = suivante;
        }
    }

    /** Appelé par le tir victorieux. */
    protected void but(Couleur marqueur) {
        score.merge(marqueur, 1, Integer::sum);
        // TODO S04, Q15 : prévenir les abonnés du but
    }

    // ---------- consulter ----------

    public Plateau plateau() { return plateau; }

    @Override public Couleur trait() { return trait; }

    @Override public int score(Couleur couleur) { return score.getOrDefault(couleur, 0); }

    @Override public List<Coup> journal() { return Collections.unmodifiableList(journal); }

    @Override public String plateauEnTexte() { return plateau.enTexte(); }

    // ---------- abonnés (séance 4, exercice 5) ----------

    /** TODO S04, Q14 : la liste des abonnés, typée par l'interface. */
    public void ajouterEcouteur(EcouteurDePartie ecouteur) {
        throw new UnsupportedOperationException("TODO S04 Q14");
    }

    /** TODO S04, Q14. */
    public void retirerEcouteur(EcouteurDePartie ecouteur) {
        throw new UnsupportedOperationException("TODO S04 Q14");
    }

    // ---------- sauvegarde (séance 5, Q5 bis) : le Memento ----------

    /**
     * L'état de la partie, figé. TODO S05, Q5 bis : les champs privés (une copie du plateau,
     * le trait, le score, la longueur du journal), un constructeur privé, <b>aucun accesseur</b> :
     * personne d'autre que {@link Partie} ne lit l'intérieur, tout le monde peut le détenir.
     */
    public static final class Sauvegarde {
        private Sauvegarde() {}
    }

    /** Le créateur produit la sauvegarde. TODO S05, Q5 bis. */
    public Sauvegarde capturer() {
        throw new UnsupportedOperationException("TODO S05 Q5 bis");
    }

    /** Et lui seul sait la relire. TODO S05, Q5 bis. */
    public void restaurer(Sauvegarde sauvegarde) {
        throw new UnsupportedOperationException("TODO S05 Q5 bis");
    }
}
