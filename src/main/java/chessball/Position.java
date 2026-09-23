package chessball;

/**
 * Une case du plateau, en coordonnées internes 0..7 : colonne = a..h, rangee = 1..8.
 * Mêmes composants et même fabrique que le {@code Position} du moteur de CPOO1 —
 * à une différence près, volontaire : <b>aucun contrôle de bornes ici</b>, parce que
 * ce jeu a une variante 10×10 et que c'est le {@link Plateau} qui connaît sa taille.
 */
public record Position(int colonne, int rangee) implements Comparable<Position> {

    /** Fabrique lisible : {@code Position.of("e4")} (et {@code "a10"} sur grand terrain). */
    public static Position of(String algebrique) {
        if (algebrique == null || algebrique.length() < 2) {
            throw new IllegalArgumentException("notation attendue « a1 »… : " + algebrique);
        }
        return new Position(algebrique.charAt(0) - 'a', Integer.parseInt(algebrique.substring(1)) - 1);
    }

    public Position decalee(int dColonne, int dRangee) {
        return new Position(colonne + dColonne, rangee + dRangee);
    }

    @Override public String toString() {
        return "" + (char) ('a' + colonne) + (rangee + 1);
    }

    @Override public int compareTo(Position autre) {
        int c = Integer.compare(rangee, autre.rangee);
        return c != 0 ? c : Integer.compare(colonne, autre.colonne);
    }
}
