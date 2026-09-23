package chessball;

import java.util.List;

/**
 * S03, exercice 2 : le <b>Décorateur</b>. Un jeu qui en enveloppe un autre, rend exactement
 * ses verdicts, et <b>ajoute</b> une ligne de journal à chaque coup tenté. Même interface que
 * ce qu'il enveloppe : il se glisse partout où un {@link Jeu} est attendu, et deux traces
 * s'empilent.
 *
 * <p>TODO Q4 : déléguer, puis ajouter. Ni plus, ni moins.</p>
 */
public final class JeuTrace implements Jeu {

    private final Jeu enveloppe;
    private final Journal journal;

    public JeuTrace(Jeu enveloppe, Journal journal) {
        this.enveloppe = enveloppe;
        this.journal = journal;
    }

    @Override public Verdict jouer(Coup coup) {
        throw new UnsupportedOperationException("TODO Q4");
    }

    @Override public Couleur trait() {
        throw new UnsupportedOperationException("TODO Q4");
    }

    @Override public int score(Couleur couleur) {
        throw new UnsupportedOperationException("TODO Q4");
    }

    @Override public List<Coup> journal() {
        throw new UnsupportedOperationException("TODO Q4");
    }

    @Override public String plateauEnTexte() {
        throw new UnsupportedOperationException("TODO Q4");
    }
}
