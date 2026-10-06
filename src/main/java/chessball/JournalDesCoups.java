package chessball;

import java.util.ArrayList;
import java.util.List;

/** Un abonné de la partie (fourni : l'Observateur s'écrit en S04 sur le jeu de la boîte) : les coups acceptés, dans la notation du journal. */
public final class JournalDesCoups implements EcouteurDePartie {

    private final List<String> lignes = new ArrayList<>();

    @Override public void surCoupTente(Coup coup, Verdict verdict) {
        if (verdict.accepte()) lignes.add(coup.toString());
    }

    public List<String> lignes() { return List.copyOf(lignes); }
}
