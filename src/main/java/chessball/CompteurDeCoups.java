package chessball;

/** Un abonné de la partie (fourni : l'Observateur s'écrit en S04 sur le jeu de la boîte) : acceptés, refusés et buts, comptés à part. */
public final class CompteurDeCoups implements EcouteurDePartie {

    private int acceptes;
    private int refuses;
    private int buts;

    @Override public void surCoupTente(Coup coup, Verdict verdict) {
        if (verdict.accepte()) acceptes++; else refuses++;
    }

    @Override public void surBut(Couleur marqueur) { buts++; }

    public int acceptes() { return acceptes; }

    public int refuses() { return refuses; }

    public int buts() { return buts; }
}
