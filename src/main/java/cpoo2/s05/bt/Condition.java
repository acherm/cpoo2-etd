package cpoo2.s05.bt;

import java.util.function.Predicate;

/** Une feuille qui teste, sans rien changer : SUCCES si le test est vrai. */
public record Condition(String nom, Predicate<Contexte> test) implements Noeud {

    @Override public Statut tick(Contexte contexte) {
        return test.test(contexte) ? Statut.SUCCES : Statut.ECHEC;
    }

    @Override public String toString() { return nom + " ?"; }
}
