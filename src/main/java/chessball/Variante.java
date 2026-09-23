package chessball;

import java.util.Random;

/**
 * Une variante ne fournit pas un objet : elle fournit une <b>famille</b> d'objets qui vont
 * ensemble. Un plateau 10×10 avec le placement du 8×8 et une IA réglée pour le 8×8, c'est un
 * match incohérent, et aucun type ne l'empêche. La <b>Fabrique abstraite</b> de la séance 6 :
 * une méthode de création par produit, une fabrique concrète par famille, et le client reçoit
 * la famille une fois.
 */
public abstract class Variante {

    public abstract String nom();

    /** Le terrain et son placement initial. */
    protected abstract Plateau creerPlateau();

    /** L'arbitrage de cette variante. */
    protected abstract Regle creerRegle();

    /** L'IA réglée pour ce terrain. */
    public abstract StrategieAdversaire creerAdversaire(Random random);

    /** Le montage est le même pour toutes les variantes : seuls les produits changent. */
    public final Partie nouvellePartie(Couleur engageur) {
        // TODO S06 Q14 : le montage, identique pour toutes les variantes, à partir des trois produits
        throw new UnsupportedOperationException("TODO S06 Q14");
    }

    @Override public String toString() { return nom(); }
}
