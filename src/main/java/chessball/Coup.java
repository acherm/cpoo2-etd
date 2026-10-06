package chessball;

import java.util.List;

/**
 * Un coup n'est pas un appel de méthode : c'est un <em>objet</em>. On peut le mettre dans une
 * liste, le rejouer, l'annuler, le nommer, l'envoyer sur le réseau.
 *
 * <p>Interface {@code sealed}, un {@code record} par sorte de coup, une {@link #origine()}
 * commune : la forme du {@code Coup} du moteur de CPOO1. La séance 4 y ajoute
 * {@link #executer(Partie)} (le coup sait s'appliquer : c'est la <b>Commande</b>),
 * {@link #annuler(Partie)} (et sa limite : la leçon du <b>Memento</b>) et la
 * {@link Macro} (plusieurs coups qui n'en font qu'un : le <b>Composite</b>).</p>
 *
 * <p>{@code toString()} rend la notation du journal : {@code e2-d4}, {@code d4>d6},
 * {@code d4!NORD}, {@code engage@d4}, {@code engagement[engage@d4 + d1-d4]}.</p>
 */
public sealed interface Coup {

    /** Case de la pièce qui agit. */
    Position origine();

    /** Applique l'effet du coup. L'arbitrage a déjà eu lieu : ici, on ne vérifie plus rien. */
    void executer(Partie partie);

    /** Défait l'effet du coup en jouant l'inverse. Tous les coups n'y arrivent pas : c'est la leçon. */
    default void annuler(Partie partie) {
        throw new UnsupportedOperationException("ce coup ne sait pas s'annuler : " + this);
    }

    // ------------------------------------------------------------------

    /** Déplacer la pièce en {@code de} vers la case {@code vers}. */
    record Deplacement(Position de, Position vers) implements Coup {
        @Override public Position origine() { return de; }
        @Override public void executer(Partie partie) { partie.plateau().deplacer(de, vers); }
        /** Naïf, et faux si la pièce a ramassé un ballon libre en chemin (J5) : voir la Q5 bis. */
        @Override public void annuler(Partie partie) { partie.plateau().deplacer(vers, de); }
        @Override public String toString() { return de + "-" + vers; }
    }

    /** La porteuse du ballon le passe à une coéquipière. */
    record Passe(Position porteuse, Position cible) implements Coup {
        @Override public Position origine() { return porteuse; }
        @Override public void executer(Partie partie) {
            partie.plateau().donnerBallon(partie.plateau().pieceEn(cible).orElseThrow());
        }
        @Override public void annuler(Partie partie) {
            partie.plateau().donnerBallon(partie.plateau().pieceEn(porteuse).orElseThrow());
        }
        @Override public String toString() { return porteuse + ">" + cible; }
    }

    /** La porteuse tire dans une direction : le ballon file jusqu'à une pièce, ou sort. */
    record Tir(Position porteuse, Direction direction) implements Coup {
        @Override public Position origine() { return porteuse; }
        @Override public void executer(Partie partie) {
            Plateau plateau = partie.plateau();
            Couleur tireur = plateau.porteuse().orElseThrow().couleur();
            Position courante = porteuse;
            while (true) {
                Position suivante = courante.decalee(direction.dColonne(), direction.dRangee());
                if (!plateau.contient(suivante)) {
                    boolean franchitLaRangeeDeFond = tireur == Couleur.BLEUS
                            ? suivante.rangee() >= plateau.taille()
                            : suivante.rangee() < 0;
                    if (franchitLaRangeeDeFond) {
                        plateau.retirerLeBallon();          // le ballon sort du terrain : on réengagera
                        partie.but(tireur);
                    } else {
                        plateau.poserBallonLibre(courante);  // sortie latérale (J8)
                    }
                    return;
                }
                Piece rencontree = plateau.pieceEn(suivante).orElse(null);
                if (rencontree != null) {
                    plateau.donnerBallon(rencontree);       // adverse : interception, amie : réception
                    return;
                }
                courante = suivante;
            }
        }
        @Override public String toString() { return porteuse + "!" + direction; }
    }

    /** Poser le ballon libre sur une case centrale : l'engagement (J2). */
    record PoserBallon(Position ou) implements Coup {
        @Override public Position origine() { return ou; }
        @Override public void executer(Partie partie) { partie.plateau().poserBallonLibre(ou); }
        @Override public String toString() { return "engage@" + ou; }
    }

    /** Plusieurs coups qui n'en font qu'un : un engagement (J3), une combinaison rejouable. */
    record Macro(String nom, List<Coup> coups) implements Coup {
        public Macro(String nom, Coup... coups) { this(nom, List.of(coups)); }
        @Override public Position origine() {
            if (coups.isEmpty()) throw new IllegalStateException("macro vide : " + nom);
            return coups.get(0).origine();
        }
        @Override public void executer(Partie partie) {
            for (Coup c : coups) c.executer(partie);
        }
        @Override public void annuler(Partie partie) {
            for (int i = coups.size() - 1; i >= 0; i--) coups.get(i).annuler(partie);
        }
        @Override public String toString() {
            StringBuilder sb = new StringBuilder(nom).append('[');
            for (int i = 0; i < coups.size(); i++) sb.append(i == 0 ? "" : " + ").append(coups.get(i));
            return sb.append(']').toString();
        }
    }
}
