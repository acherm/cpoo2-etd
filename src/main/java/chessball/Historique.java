package chessball;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Le gardien : il fait jouer la partie, garde une sauvegarde avant chaque coup accepté, et sait
 * revenir en arrière. Il détient des {@link Partie.Sauvegarde} qu'il ne peut pas lire : c'est
 * le Memento. Il tient aussi le carnet des coups joués par lui : l'invocateur de la Commande.
 *
 * <p>TODO S05, Q5 bis.</p>
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
        throw new UnsupportedOperationException("TODO S05 Q5 bis");
    }

    /** Revient à l'état d'avant le dernier coup joué par cet historique. Faux s'il n'y en a pas. */
    public boolean annuler() {
        throw new UnsupportedOperationException("TODO S05 Q5 bis");
    }

    /** Les coups joués par cet historique, dans l'ordre : un carnet rejouable. */
    public List<Coup> carnet() {
        return Collections.unmodifiableList(carnet);
    }

    public Partie partie() { return partie; }
}
