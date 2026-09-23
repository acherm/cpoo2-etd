package chessball;

import java.util.List;

/**
 * S04, exercice 5, Q15 : un abonné qui note les coups <b>acceptés</b>, dans la notation
 * du journal ({@code e2-d4}), et ignore les refus.
 *
 * <p>TODO Q15.</p>
 */
public final class JournalDesCoups implements EcouteurDePartie {

    @Override public void surCoupTente(Coup coup, Verdict verdict) {
        throw new UnsupportedOperationException("TODO Q15");
    }

    public List<String> lignes() {
        throw new UnsupportedOperationException("TODO Q15");
    }
}
