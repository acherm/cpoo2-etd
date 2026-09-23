package chessball;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * TD6 ex. 2, Q9 : l'oracle du plug-in. Rouge sur le squelette.
 *
 * <p>L'adversaire est découvert par son nom, au chargement. Les classes ci-dessous jouent le
 * tiers : le code de {@code chessball} ne les nomme nulle part, elles n'existent que pour le test.
 * Chaque défaillance possible du plug-in a son test, et toutes se replient sur l'aléatoire.</p>
 */
class PlugInTest {

    /** Le plug-in d'un tiers : bien formé. */
    public static final class AdversaireTiers implements StrategieAdversaire {
        public AdversaireTiers(Random random) {}
        @Override public Coup choisirCoup(Plateau plateau, Couleur trait) { return CoupsPossibles.pour(plateau, trait).get(0); }
    }

    /** Existe, a le bon constructeur, mais n'est pas un adversaire. */
    public static final class PasUnAdversaire {
        public PasUnAdversaire(Random random) {}
    }

    /** Le constructeur lève : un plug-in ne doit pas pouvoir planter le jeu. */
    public static final class AdversaireQuiPlante implements StrategieAdversaire {
        public AdversaireQuiPlante(Random random) { throw new IllegalStateException("licence expirée"); }
        @Override public Coup choisirCoup(Plateau plateau, Couleur trait) { throw new IllegalStateException(); }
    }

    static Properties avec(String nom) {
        Properties p = new Properties();
        if (nom != null) p.setProperty(ChargeurDAdversaire.CLE, nom);
        return p;
    }

    @Test
    void unPlugInBienFormeEstCharge() {
        assertInstanceOf(AdversaireTiers.class, ChargeurDAdversaire.charger(avec(AdversaireTiers.class.getName()), new Random(1)));
    }

    @Test
    void classeAbsenteRepliSurLAleatoire() {
        assertInstanceOf(AdversaireAleatoire.class, ChargeurDAdversaire.charger(avec("chessball.AdversaireMinimax"), new Random(1)));
    }

    @Test
    void mauvaisTypeRepliSurLAleatoire() {
        assertInstanceOf(AdversaireAleatoire.class, ChargeurDAdversaire.charger(avec(PasUnAdversaire.class.getName()), new Random(1)));
    }

    @Test
    void constructeurAbsentRepliSurLAleatoire() {
        assertInstanceOf(AdversaireAleatoire.class, ChargeurDAdversaire.charger(avec("java.lang.String"), new Random(1)));
    }

    @Test
    void constructeurQuiLeveRepliSurLAleatoire() {
        assertInstanceOf(AdversaireAleatoire.class, ChargeurDAdversaire.charger(avec(AdversaireQuiPlante.class.getName()), new Random(1)));
    }

    @Test
    void cleAbsenteRepliSurLAleatoire() {
        assertInstanceOf(AdversaireAleatoire.class, ChargeurDAdversaire.charger(avec(null), new Random(1)));
    }

    @Test
    void leFichierDeProprietesEstLu(@TempDir Path dossier) throws Exception {
        Path fichier = dossier.resolve("plugins.properties");
        Files.writeString(fichier, ChargeurDAdversaire.CLE + "=" + AdversaireTiers.class.getName() + "\n");
        assertInstanceOf(AdversaireTiers.class, ChargeurDAdversaire.charger(fichier, new Random(1)));
        assertInstanceOf(AdversaireAleatoire.class, ChargeurDAdversaire.charger(dossier.resolve("absent.properties"), new Random(1)));
    }
}
