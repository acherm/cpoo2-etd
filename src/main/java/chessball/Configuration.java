package chessball;

import java.util.ArrayList;
import java.util.List;

/**
 * S07, exercice 8 : les paramètres d'une partie, et le <b>Monteur</b> qui les assemble
 * (exercice 20 du fil rouge, « trop de paramètres »).
 *
 * <p>Une partie a beaucoup d'options, presque toutes facultatives : le placement de départ,
 * la règle, qui engage, le nombre de tours par mi-temps (K, règle J11), la graine du hasard,
 * les abonnés présents dès le premier coup. Le monteur nomme chaque option, et
 * {@link Monteur#construire()} vérifie les invariants <b>une fois</b>. Le produit est immuable :
 * une configuration sert autant de parties qu'on veut.</p>
 *
 * <p>Oracle : {@code ConfigurationTest}. TODO Q13 : les champs du produit (tous {@code final}),
 * le constructeur privé qui copie ce que le monteur a accumulé, {@link #nouvellePartie()}, et le
 * monteur avec ses options nommées et son {@code construire()}.</p>
 */
public final class Configuration {

    public static final int TOURS_PAR_MI_TEMPS_PAR_DEFAUT = 20;
    public static final long GRAINE_PAR_DEFAUT = 42L;

    // TODO Q13 : les champs du produit, tous final : plateau de départ, règle, premier trait,
    // tours par mi-temps, graine, abonnés.

    private Configuration(Monteur m) {
        throw new UnsupportedOperationException("TODO S07 Q13");
    }

    /** Le point d'entrée du monteur : {@code Configuration.nouvelle().graine(7L).construire()}. */
    public static Monteur nouvelle() { return new Monteur(); }

    /** Le placement standard, le jeu de base, les Bleus engagent, K = 20, graine 42. */
    public static Configuration parDefaut() { return nouvelle().construire(); }

    /** Une partie neuve, sur une <b>copie</b> du plateau de départ, avec les abonnés déjà en place. */
    public Partie nouvellePartie() {
        throw new UnsupportedOperationException("TODO S07 Q13");
    }

    public Plateau plateauDeDepart() { throw new UnsupportedOperationException("TODO S07 Q13"); }
    public Regle regle() { throw new UnsupportedOperationException("TODO S07 Q13"); }
    public Couleur premierTrait() { throw new UnsupportedOperationException("TODO S07 Q13"); }
    public int toursParMiTemps() { throw new UnsupportedOperationException("TODO S07 Q13"); }
    public long graine() { throw new UnsupportedOperationException("TODO S07 Q13"); }
    public List<EcouteurDePartie> ecouteurs() { throw new UnsupportedOperationException("TODO S07 Q13"); }

    /**
     * Le Monteur : des options nommées, accumulées, validées une fois dans construire().
     * Chaque option rend {@code this} : les appels se chaînent, dans n'importe quel ordre.
     */
    public static final class Monteur {
        // TODO Q13 : une valeur par défaut pour chaque option (Placements.standard(),
        // Regles.duJeuDeBase(), Couleur.BLEUS, TOURS_PAR_MI_TEMPS_PAR_DEFAUT, GRAINE_PAR_DEFAUT,
        // aucun abonné).
        private final List<EcouteurDePartie> ecouteurs = new ArrayList<>();

        private Monteur() { }

        public Monteur plateau(Plateau p) { throw new UnsupportedOperationException("TODO S07 Q13"); }
        public Monteur regle(Regle r) { throw new UnsupportedOperationException("TODO S07 Q13"); }
        public Monteur premierTrait(Couleur c) { throw new UnsupportedOperationException("TODO S07 Q13"); }
        public Monteur toursParMiTemps(int k) { throw new UnsupportedOperationException("TODO S07 Q13"); }
        public Monteur graine(long g) { throw new UnsupportedOperationException("TODO S07 Q13"); }
        public Monteur ecouteur(EcouteurDePartie e) { ecouteurs.add(e); return this; }

        /**
         * Les invariants, vérifiés ici et nulle part ailleurs : au moins un tour par mi-temps, et
         * un placement de départ qui donne des pièces aux deux équipes. Sinon {@code IllegalStateException}.
         */
        public Configuration construire() {
            throw new UnsupportedOperationException("TODO S07 Q13");
        }
    }
}
