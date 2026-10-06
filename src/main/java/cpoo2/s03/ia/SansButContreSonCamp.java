package cpoo2.s03.ia;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;

/**
 * S03, exercice 2 : un <b>Décorateur</b> d'IA. Il enveloppe n'importe quelle {@link IA}, lui
 * demande son coup, et si ce coup est un but contre son camp, le remplace par le premier coup
 * légal (dans l'ordre de {@code coupsLegaux()}) qui n'en est pas un. S'il n'y en a aucun, il
 * garde le coup proposé.
 */
public final class SansButContreSonCamp implements IA {

    // TODO S03 Q9 : l'IA enveloppée, dans un champ typé par l'interface

    public SansButContreSonCamp(IA enveloppee) {
        // TODO S03 Q9
    }

    @Override public Coup choisirCoup(Jeu jeu) {
        // TODO S03 Q9 : déléguer, puis ajouter le garde-fou
        throw new UnsupportedOperationException("TODO S03 Q9");
    }

    /** Vrai si ce coup, joué par {@code trait}, pousse le ballon sur la ligne que {@code trait} défend. */
    static boolean contreSonCamp(Coup coup, Couleur trait) {
        // TODO S03 Q8
        throw new UnsupportedOperationException("TODO S03 Q8");
    }
}
