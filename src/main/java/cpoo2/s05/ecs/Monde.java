package cpoo2.s05.ecs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Piece;
import cpoo2.jeu.Plateau;
import cpoo2.jeu.Position;
import cpoo2.jeu.TypePiece;

/**
 * S05, exercice 4 : un Entity Component System minimal, <b>en contrepoint</b> de la
 * {@code Partie}. Une entité n'est qu'un identifiant, ses composants sont des {@code record} sans
 * comportement, les systèmes portent tout le comportement. Le ballon est une entité comme une
 * autre : il a une case, et le marqueur {@link Ballon}.
 */
public final class Monde {

    // ---------- les composants : des données, rien d'autre ----------

    public record Case(int colonne, int rangee) {}
    public record Equipe(Couleur couleur) {}
    public record Role(TypePiece type) {}
    public record Ballon() {}                              // composant « marqueur »
    public record VeutSeDeplacer(Case cible) {}            // l'intention, posée par l'entrée ou une IA

    /** Ce qui décide si une intention est légale : le motif du refus, ou vide si elle l'est. */
    @FunctionalInterface
    public interface RegleDuMonde {
        Optional<String> refus(Monde monde, int entite, Case cible);
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
        throw new UnsupportedOperationException("TODO S05 Q16");
    }

    public <C> void ajouter(int entite, C composant) {
        throw new UnsupportedOperationException("TODO S05 Q16");
    }

    /** L'absence d'un composant n'est pas une erreur : c'est l'Objet nul de S01, {@code Optional}. */
    public <C> Optional<C> composant(int entite, Class<C> type) {
        throw new UnsupportedOperationException("TODO S05 Q16");
    }

    public void retirer(int entite, Class<?> type) {
        throw new UnsupportedOperationException("TODO S05 Q16");
    }

    /** Les entités qui ont TOUS ces composants : une intersection d'ensembles de clés. */
    public Set<Integer> entitesAvec(Class<?>... types) {
        throw new UnsupportedOperationException("TODO S05 Q16");
    }

    // ---------- fournis : la position de départ, et une règle ----------

    /** Le plateau de la boîte en entités : dix pièces et un ballon, onze entités. */
    public static Monde depuis(Plateau plateau) {
        Monde monde = new Monde();
        for (Map.Entry<Position, Piece> e : plateau.pieces().entrySet()) {
            int piece = monde.creerEntite();
            monde.ajouter(piece, new Case(e.getKey().colonne(), e.getKey().rangee()));
            monde.ajouter(piece, new Equipe(e.getValue().couleur()));
            monde.ajouter(piece, new Role(e.getValue().type()));
        }
        int ballon = monde.creerEntite();
        monde.ajouter(ballon, new Case(plateau.ballon().colonne(), plateau.ballon().rangee()));
        monde.ajouter(ballon, new Ballon());
        return monde;
    }

    /** Le déplacement de la boîte : un pas vers une case voisine libre, c'est-à-dire que ni pièce ni ballon n'occupe. */
    public static RegleDuMonde unPasVersUneCaseLibre() {
        return (monde, entite, cible) -> {
            Case depart = monde.composant(entite, Case.class).orElseThrow();
            if (Math.max(Math.abs(cible.colonne() - depart.colonne()), Math.abs(cible.rangee() - depart.rangee())) != 1) {
                return Optional.of("un pas, vers une voisine");
            }
            boolean occupee = monde.entitesAvec(Case.class).stream()
                    .anyMatch(e -> monde.composant(e, Case.class).orElseThrow().equals(cible));
            return occupee ? Optional.of("case occupée") : Optional.empty();
        };
    }

    // ---------- le système de déplacement (Q17) ----------

    /** Reçoit UNE règle, ne teste aucun type : la règle décide, le système applique. */
    public static Systeme systemeDeplacement(RegleDuMonde regle) {
        return monde -> {
            throw new UnsupportedOperationException("TODO S05 Q17");
        };
    }
}
