package cpoo2.s04.observateur;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.EcouteurDePartie;

/** S04, Q16 : un abonné qui compte les coups joués, et parmi eux les poussées. */
public final class CompteurDeCoups implements EcouteurDePartie {

    private int coups;
    private int poussees;

    public int coups() { return coups; }

    public int poussees() { return poussees; }

    @Override public void coupJoue(Couleur equipe, Coup coup) {
        throw new UnsupportedOperationException("TODO S04 Q16");
    }
}
