package cpoo2.s05.ecs;

import chessball.Couleur;
import chessball.TypePiece;
import chessball.Verdict;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * TD5, partie A : un Entity Component System minimal, <b>en contrepoint</b> de l'application
 * {@code chessball}. Une entité n'est qu'un identifiant, ses composants sont des {@code record}
 * sans comportement, les systèmes portent tout le comportement.
 *
 * <p>Ce paquet ne fait pas partie de l'application : il sert à la comparer.</p>
 */
public final class Monde {

    // ---------- les composants : des données, rien d'autre ----------

    public record Case(int colonne, int rangee) {}
    public record Equipe(Couleur couleur) {}
    public record Motif(TypePiece type) {}
    public record PorteurBallon() {}                       // composant « marqueur »
    public record VeutSeDeplacer(Case cible) {}            // l'intention, posée par l'entrée

    /** Ce que décide qu'une intention est légale, sans un seul {@code if} de types dans le système. */
    @FunctionalInterface
    public interface RegleDuMonde {
        Verdict verifier(Monde monde, int entite, Case cible);
    }

    /** Un système : une requête sur les entités, une boucle. */
    @FunctionalInterface
    public interface Systeme {
        void mettreAJour(Monde monde);
    }

    // ---------- le stockage : type de composant -> (entité -> composant) ----------

    private int prochainId = 0;
    private final Map<Class<?>, Map<Integer, Object>> composants = new HashMap<>();

    /** Un identifiant neuf. */
    public int creerEntite() {
        throw new UnsupportedOperationException("TODO TD5 Q1");
    }

    public <C> void ajouter(int entite, C composant) {
        throw new UnsupportedOperationException("TODO TD5 Q1");
    }

    /** L'absence d'un composant n'est pas une erreur : c'est l'Objet nul de S01, {@code Optional}. */
    public <C> Optional<C> composant(int entite, Class<C> type) {
        throw new UnsupportedOperationException("TODO TD5 Q1");
    }

    public void retirer(int entite, Class<?> type) {
        throw new UnsupportedOperationException("TODO TD5 Q1");
    }

    /** Les entités qui ont TOUS ces composants : une intersection d'ensembles de clés. */
    public Set<Integer> entitesAvec(Class<?>... types) {
        throw new UnsupportedOperationException("TODO TD5 Q1");
    }

    // ---------- le système de déplacement (Q3) ----------

    /** Reçoit UNE règle, ne teste aucun type : la règle décide, le système applique. */
    public static Systeme systemeDeplacement(RegleDuMonde regle) {
        return monde -> {
            throw new UnsupportedOperationException("TODO TD5 Q3");
        };
    }
}
