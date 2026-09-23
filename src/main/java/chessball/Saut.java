package chessball;

/**
 * Le déplacement d'un sauteur : un saut n'est pas une direction (on ne glisse pas
 * le long d'un saut), c'est pourquoi le moteur de CPOO1 répond {@code false} à
 * {@code directionEmpruntable(CAVALIER, …)}.
 */
public record Saut(int dColonne, int dRangee) {}
