package chessball;

import java.util.List;

/**
 * Un coup, décrit par ses <em>données</em> : d'où, vers où, dans quelle direction.
 *
 * <p>Interface {@code sealed}, un {@code record} par sorte de coup, une {@link #origine()}
 * commune : la forme du {@code Coup} du moteur de CPOO1. Aujourd'hui un coup ne <em>fait</em>
 * rien, c'est la {@link Partie} qui l'applique dans son {@code switch}.</p>
 *
 * <p><b>S05, Q4 bis (Commande)</b> : donnez à chaque coup son {@link #executer(Partie)}, en y
 * déplaçant le code de {@code Partie.appliquer}, puis faites que la partie appelle
 * {@code coup.executer(this)}. <b>Q5 bis (Memento)</b> : {@link #annuler(Partie)} par le coup
 * inverse, et sa limite. <b>Q5 ter (Composite)</b> : la {@link Macro}, plusieurs coups qui n'en
 * font qu'un.</p>
 *
 * <p>{@code toString()} rend la notation du journal : {@code e2-d4}, {@code d4>d6},
 * {@code d4!NORD}, {@code engage@d4}, {@code engagement[engage@d4 + d1-d4]}.</p>
 */
public sealed interface Coup {

    /** Case de la pièce qui agit. */
    Position origine();

    /** Applique l'effet du coup. L'arbitrage a déjà eu lieu : ici, on ne vérifie plus rien. */
    default void executer(Partie partie) {
        throw new UnsupportedOperationException("TODO S05 Q4 bis : " + this + " ne sait pas encore s'exécuter");
    }

    /** Défait l'effet du coup en jouant l'inverse. Tous les coups n'y arrivent pas : c'est la leçon. */
    default void annuler(Partie partie) {
        throw new UnsupportedOperationException("ce coup ne sait pas s'annuler : " + this);
    }

    // ------------------------------------------------------------------

    /** Déplacer la pièce en {@code de} vers la case {@code vers}. */
    record Deplacement(Position de, Position vers) implements Coup {
        @Override public Position origine() { return de; }
        @Override public String toString() { return de + "-" + vers; }
    }

    /** La porteuse du ballon le passe à une coéquipière. */
    record Passe(Position porteuse, Position cible) implements Coup {
        @Override public Position origine() { return porteuse; }
        @Override public String toString() { return porteuse + ">" + cible; }
    }

    /** La porteuse tire dans une direction : le ballon file jusqu'à une pièce, ou sort. */
    record Tir(Position porteuse, Direction direction) implements Coup {
        @Override public Position origine() { return porteuse; }
        @Override public String toString() { return porteuse + "!" + direction; }
    }

    /** Poser le ballon libre sur une case centrale : l'engagement (J2). */
    record PoserBallon(Position ou) implements Coup {
        @Override public Position origine() { return ou; }
        @Override public String toString() { return "engage@" + ou; }
    }

    /**
     * Plusieurs coups qui n'en font qu'un : un engagement (J3), une combinaison rejouable.
     * TODO S05 Q5 ter : {@code executer} dans l'ordre, {@code annuler} dans l'ordre inverse.
     */
    record Macro(String nom, List<Coup> coups) implements Coup {
        public Macro(String nom, Coup... coups) { this(nom, List.of(coups)); }
        @Override public Position origine() {
            if (coups.isEmpty()) throw new IllegalStateException("macro vide : " + nom);
            return coups.get(0).origine();
        }
        @Override public String toString() {
            StringBuilder sb = new StringBuilder(nom).append('[');
            for (int i = 0; i < coups.size(); i++) sb.append(i == 0 ? "" : " + ").append(coups.get(i));
            return sb.append(']').toString();
        }
    }
}
