package chessball;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Deux façons de jouer, les plus simples, pour que l'application tourne dès la séance 9 : au
 * hasard, et « pressé » (tirer dès qu'on peut, sinon passer, sinon bouger).
 *
 * <p>Un adversaire est ici une fonction {@code Partie -> Coup} : c'est la forme minimale d'une
 * Stratégie. La séance 6 en fait une interface nommée ({@code StrategieAdversaire}) avec ses
 * politiques ; ces deux-là s'y branchent par une lambda.</p>
 */
public final class Adversaires {

    private Adversaires() {}

    /** Choisit uniformément parmi les coups légaux. À graine égale, mêmes coups : c'est rejouable. */
    public static Function<Partie, Coup> auHasard(long graine) {
        Random random = new Random(graine);
        return partie -> {
            List<Coup> possibles = GenerateurDeCoups.coupsLegaux(partie);
            if (possibles.isEmpty()) throw new IllegalStateException("aucun coup possible pour " + partie.trait());
            return possibles.get(random.nextInt(possibles.size()));
        };
    }

    /**
     * Tire si un tir est légal, sinon passe, sinon va chercher le ballon s'il est libre et à portée,
     * sinon se déplace au hasard. Assez pour marquer, pas assez pour bien jouer.
     */
    public static Function<Partie, Coup> presse(long graine) {
        Random random = new Random(graine);
        return partie -> {
            List<Coup> possibles = GenerateurDeCoups.coupsLegaux(partie);
            if (possibles.isEmpty()) throw new IllegalStateException("aucun coup possible pour " + partie.trait());
            List<Coup> tirs = possibles.stream().filter(c -> c instanceof Coup.Tir).toList();
            if (tirs.isEmpty() && partie.plateau().ballonEstLibre()) {
                Position ballon = partie.plateau().positionDuBallon().orElseThrow();
                for (Coup c : possibles) {
                    if (c instanceof Coup.Deplacement d && d.vers().equals(ballon)) return c;   // J5 : arriver dessus, c'est s'en emparer
                }
            }
            if (!tirs.isEmpty()) return tirs.get(random.nextInt(tirs.size()));
            List<Coup> passes = possibles.stream().filter(c -> c instanceof Coup.Passe).toList();
            if (!passes.isEmpty()) return passes.get(random.nextInt(passes.size()));
            return possibles.get(random.nextInt(possibles.size()));
        };
    }

    // ------------------------------------------------------------------ les adaptateurs
    // Les séances 4 et 5 écrivent leurs adversaires sous d'autres formes : StrategieAdversaire
    // (S06, sur le plateau et le trait) et AdversaireBT (S05, sur la partie). Les deux méthodes
    // ci-dessous les ramènent à la forme Partie -> Coup que Main et l'IA sous délai attendent.
    // C'est un Adaptateur, le plus petit possible : une lambda qui traduit la signature.

    /** Un adversaire de la séance 6, vu comme une fonction de la partie. */
    public static Function<Partie, Coup> depuis(StrategieAdversaire strategie) {
        return partie -> strategie.choisirCoup(partie.plateau(), partie.trait());
    }

    /** L'attaquant behaviour tree de la séance 5, vu comme une fonction de la partie. */
    public static Function<Partie, Coup> depuis(chessball.ia.AdversaireBT bt) {
        return bt::choisir;
    }

    /**
     * Un adversaire par son nom, pour la ligne de commande de {@link Main} : {@code hasard},
     * {@code presse} (fournis), {@code heuristique} (S06), {@code bt} (S05). Les deux derniers ne
     * jouent qu'une fois leurs séances faites : avant, ils lèvent une exception qui le dit.
     */
    public static Function<Partie, Coup> parNom(String nom, long graine) {
        return switch (nom) {
            case "hasard" -> auHasard(graine);
            case "presse" -> presse(graine);
            case "heuristique" -> depuis(new AdversaireHeuristique(new Random(graine)));
            case "bt" -> depuis(new chessball.ia.AdversaireBT());
            default -> throw new IllegalArgumentException("adversaire inconnu : " + nom
                    + " (attendu : hasard, presse, heuristique, bt)");
        };
    }
}
