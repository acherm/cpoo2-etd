package cpoo2.s05.bt;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.Position;

/**
 * Les feuilles de l'attaquant, fournies : des conditions et des actions sur la partie. Aucune ne
 * joue de but contre son camp.
 */
public final class Feuilles {

    private Feuilles() {}

    /** Une poussée légale envoie le ballon sur la ligne où l'on marque. */
    public static Noeud butAPortee() {
        return new Condition("un but est à portée", ctx -> poussees(ctx).stream().anyMatch(p -> marque(p, ctx.equipe())));
    }

    /** Jouer cette poussée. */
    public static Noeud marquer() {
        return new Action("marquer", ctx -> poussees(ctx).stream().filter(p -> marque(p, ctx.equipe())).<Coup>map(p -> p).findFirst());
    }

    /** Une de nos pièces touche le ballon. */
    public static Noeud auContactDuBallon() {
        return new Condition("au contact du ballon", ctx -> ctx.jeu().plateau().pieces().entrySet().stream()
                .anyMatch(e -> e.getValue().couleur() == ctx.equipe() && distance(e.getKey(), ctx.jeu().plateau().ballon()) == 1));
    }

    /** Une poussée qui rapproche le ballon de la ligne où l'on marque. */
    public static Noeud pousserVersLeBut() {
        return new Action("pousser vers le but", ctx -> {
            int avant = ecart(ctx.jeu().plateau().ballon(), ctx.equipe());
            return poussees(ctx).stream()
                    .filter(p -> ecart(p.arriveeDuBallon().orElseThrow(), ctx.equipe()) < avant)
                    .<Coup>map(p -> p).findFirst();
        });
    }

    /** Le déplacement qui amène une pièce le plus près du ballon, s'il la rapproche. */
    public static Noeud seRapprocherDuBallon() {
        return new Action("se rapprocher du ballon", ctx -> {
            Position ballon = ctx.jeu().plateau().ballon();
            return ctx.jeu().coupsLegaux().stream()
                    .filter(c -> c instanceof Coup.Deplacement)
                    .filter(c -> distance(c.arrivee(), ballon) < distance(c.origine(), ballon))
                    .min(Comparator.comparingInt(c -> distance(c.arrivee(), ballon)));
        });
    }

    /** N'importe quel coup légal, tiré avec le hasard de cette IA, sauf un but contre son camp. */
    public static Noeud auHasard() {
        return new Action("au hasard", ctx -> {
            List<Coup> surs = ctx.jeu().coupsLegaux().stream().filter(c -> !contreSonCamp(c, ctx.equipe())).toList();
            return surs.isEmpty() ? Optional.empty() : Optional.of(surs.get(ctx.hasard().nextInt(surs.size())));
        });
    }

    // ---------- outils ----------

    private static List<Coup.Poussee> poussees(Contexte ctx) {
        return ctx.jeu().coupsLegaux().stream()
                .filter(c -> c instanceof Coup.Poussee).map(c -> (Coup.Poussee) c)
                .filter(p -> !contreSonCamp(p, ctx.equipe()))
                .toList();
    }

    private static boolean marque(Coup.Poussee p, Couleur equipe) {
        return p.arriveeDuBallon().orElseThrow().rangee() == equipe.rangeeOuElleMarque();
    }

    static boolean contreSonCamp(Coup coup, Couleur equipe) {
        return coup instanceof Coup.Poussee p && p.arriveeDuBallon().orElseThrow().rangee() == equipe.rangeeQuElleDefend();
    }

    private static int ecart(Position p, Couleur equipe) {
        return Math.abs(p.rangee() - equipe.rangeeOuElleMarque());
    }

    static int distance(Position a, Position b) {
        return Math.max(Math.abs(a.colonne() - b.colonne()), Math.abs(a.rangee() - b.rangee()));
    }
}
