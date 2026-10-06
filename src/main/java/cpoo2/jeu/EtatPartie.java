package cpoo2.jeu;

import java.util.List;
import java.util.Optional;

/**
 * S05, exercice 1 : le patron <b>État</b>. L'état d'une partie décide de ce qu'elle permet (ses
 * coups légaux) et de ce qu'elle devient après un coup. La {@link Partie} ne teste plus aucun
 * drapeau : elle délègue à son état.
 *
 * <pre>
 *   EnJeu ──tacle──▶ ApresTacle ──autre coup──▶ EnJeu
 *     │ │                 │
 *     │ └──but──▶ Gagnee ◀┘ but
 *     └──nulle──▶ Nulle  ◀── nulle
 * </pre>
 */
public sealed interface EtatPartie permits EnJeu, ApresTacle, Gagnee, Nulle {

    /** Les coups que cet état autorise, parmi les coups possibles de la partie. */
    List<Coup> coupsLegaux(Partie partie);

    default boolean estTerminee() { return false; }

    default Optional<Couleur> vainqueur() { return Optional.empty(); }

    /**
     * L'état suivant, une fois le coup joué et appliqué (le plateau, le trait et le compteur de
     * la partie sont déjà à jour). Les deux états en cours partagent ces transitions : un but
     * mène à {@link Gagnee}, une partie bloquée ou trop longue à {@link Nulle}, un tacle à
     * {@link ApresTacle}, tout autre coup à {@link EnJeu}.
     */
    default EtatPartie apres(Coup coup, Partie partie) {
        // TODO S05 Q4 : les transitions de votre machine à états (Q1)
        throw new UnsupportedOperationException("TODO S05 Q4");
    }
}
