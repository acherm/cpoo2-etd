package chessball;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * L'état matériel d'un match : qui est où, et où est le ballon.
 *
 * <p>Le plateau ne connaît <em>aucune</em> règle : il ne sait pas si un déplacement est légal,
 * ni ce qu'est une passe. Il sait poser, déplacer, dire ce qu'il y a sur une case, et donner
 * les cases strictement comprises entre deux cases alignées. Tout le reste est à construire.</p>
 *
 * <p>Invariant du ballon (cahier des charges §5) : <b>posé libre sur une case XOR porté par une
 * pièce</b>. Il est tenu ici par construction — {@code ballonLibre} et {@code porteuse} ne sont
 * jamais non nuls tous les deux.</p>
 */
public final class Plateau {

    private final int taille;
    private final Map<Position, Piece> occupation = new LinkedHashMap<>();
    private Position ballonLibre;   // non nul si le ballon est libre
    private Piece porteuse;     // non nul si le ballon est porté

    public Plateau(int taille) {
        this.taille = taille;
    }

    public int taille() { return taille; }

    public boolean contient(Position c) {
        return c.colonne() >= 0 && c.colonne() < taille && c.rangee() >= 0 && c.rangee() < taille;
    }

    // ---------- pièces ----------

    public Optional<Piece> pieceEn(Position c) {
        return Optional.ofNullable(occupation.get(c));
    }

    public boolean estVide(Position c) {
        return !occupation.containsKey(c);
    }

    public Optional<Position> positionDe(Piece p) {
        return occupation.entrySet().stream()
                .filter(e -> e.getValue() == p)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    /** Pose une pièce sur une case vide du plateau. Si le ballon y était libre, la pièce s'en empare (J5). */
    public void placer(Piece p, Position c) {
        exigeDansLePlateau(c);
        if (occupation.containsKey(c)) {
            throw new IllegalStateException("case déjà occupée : " + c);
        }
        occupation.put(c, p);
        if (c.equals(ballonLibre)) {
            ballonLibre = null;
            porteuse = p;
        }
    }

    /** Déplacement <em>brut</em>, sans contrôle de règle. Le ballon suit sa porteuse (J4, dribble). */
    public void deplacer(Position depart, Position arrivee) {
        Piece p = occupation.get(depart);
        if (p == null) throw new IllegalStateException("aucune pièce en " + depart);
        occupation.remove(depart);
        placer(p, arrivee);
    }

    public List<Piece> piecesDe(Couleur couleur) {
        List<Piece> resultat = new ArrayList<>();
        for (Piece p : occupation.values()) {
            if (p.couleur() == couleur) resultat.add(p);
        }
        return resultat;
    }

    public Map<Position, Piece> occupation() {
        return Collections.unmodifiableMap(occupation);
    }

    // ---------- ballon ----------

    public void poserBallonLibre(Position c) {
        exigeDansLePlateau(c);
        ballonLibre = c;
        porteuse = null;
        pieceEn(c).ifPresent(this::donnerBallon);   // une pièce déjà là s'en empare
    }

    public void donnerBallon(Piece p) {
        ballonLibre = null;
        porteuse = p;
    }

    public Optional<Piece> porteuse() {
        return Optional.ofNullable(porteuse);
    }

    /** La case du ballon, qu'il soit libre ou porté. */
    public Optional<Position> positionDuBallon() {
        return porteuse != null ? positionDe(porteuse) : Optional.ofNullable(ballonLibre);
    }

    public boolean ballonEstLibre() {
        return ballonLibre != null;
    }

    /** Le ballon quitte le terrain (but marqué) : il n'est plus nulle part, en attendant l'engagement. */
    public void retirerLeBallon() {
        ballonLibre = null;
        porteuse = null;
    }

    /**
     * Recopie l'état de {@code source} dans <em>ce</em> plateau. L'identité de l'objet est
     * préservée : qui détient une référence sur ce plateau la garde valide — c'est ce qui
     * distingue une restauration d'un remplacement.
     */
    public void restaurerDepuis(Plateau source) {
        if (source.taille != taille) {
            throw new IllegalArgumentException("plateaux de tailles différentes");
        }
        occupation.clear();
        occupation.putAll(source.occupation);
        ballonLibre = source.ballonLibre;
        porteuse = source.porteuse;
    }

    /** Copie superficielle : les {@link Piece} sont immuables, les partager ne coûte rien. */
    public Plateau copie() {
        Plateau clone = new Plateau(taille);
        clone.occupation.putAll(occupation);
        clone.ballonLibre = ballonLibre;
        clone.porteuse = porteuse;
        return clone;
    }

    // ---------- géométrie ----------

    /** Vrai si les deux cases sont sur une même ligne, colonne ou diagonale (et distinctes). */
    public static boolean alignees(Position a, Position b) {
        int dc = Math.abs(a.colonne() - b.colonne());
        int dl = Math.abs(a.rangee() - b.rangee());
        return (dc != 0 || dl != 0) && (dc == 0 || dl == 0 || dc == dl);
    }

    /** Cases <em>strictement</em> entre deux cases alignées, dans l'ordre du parcours. */
    public static List<Position> entreDeux(Position a, Position b) {
        if (!alignees(a, b)) throw new IllegalArgumentException(a + " et " + b + " ne sont pas alignées");
        int pasCol = Integer.signum(b.colonne() - a.colonne());
        int pasLig = Integer.signum(b.rangee() - a.rangee());
        List<Position> chemin = new ArrayList<>();
        Position courante = a.decalee(pasCol, pasLig);
        while (!courante.equals(b)) {
            chemin.add(courante);
            courante = courante.decalee(pasCol, pasLig);
        }
        return chemin;
    }

    // ---------- affichage ----------

    public String enTexte() {
        StringBuilder sb = new StringBuilder();
        for (int lig = taille - 1; lig >= 0; lig--) {
            sb.append(lig + 1).append(' ');
            for (int col = 0; col < taille; col++) {
                Position c = new Position(col, lig);
                Piece p = occupation.get(c);
                char lettre;
                if (p == null) {
                    lettre = c.equals(ballonLibre) ? 'o' : '.';
                } else {
                    lettre = p.couleur() == Couleur.BLEUS
                            ? p.type().lettre()
                            : Character.toLowerCase(p.type().lettre());
                }
                char marque = (p != null && p == porteuse) ? '*' : ' ';
                sb.append(' ').append(lettre).append(marque);
            }
            sb.append('\n');
        }
        sb.append("  ");
        for (int col = 0; col < taille; col++) sb.append(' ').append((char) ('a' + col)).append(' ');
        return sb.append('\n').toString();
    }

    private void exigeDansLePlateau(Position c) {
        if (!contient(c)) throw new IllegalArgumentException("hors plateau : " + c);
    }
}
