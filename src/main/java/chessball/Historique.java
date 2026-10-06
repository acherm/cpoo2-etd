package chessball;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Le gardien : il fait jouer la partie, garde une sauvegarde avant chaque coup accepté, et sait
 * revenir en arrière. Il détient des {@link Partie.Sauvegarde} qu'il ne peut pas lire : c'est
 * le Memento. Il tient aussi le carnet des coups joués par lui : c'est l'invocateur de la Commande.
 */
public final class Historique {

    private final Partie partie;
    private final Deque<Partie.Sauvegarde> avant = new ArrayDeque<>();
    private final List<Coup> carnet = new ArrayList<>();

    public Historique(Partie partie) {
        this.partie = partie;
    }

    /** Sauvegarde, puis joue. Un coup refusé ne laisse rien derrière lui. */
    public Verdict jouer(Coup coup) {
        Partie.Sauvegarde sauvegarde = partie.capturer();
        Verdict verdict = partie.jouer(coup);
        if (verdict.accepte()) {
            avant.push(sauvegarde);
            carnet.add(coup);
        }
        return verdict;
    }

    /** Revient à l'état d'avant le dernier coup joué par cet historique. Faux s'il n'y en a pas. */
    public boolean annuler() {
        if (avant.isEmpty()) return false;
        partie.restaurer(avant.pop());
        carnet.remove(carnet.size() - 1);
        return true;
    }

    /** Les coups joués par cet historique, dans l'ordre : un carnet rejouable. */
    public List<Coup> carnet() {
        return Collections.unmodifiableList(carnet);
    }

    public Partie partie() { return partie; }
}
