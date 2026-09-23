package chessball;

import java.util.Objects;

/**
 * Trois lignes de vérification, pour travailler sans JUnit (le TD7 vous fera écrire le vrai).
 * Appelez {@link #bilan()} à la fin de votre {@code main}.
 */
public final class Verif {

    private static int reussis = 0;
    private static int echoues = 0;

    private Verif() {}

    public static void que(boolean condition, String message) {
        if (condition) {
            reussis++;
        } else {
            echoues++;
            System.out.println("  ÉCHEC : " + message);
        }
    }

    public static void egal(Object attendu, Object obtenu, String message) {
        que(Objects.equals(attendu, obtenu), message + " (attendu " + attendu + ", obtenu " + obtenu + ")");
    }

    /** Vérifie qu'une action refuse bien ce qu'elle doit refuser. */
    public static void leve(Class<? extends Throwable> type, Runnable action, String message) {
        try {
            action.run();
            que(false, message + " (aucune exception levée)");
        } catch (Throwable t) {
            que(type.isInstance(t), message + " (levé : " + t.getClass().getSimpleName() + ")");
        }
    }

    public static void bilan() {
        System.out.printf("%n%d vérification(s) : %d OK, %d en échec%n", reussis + echoues, reussis, echoues);
    }
}
