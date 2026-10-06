package cpoo2.s03.ia;

import java.util.List;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;
import cpoo2.jeu.Plateau;

/**
 * S03, exercice 3 : le <b>Proxy</b> (de protection). Ce qu'on remet à une IA : la même interface
 * que la partie, {@link Jeu}, tout ce qu'il faut pour choisir un coup, et rien pour le jouer.
 * {@code jouer} lève {@code UnsupportedOperationException}, sans toucher à la partie réelle.
 */
public final class JeuEnLectureSeule implements Jeu {

    // TODO S03 Q14 : la partie réelle, dans un champ typé par l'interface

    public JeuEnLectureSeule(Jeu reel) {
        // TODO S03 Q14
    }

    @Override public Couleur trait() {
        throw new UnsupportedOperationException("TODO S03 Q14");
    }

    @Override public Plateau plateau() {
        throw new UnsupportedOperationException("TODO S03 Q14");
    }

    @Override public List<Coup> coupsLegaux() {
        throw new UnsupportedOperationException("TODO S03 Q14");
    }

    @Override public boolean jouer(Coup coup) {
        // TODO S03 Q14 : refuser, et que ça se voie
        return false;
    }
}
