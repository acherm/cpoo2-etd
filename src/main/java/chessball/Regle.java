package chessball;

/** Une condition que doit respecter un coup avant d'être joué. Une seule méthode : les règles se composent. */
@FunctionalInterface
public interface Regle {
    Verdict verifier(Partie partie, Coup coup);
}
