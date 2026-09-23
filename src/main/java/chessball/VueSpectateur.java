package chessball;

import java.util.List;

/**
 * S03, exercice 3 : le <b>Proxy</b> (de protection). Un spectateur reçoit exactement la même
 * interface qu'un joueur, {@link Jeu}, et ne doit pas pouvoir jouer. La partie réelle ne doit
 * pas savoir qu'elle est vue à travers un proxy, ni le client.
 *
 * <p>TODO Q7 : détenir la partie (typée par l'interface, jamais par {@link Partie}), refuser
 * {@code jouer} sans la consulter, déléguer le reste.</p>
 */
public final class VueSpectateur implements Jeu {

    public VueSpectateur(Jeu partie) {
        // TODO Q7
    }

    @Override public Verdict jouer(Coup coup) {
        throw new UnsupportedOperationException("TODO Q7");
    }

    @Override public Couleur trait() {
        throw new UnsupportedOperationException("TODO Q7");
    }

    @Override public int score(Couleur couleur) {
        throw new UnsupportedOperationException("TODO Q7");
    }

    @Override public List<Coup> journal() {
        throw new UnsupportedOperationException("TODO Q7");
    }

    @Override public String plateauEnTexte() {
        throw new UnsupportedOperationException("TODO Q7");
    }
}
