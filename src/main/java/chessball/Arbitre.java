package chessball;

import java.util.List;
import java.util.function.Function;

/**
 * Le squelette d'un match, écrit une fois pour toutes : tirage au sort, puis pour chaque mi-temps
 * une partie neuve engagée par l'équipe qui doit l'être, K tours par équipe, et le score qui
 * s'accumule. Les variantes ne réécrivent pas l'algorithme : elles remplissent ses <b>trous</b>.
 *
 * <p>Le <b>Patron de méthode</b> de la séance 6. Le hasard du tirage est <b>reçu</b>
 * ({@link TirageAuSort}), jamais fabriqué ici : c'est l'injection de la même séance.</p>
 */
public abstract class Arbitre {

    /** Le bilan d'un match : les buts, et les mi-temps telles qu'elles ont été jouées. */
    public record Resultat(Couleur premierEngageur, int butsBleus, int butsRouges, List<Partie> miTemps) {
        public int buts(Couleur c) { return c == Couleur.BLEUS ? butsBleus : butsRouges; }
    }

    private final Function<Couleur, Partie> nouvelleMiTemps;
    private final int toursParMiTemps;
    private final TirageAuSort tirage;

    /**
     * @param nouvelleMiTemps la partie neuve d'une mi-temps, engagée par l'équipe donnée
     *                        (une {@link Variante} en fournit une)
     * @param toursParMiTemps K, le nombre de tours de chaque équipe par mi-temps
     * @param tirage          qui engage la première mi-temps
     */
    protected Arbitre(Function<Couleur, Partie> nouvelleMiTemps, int toursParMiTemps, TirageAuSort tirage) {
        this.nouvelleMiTemps = nouvelleMiTemps;
        this.toursParMiTemps = toursParMiTemps;
        this.tirage = tirage;
    }

    /** {@code final} : c'est l'algorithme, pas une suggestion. */
    public final Resultat jouerLeMatch(StrategieAdversaire bleus, StrategieAdversaire rouges) {
        // TODO S06 Q9 bis : le squelette. Le tirage désigne qui engage ; pour chaque mi-temps
        //   (nombreDeMiTemps()) : une partie neuve engagée par engageurDeLaMiTemps(...),
        //   le crochet avantMiTemps(...), toursParMiTemps() tours par équipe (le Tournoi de la
        //   Q8 sait les jouer), et le score de la mi-temps s'ajoute au total. Rendre le Resultat.
        throw new UnsupportedOperationException("TODO S06 Q9 bis");
    }

    // --- les trous que les variantes remplissent ---

    /** Deux mi-temps par défaut : la variante « une mi-temps » du feature model redéfinit. */
    protected int nombreDeMiTemps() { return 2; }

    protected int toursParMiTemps() { return toursParMiTemps; }

    /** Un crochet vide : une variante peut annoncer, journaliser, préparer. */
    protected void avantMiTemps(int miTemps, Partie partie) {}

    /** J12 : qui engage cette mi-temps, sachant qui a engagé la première. */
    protected abstract Couleur engageurDeLaMiTemps(int miTemps, Couleur premierEngageur);
}
