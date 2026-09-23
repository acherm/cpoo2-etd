package chessball;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Random;

/**
 * Le plug-in : l'adversaire est découvert <b>au chargement</b>, par son nom, lu dans un fichier
 * {@code plugins.properties} ({@code adversaire=chessball.AdversaireHeuristique}). Un tiers peut
 * livrer sa propre IA sans toucher au dépôt du jeu. Repli sur l'aléatoire si le nom est absent,
 * inconnu, du mauvais type, sans le bon constructeur, ou si le constructeur lève.
 */
public final class ChargeurDAdversaire {

    public static final String CLE = "adversaire";

    private ChargeurDAdversaire() {}

    public static StrategieAdversaire charger(Path fichier, Random random) {
        Properties proprietes = new Properties();
        try (InputStream in = Files.newInputStream(fichier)) {
            proprietes.load(in);
        } catch (IOException e) {
            return new AdversaireAleatoire(random);
        }
        return charger(proprietes, random);
    }

    public static StrategieAdversaire charger(Properties proprietes, Random random) {
        // TODO S06 Q9 : lire la clé CLE, Class.forName, le constructeur (Random), instancier,
        //   vérifier le type par instanceof, et se replier sur AdversaireAleatoire dans TOUS les
        //   cas d'échec (clé absente, classe inconnue, mauvais type, constructeur absent, constructeur
        //   qui lève). Vous avez tout vu au TD de l'injecteur.
        throw new UnsupportedOperationException("TODO S06 Q9");
    }
}
