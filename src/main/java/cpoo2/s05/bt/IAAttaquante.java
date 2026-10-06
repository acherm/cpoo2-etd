package cpoo2.s05.bt;

import java.util.Random;

import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;
import cpoo2.s03.ia.IA;

/**
 * S05, exercice 3 : une IA de la séance 3, écrite en <b>behaviour tree</b>. L'arbre est un
 * <b>poids-mouche</b> : une seule instance, partagée par toutes les attaquantes. Ce qui appartient
 * à chacune voyage dans le {@link Contexte}.
 */
public final class IAAttaquante implements IA {

    public IAAttaquante(Random hasard) {
        // TODO S05 Q14 : ce qui appartient à cette IA, et à elle seule
    }

    /** L'arbre, le même pour toutes les attaquantes. TODO S05 Q13 : l'assembler, dans l'ordre des priorités. */
    public static Noeud arbre() {
        throw new UnsupportedOperationException("TODO S05 Q13");
    }

    @Override public Coup choisirCoup(Jeu jeu) {
        // TODO S05 Q14 : un contexte pour cette décision, un tick de l'arbre partagé, le coup choisi
        throw new UnsupportedOperationException("TODO S05 Q14");
    }
}
