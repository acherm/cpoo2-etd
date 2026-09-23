package chessball;

import java.util.EnumMap;
import java.util.Map;

/**
 * Le contexte de la Stratégie : un tournoi tient <b>une</b> partie et deux adversaires, et joue des
 * manches. Entre deux manches, on change d'adversaire <b>sans reconstruire la partie</b> : c'est
 * l'exigence « IA remplaçable à chaud pour des tournois IA contre IA ».
 */
public final class Tournoi {

    private final Partie partie;
    private final Map<Couleur, StrategieAdversaire> adversaires = new EnumMap<>(Couleur.class);
    private int manchesJouees;

    public Tournoi(Partie partie, StrategieAdversaire bleus, StrategieAdversaire rouges) {
        this.partie = partie;
        adversaires.put(Couleur.BLEUS, bleus);
        adversaires.put(Couleur.ROUGES, rouges);
    }

    /** Rechangeable à tout moment : le tournoi détient ses stratégies, il ne les connaît pas. */
    public void definirAdversaire(Couleur couleur, StrategieAdversaire adversaire) {
        // TODO S06 Q8 : remplacer, sans toucher à la partie
        throw new UnsupportedOperationException("TODO S06 Q8");
    }

    public StrategieAdversaire adversaire(Couleur couleur) { return adversaires.get(couleur); }

    /**
     * Une manche : {@code toursParEquipe} tours pour chaque équipe, sur la même partie. Poser le
     * ballon ne compte pas pour un tour (J3 : on engage <em>et</em> on joue).
     */
    public void jouerManche(int toursParEquipe) {
        // TODO S06 Q8 : tant que 2 × toursParEquipe tours n'ont pas été joués : l'adversaire du
        //   trait choisit sur une COPIE du plateau, la partie arbitre, un coup refusé est une
        //   erreur (IllegalStateException), poser le ballon ne compte pas pour un tour.
        throw new UnsupportedOperationException("TODO S06 Q8");
    }

    public int manchesJouees() { return manchesJouees; }

    public Partie partie() { return partie; }
}
