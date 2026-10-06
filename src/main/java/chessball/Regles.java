package chessball;

import java.util.List;

/**
 * Les règles élémentaires du jeu et les façons de les assembler. <b>Une règle composée est
 * une règle</b> : {@link #toutesLes} et {@link #lUneDes} rendent une {@link Regle} qu'on peut
 * imbriquer, tracer, remplacer. C'est le Composite de S00 (les Et et Ou de la légalité), écrit
 * sur l'application.
 */
public final class Regles {

    private Regles() {}

    // ---------- règles élémentaires ----------

    /** On ne joue pas la pièce de l'adversaire, on ne passe ni ne tire sans le ballon. */
    public static Regle trait() {
        return (partie, coup) -> switch (coup) {
            case Coup.Deplacement d -> aQuiEst(partie, d.de()) == partie.trait()
                    ? Verdict.ok() : Verdict.refus("ce n'est pas votre pièce");
            case Coup.Passe p -> aQuiEst(partie, p.porteuse()) == partie.trait()
                    ? Verdict.ok() : Verdict.refus("ce n'est pas votre pièce");
            case Coup.Tir t -> partie.plateau().porteuse()
                    .map(porteuse -> porteuse.couleur() == partie.trait()
                            ? Verdict.ok() : Verdict.refus("le ballon n'est pas à vous"))
                    .orElse(Verdict.refus("personne ne porte le ballon"));
            default -> Verdict.ok();
        };
    }

    /** Le déplacement suit le motif de la pièce et la case d'arrivée est libre (J6 : aucune capture). */
    public static Regle motifRespecte() {
        return (partie, coup) -> {
            if (!(coup instanceof Coup.Deplacement d)) return Verdict.ok();
            Plateau plateau = partie.plateau();
            Piece piece = plateau.pieceEn(d.de()).orElse(null);
            if (piece == null) return Verdict.refus("aucune pièce en " + d.de());
            if (!plateau.contient(d.vers())) return Verdict.refus("hors du plateau : " + d.vers());
            if (!plateau.estVide(d.vers())) return Verdict.refus("case occupée : aucune capture dans ce jeu");
            return Motifs.de(piece).accessibles(plateau, d.de()).contains(d.vers())
                    ? Verdict.ok() : Verdict.refus("déplacement impossible pour un(e) " + piece.type());
        };
    }

    /** Passe : cible coéquipière, alignée sur une trajectoire de la porteuse, chemin libre (J7). */
    public static Regle passeValide() {
        return (partie, coup) -> {
            if (!(coup instanceof Coup.Passe p)) return Verdict.ok();
            Plateau plateau = partie.plateau();
            Piece porteuse = plateau.porteuse().orElse(null);
            if (porteuse == null) return Verdict.refus("le ballon est libre : rien à passer");
            Position depart = plateau.positionDe(porteuse).orElseThrow();
            if (!depart.equals(p.porteuse())) return Verdict.refus("la porteuse n'est pas en " + p.porteuse());
            Piece cible = plateau.pieceEn(p.cible()).orElse(null);
            if (cible == null) return Verdict.refus("aucune coéquipière en " + p.cible());
            if (cible.couleur() != porteuse.couleur()) return Verdict.refus("la cible est adverse");
            return Motifs.de(porteuse).vise(plateau, depart, p.cible())
                    ? Verdict.ok() : Verdict.refus("cible non alignée ou trajectoire obstruée");
        };
    }

    /** Tir : la direction appartient au motif de la porteuse et progresse vers la ligne adverse. */
    public static Regle tirValide() {
        return (partie, coup) -> {
            if (!(coup instanceof Coup.Tir t)) return Verdict.ok();
            Piece porteuse = partie.plateau().porteuse().orElse(null);
            if (porteuse == null) return Verdict.refus("le ballon est libre : rien à tirer");
            if (!partie.plateau().positionDe(porteuse).orElseThrow().equals(t.porteuse())) {
                return Verdict.refus("la porteuse n'est pas en " + t.porteuse());
            }
            Motif motif = Motifs.de(porteuse);
            if (motif.directions().isEmpty()) return Verdict.refus(porteuse.type() + " ne peut pas tirer (elle saute)");
            if (!motif.directions().contains(t.direction())) return Verdict.refus("direction hors du motif");
            return t.direction().versLaRangeeDe(porteuse.couleur())
                    ? Verdict.ok() : Verdict.refus("on ne tire pas vers son propre but");
        };
    }

    /** L'engagement se pose sur l'une des quatre cases centrales, libre (J2, J18). */
    public static Regle engagementAuCentre() {
        return (partie, coup) -> {
            if (!(coup instanceof Coup.PoserBallon p)) return Verdict.ok();
            if (partie.plateau().positionDuBallon().isPresent()) return Verdict.refus("le ballon est déjà en jeu");
            int milieu = partie.plateau().taille() / 2;
            boolean central = (p.ou().colonne() == milieu - 1 || p.ou().colonne() == milieu)
                    && (p.ou().rangee() == milieu - 1 || p.ou().rangee() == milieu);
            if (!central) return Verdict.refus("engagement hors des cases centrales : " + p.ou());
            return partie.plateau().estVide(p.ou())
                    ? Verdict.ok() : Verdict.refus("case centrale occupée : " + p.ou());
        };
    }

    // ---------- assemblages : le Composite ----------

    /** Le ET : toutes doivent passer, le premier refus l'emporte et porte son motif. */
    public static Regle toutesLes(Regle... regles) {
        List<Regle> liste = List.of(regles);
        return (partie, coup) -> {
            for (Regle r : liste) {
                Verdict v = r.verifier(partie, coup);
                if (!v.accepte()) return v;
            }
            return Verdict.ok();
        };
    }

    /** Le OU : la première acceptation l'emporte, sinon le premier refus est rendu. */
    public static Regle lUneDes(Regle... regles) {
        List<Regle> liste = List.of(regles);
        return (partie, coup) -> {
            Verdict premierRefus = null;
            for (Regle r : liste) {
                Verdict v = r.verifier(partie, coup);
                if (v.accepte()) return v;
                if (premierRefus == null) premierRefus = v;
            }
            return premierRefus == null ? Verdict.refus("aucune règle") : premierRefus;
        };
    }

    /**
     * Un coup composite passerait l'arbitrage sans que ses parties soient jamais vérifiées.
     * On le déplie : chaque partie est arbitrée dans l'état laissé par la précédente, puis
     * l'état est rendu intact. Possible parce que la partie sait se sauvegarder (Q5 bis).
     */
    public static Regle enDepliantLesMacros(Regle base) {
        return (partie, coup) -> {
            if (!(coup instanceof Coup.Macro macro)) return base.verifier(partie, coup);
            if (macro.coups().isEmpty()) return Verdict.refus("macro vide : " + macro.nom());
            Partie.Sauvegarde avant = partie.capturer();
            try {
                for (Coup morceau : macro.coups()) {
                    Verdict v = base.verifier(partie, morceau);
                    if (!v.accepte()) return v;
                    morceau.executer(partie);
                }
                return Verdict.ok();
            } finally {
                partie.restaurer(avant);
            }
        };
    }

    /** L'arbitrage complet du jeu de base : une composition, plus une cascade de conditions. */
    public static Regle duJeuDeBase() {
        return enDepliantLesMacros(
                toutesLes(trait(), motifRespecte(), passeValide(), tirValide(), engagementAuCentre()));
    }

    private static Couleur aQuiEst(Partie partie, Position c) {
        return partie.plateau().pieceEn(c).map(Piece::couleur).orElse(null);
    }
}
