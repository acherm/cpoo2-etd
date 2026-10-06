package cpoo2.s05.entrainement;

import cpoo2.jeu.Coup;
import cpoo2.jeu.Partie;

/**
 * S05, exercice 2 : le <b>gardien</b> du Memento, pour le mode entraînement. Il joue les coups en
 * gardant, avant chacun, une sauvegarde de la partie, et sait revenir en arrière. Il détient les
 * sauvegardes sans pouvoir les ouvrir.
 */
public final class Historique {

    private final Partie partie;
    // TODO S05 Q9 : les sauvegardes, la plus récente en premier

    public Historique(Partie partie) {
        this.partie = partie;
    }

    /** Joue le coup, après avoir sauvegardé la partie. Vrai si le coup est accepté. */
    public boolean jouer(Coup coup) {
        throw new UnsupportedOperationException("TODO S05 Q9");
    }

    /** Revient un coup en arrière. Faux s'il n'y a rien à annuler. */
    public boolean annuler() {
        throw new UnsupportedOperationException("TODO S05 Q9");
    }

    /** Combien de coups on peut encore annuler. */
    public int profondeur() {
        throw new UnsupportedOperationException("TODO S05 Q9");
    }
}
