package cpoo2.s05.bt;

import java.util.Optional;
import java.util.Random;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;

/**
 * Ce qu'un arbre reçoit à chaque tick : la partie telle qu'on la voit, et ce qui appartient à
 * <b>cette</b> IA (son hasard). Les actions y déposent le coup choisi.
 */
public final class Contexte {

    private final Jeu jeu;
    private final Random hasard;
    private Coup choisi;

    public Contexte(Jeu jeu, Random hasard) {
        this.jeu = jeu;
        this.hasard = hasard;
    }

    public Jeu jeu() { return jeu; }

    /** L'équipe pour laquelle on décide : celle qui a le trait. */
    public Couleur equipe() { return jeu.trait(); }

    public Random hasard() { return hasard; }

    public Optional<Coup> choisi() { return Optional.ofNullable(choisi); }

    public void choisir(Coup coup) { this.choisi = coup; }
}
