package cpoo2.s05.bt;

import java.util.Optional;
import java.util.function.Function;

import cpoo2.jeu.Coup;

/** Une feuille qui agit : elle cherche un coup de sa sorte. SUCCES si elle en trouve un, et elle le choisit. */
public record Action(String nom, Function<Contexte, Optional<Coup>> choix) implements Noeud {

    @Override public Statut tick(Contexte contexte) {
        Optional<Coup> coup = choix.apply(contexte);
        coup.ifPresent(contexte::choisir);
        return coup.isPresent() ? Statut.SUCCES : Statut.ECHEC;
    }

    @Override public String toString() { return nom; }
}
