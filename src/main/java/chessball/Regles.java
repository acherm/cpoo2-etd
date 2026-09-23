package chessball;

/**
 * L'arbitrage du jeu de base, <b>tel qu'on l'écrit d'abord</b> : une seule règle, une cascade de
 * conditions. Elle marche. Mais chaque variante rouvre cette méthode, et une règle ne se teste
 * pas seule : elle n'existe pas en tant qu'objet.
 *
 * <p><b>S05, Q2 bis (Composite)</b> : démontez {@link #verifierJeuDeBase} en règles élémentaires
 * ({@link #trait}, {@link #motifRespecte}, {@link #passeValide}, {@link #tirValide},
 * {@link #engagementAuCentre}), écrivez les deux assemblages {@link #toutesLes} (le ET) et
 * {@link #lUneDes} (le OU), puis faites de {@link #duJeuDeBase} une composition. Une règle
 * composée est une règle. <b>Q5 ter</b> : {@link #enDepliantLesMacros}.</p>
 */
public final class Regles {

    private Regles() {}

    // ---------- règles élémentaires : TODO S05, Q2 bis ----------

    /** On ne joue pas la pièce de l'adversaire, on ne passe ni ne tire sans le ballon. */
    public static Regle trait() {
        throw new UnsupportedOperationException("TODO S05 Q2 bis");
    }

    /** Le déplacement suit le motif de la pièce et la case d'arrivée est libre (J6 : aucune capture). */
    public static Regle motifRespecte() {
        throw new UnsupportedOperationException("TODO S05 Q2 bis");
    }

    /** Passe : cible coéquipière, alignée sur une trajectoire de la porteuse, chemin libre (J7). */
    public static Regle passeValide() {
        throw new UnsupportedOperationException("TODO S05 Q2 bis");
    }

    /** Tir : la direction appartient au motif de la porteuse et progresse vers la ligne adverse. */
    public static Regle tirValide() {
        throw new UnsupportedOperationException("TODO S05 Q2 bis");
    }

    /** L'engagement se pose sur l'une des quatre cases centrales, libre (J2, J18). */
    public static Regle engagementAuCentre() {
        throw new UnsupportedOperationException("TODO S05 Q2 bis");
    }

    // ---------- assemblages : le Composite, TODO S05, Q2 bis ----------

    /** Le ET : toutes doivent passer, le premier refus l'emporte et porte son motif. */
    public static Regle toutesLes(Regle... regles) {
        throw new UnsupportedOperationException("TODO S05 Q2 bis");
    }

    /** Le OU : la première acceptation l'emporte, sinon le premier refus est rendu. */
    public static Regle lUneDes(Regle... regles) {
        throw new UnsupportedOperationException("TODO S05 Q2 bis");
    }

    /**
     * Un coup composite passerait l'arbitrage sans que ses parties soient jamais vérifiées.
     * On le déplie : chaque partie est arbitrée dans l'état laissé par la précédente, puis
     * l'état est rendu intact. Possible parce que la partie sait se sauvegarder (Q5 bis).
     * TODO S05, Q5 ter.
     */
    public static Regle enDepliantLesMacros(Regle base) {
        throw new UnsupportedOperationException("TODO S05 Q5 ter");
    }

    // ---------- le jeu de base ----------

    /** L'arbitrage complet du jeu de base. Aujourd'hui : une règle monolithique. */
    public static Regle duJeuDeBase() {
        return Regles::verifierJeuDeBase;
    }

    private static Verdict verifierJeuDeBase(Partie partie, Coup coup) {
        Plateau plateau = partie.plateau();
        return switch (coup) {
            case Coup.Deplacement d -> {
                Piece piece = plateau.pieceEn(d.de()).orElse(null);
                if (piece == null) yield Verdict.refus("aucune pièce en " + d.de());
                if (piece.couleur() != partie.trait()) yield Verdict.refus("ce n'est pas votre pièce");
                if (!plateau.contient(d.vers())) yield Verdict.refus("hors du plateau : " + d.vers());
                if (!plateau.estVide(d.vers())) yield Verdict.refus("case occupée : aucune capture dans ce jeu");
                yield Motifs.de(piece).accessibles(plateau, d.de()).contains(d.vers())
                        ? Verdict.ok() : Verdict.refus("déplacement impossible pour un(e) " + piece.type());
            }
            case Coup.Passe p -> {
                Piece porteuse = plateau.porteuse().orElse(null);
                if (porteuse == null) yield Verdict.refus("le ballon est libre : rien à passer");
                if (porteuse.couleur() != partie.trait()) yield Verdict.refus("le ballon n'est pas à vous");
                Position depart = plateau.positionDe(porteuse).orElseThrow();
                if (!depart.equals(p.porteuse())) yield Verdict.refus("la porteuse n'est pas en " + p.porteuse());
                Piece cible = plateau.pieceEn(p.cible()).orElse(null);
                if (cible == null) yield Verdict.refus("aucune coéquipière en " + p.cible());
                if (cible.couleur() != porteuse.couleur()) yield Verdict.refus("la cible est adverse");
                yield Motifs.de(porteuse).vise(plateau, depart, p.cible())
                        ? Verdict.ok() : Verdict.refus("cible non alignée ou trajectoire obstruée");
            }
            case Coup.Tir t -> {
                Piece porteuse = plateau.porteuse().orElse(null);
                if (porteuse == null) yield Verdict.refus("le ballon est libre : rien à tirer");
                if (porteuse.couleur() != partie.trait()) yield Verdict.refus("le ballon n'est pas à vous");
                if (!plateau.positionDe(porteuse).orElseThrow().equals(t.porteuse())) {
                    yield Verdict.refus("la porteuse n'est pas en " + t.porteuse());
                }
                Motif motif = Motifs.de(porteuse);
                if (motif.directions().isEmpty()) yield Verdict.refus(porteuse.type() + " ne peut pas tirer (elle saute)");
                if (!motif.directions().contains(t.direction())) yield Verdict.refus("direction hors du motif");
                yield t.direction().versLaRangeeDe(porteuse.couleur())
                        ? Verdict.ok() : Verdict.refus("on ne tire pas vers son propre but");
            }
            case Coup.PoserBallon p -> {
                if (plateau.positionDuBallon().isPresent()) yield Verdict.refus("le ballon est déjà en jeu");
                int milieu = plateau.taille() / 2;
                boolean central = (p.ou().colonne() == milieu - 1 || p.ou().colonne() == milieu)
                        && (p.ou().rangee() == milieu - 1 || p.ou().rangee() == milieu);
                if (!central) yield Verdict.refus("engagement hors des cases centrales : " + p.ou());
                yield plateau.estVide(p.ou()) ? Verdict.ok() : Verdict.refus("case centrale occupée : " + p.ou());
            }
            case Coup.Macro m -> Verdict.refus("un coup composite n'est pas arbitré tel quel : S05 Q5 ter");
        };
    }
}
