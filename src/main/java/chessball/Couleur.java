package chessball;

/** Les deux camps. Les Bleus occupent les rangées 1-2 et attaquent la rangée 8 (J9). */
public enum Couleur {
    BLEUS, ROUGES;

    public Couleur adverse() { return this == BLEUS ? ROUGES : BLEUS; }

    /** Rangée de fond visée par cette équipe sur un plateau 8×8 — signature du moteur de CPOO1. */
    public int rangeeAdverse() { return rangeeAdverse(8); }

    /** La même, sur un plateau de {@code cote} rangées (variante 10×10 du feature model). */
    public int rangeeAdverse(int cote) { return this == BLEUS ? cote - 1 : 0; }
}
