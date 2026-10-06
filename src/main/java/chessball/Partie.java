package chessball;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Le match de l'ancienne variante (pièces d'échecs), fourni complet : les séances le remplacent
 * une à une par le ChessBall de la boîte ({@code cpoo2.jeu.Partie}). Il sert encore aux exercices
 * de S06 à S09 qui n'ont pas été migrés.
 */
public class Partie implements Jeu {

    private final Plateau plateau;
    private final Regle regle;
    private final List<Coup> journal = new ArrayList<>();
    private final List<EcouteurDePartie> ecouteurs = new ArrayList<>();
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
            coup.executer(this);                   
            journal.add(coup);
            if (!(coup instanceof Coup.PoserBallon)) {   // J3 : poser le ballon et jouer, un seul tour
                trait = trait.adverse();
            }
        }
        diffuser(e -> e.surCoupTente(coup, verdict));
        return verdict;
    }

    /** Rejoue un carnet, coup par coup, à travers l'arbitrage : un match se reconstruit depuis ses coups. */
    public void rejouer(List<Coup> carnet) {
        for (Coup coup : carnet) {
            Verdict v = jouer(coup);
            if (!v.accepte()) throw new IllegalStateException("carnet invalide au coup " + coup + " : " + v.motif());
        }
    }

    /** Appelé par le tir victorieux. */
    protected void but(Couleur marqueur) {
        score.merge(marqueur, 1, Integer::sum);
        diffuser(e -> e.surBut(marqueur));
    }

    // ---------- consulter ----------

    public Plateau plateau() { return plateau; }

    @Override public Couleur trait() { return trait; }

    @Override public int score(Couleur couleur) { return score.getOrDefault(couleur, 0); }

    @Override public List<Coup> journal() { return Collections.unmodifiableList(journal); }

    @Override public String plateauEnTexte() { return plateau.enTexte(); }

    // ---------- abonnés (séance 3, exercice 5) ----------

    public void ajouterEcouteur(EcouteurDePartie ecouteur) { ecouteurs.add(ecouteur); }

    public void retirerEcouteur(EcouteurDePartie ecouteur) { ecouteurs.remove(ecouteur); }

    private void diffuser(Consumer<EcouteurDePartie> evenement) {
        for (EcouteurDePartie e : List.copyOf(ecouteurs)) {   // copie : un abonné peut se désabonner
            evenement.accept(e);
        }
    }

    // ---------- sauvegarde (séance 4, Q5 bis) : le Memento ----------

    /**
     * L'état de la partie, figé. Personne d'autre que {@link Partie} ne peut lire l'intérieur :
     * pas d'accesseur, constructeur privé. Le gardien peut le détenir sans pouvoir l'ouvrir.
     */
    public static final class Sauvegarde {
        private final Plateau plateau;
        private final Couleur trait;
        private final Map<Couleur, Integer> score;
        private final int tailleDuJournal;

        private Sauvegarde(Partie p) {
            this.plateau = p.plateau.copie();
            this.trait = p.trait;
            this.score = new EnumMap<>(p.score);
            this.tailleDuJournal = p.journal.size();
        }
    }

    /** Le créateur produit la sauvegarde. */
    public Sauvegarde capturer() { return new Sauvegarde(this); }

    /** Et lui seul sait la relire. */
    public void restaurer(Sauvegarde sauvegarde) {
        this.plateau.restaurerDepuis(sauvegarde.plateau);
        this.trait = sauvegarde.trait;
        this.score = new EnumMap<>(sauvegarde.score);
        while (journal.size() > sauvegarde.tailleDuJournal) journal.remove(journal.size() - 1);
    }
}
