package cpoo2.jeu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Une partie d'essai : une position figée, son trait, et ses coups légaux <b>donnés à la main</b>
 * (ceux des tests ont été calculés par un arbitre complet). Il n'y a pas d'arbitre dans ce
 * paquet : {@code jouer} accepte un coup de la liste et le note, rien d'autre ne bouge.
 */
public final class PartieDEssai implements Jeu {

    private final Plateau plateau;
    private final Couleur trait;
    private final List<Coup> coupsLegaux;
    private final List<Coup> coupsJoues = new ArrayList<>();

    public PartieDEssai(Plateau plateau, Couleur trait, List<Coup> coupsLegaux) {
        this.plateau = plateau;
        this.trait = trait;
        this.coupsLegaux = List.copyOf(coupsLegaux);
    }

    /** {@code PartieDEssai.de(plateau, BLEUS, "déplacement b6-b5", "poussée c5-d4", …)}. */
    public static PartieDEssai de(Plateau plateau, Couleur trait, String... coupsLegaux) {
        return new PartieDEssai(plateau, trait, Arrays.stream(coupsLegaux).map(Coup::lire).toList());
    }

    /** La position de départ de la boîte et ses 25 coups légaux (ceux du kit officiel). */
    public static PartieDEssai depart() {
        return de(Plateau.officiel(), Couleur.BLEUS,
                "déplacement b6-b5", "déplacement b6-c6", "déplacement b6-a6", "déplacement b6-a5",
                "déplacement d6-d5", "déplacement d6-e6", "déplacement d6-c6",
                "déplacement f6-f5", "déplacement f6-g6", "déplacement f6-e6", "déplacement f6-g5",
                "déplacement c5-c6", "déplacement c5-c4", "déplacement c5-d5", "déplacement c5-b5",
                "poussée c5-d4", "saut c5-e3", "déplacement c5-b4",
                "déplacement e5-e6", "déplacement e5-e4", "déplacement e5-f5", "déplacement e5-d5",
                "déplacement e5-f4", "poussée e5-d4", "saut e5-c3");
    }

    @Override public Couleur trait() { return trait; }

    @Override public Plateau plateau() { return plateau; }

    @Override public List<Coup> coupsLegaux() { return coupsLegaux; }

    @Override public boolean jouer(Coup coup) {
        if (!coupsLegaux.contains(coup)) {
            return false;
        }
        coupsJoues.add(coup);
        return true;
    }

    /** Ce qui a été joué sur cette partie, dans l'ordre. Pas dans {@link Jeu} : c'est pour les tests. */
    public List<Coup> coupsJoues() { return Collections.unmodifiableList(coupsJoues); }
}
