package chessball;

import java.util.List;
import java.util.Objects;

/**
 * S07, exercice 9 : ce qui sort de la partie vers l'affichage, un fichier ou le réseau
 * (exercice 17 du fil rouge, « ce qui sort du modèle »). Le <b>DTO</b>.
 *
 * <p>Des données plates et immuables, sans une seule référence vers le modèle : des entiers, des
 * chaînes, une énumération. Aucun comportement métier, aucun invariant à protéger. Il est assemblé
 * au moment de sortir, à partir de l'interface {@link Jeu} et d'elle seule.</p>
 *
 * <p>Oracle : {@code EtatPartieDTOTest}. TODO Q15 : l'assembleur {@link #de(Jeu)}, puis
 * {@link #enTexte()} et {@link #enJson()}, écrits à la main.</p>
 */
public record EtatPartieDTO(
        int scoreBleus,
        int scoreRouges,
        Couleur trait,
        int coupsJoues,
        String plateau,
        List<String> derniersCoups) {

    /** Combien de coups voyagent avec l'état : assez pour un affichage, pas tout le carnet. */
    public static final int DERNIERS_COUPS = 5;

    public EtatPartieDTO {
        Objects.requireNonNull(trait);
        Objects.requireNonNull(plateau);
        derniersCoups = List.copyOf(derniersCoups);   // figé : personne ne le remplira après coup
    }

    /** L'assembleur : lit le jeu par son interface, copie, et coupe le lien. */
    public static EtatPartieDTO de(Jeu jeu) {
        throw new UnsupportedOperationException("TODO S07 Q15");
    }

    /** Pour un terminal ou un journal : le score d'abord, le plateau, puis les derniers coups. */
    public String enTexte() {
        throw new UnsupportedOperationException("TODO S07 Q15");
    }

    /** Pour une IHM ou un service, à la main : l'autre côté n'a pas nos classes, seulement du texte. */
    public String enJson() {
        throw new UnsupportedOperationException("TODO S07 Q15");
    }
}
