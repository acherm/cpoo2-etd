package chessball.ia;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Un nœud de behaviour tree. Les composites ({@link Sequence}, {@link Selecteur}) tiennent des
 * nœuds et sont des nœuds : le Composite, pour la seconde fois de la séance. Les nœuds à un
 * enfant ({@link Inverseur}, {@link Repeter}) modifient le statut de l'enfant : des Décorateurs.
 * Les feuilles portent une fonction.
 *
 * <p>TODO S05, Q10 : les six {@code tick}. {@code BehaviourTreeTest} est l'oracle.</p>
 */
public sealed interface Noeud permits Noeud.Condition, Noeud.Action, Noeud.Sequence,
        Noeud.Selecteur, Noeud.Inverseur, Noeud.Repeter {

    Statut tick(Contexte ctx);

    /** Une feuille qui lit l'état, sans effet : SUCCES ou ECHEC selon le prédicat. */
    record Condition(String nom, Predicate<Contexte> test) implements Noeud {
        @Override public Statut tick(Contexte ctx) {
            throw new UnsupportedOperationException("TODO S05 Q10");
        }
    }

    /** Une feuille qui agit, et dit si elle a réussi. */
    record Action(String nom, Function<Contexte, Statut> effet) implements Noeud {
        @Override public Statut tick(Contexte ctx) {
            throw new UnsupportedOperationException("TODO S05 Q10");
        }
    }

    /** Le ET : les enfants dans l'ordre, on s'arrête au premier qui ne réussit pas. */
    record Sequence(List<Noeud> enfants) implements Noeud {
        public Sequence(Noeud... enfants) { this(List.of(enfants)); }
        @Override public Statut tick(Contexte ctx) {
            throw new UnsupportedOperationException("TODO S05 Q10");
        }
    }

    /** Le OU (fallback) : les enfants dans l'ordre, on s'arrête au premier qui n'échoue pas. */
    record Selecteur(List<Noeud> enfants) implements Noeud {
        public Selecteur(Noeud... enfants) { this(List.of(enfants)); }
        @Override public Statut tick(Contexte ctx) {
            throw new UnsupportedOperationException("TODO S05 Q10");
        }
    }

    /** Un enfant, le statut inversé. {@code EN_COURS} traverse tel quel. */
    record Inverseur(Noeud enfant) implements Noeud {
        @Override public Statut tick(Contexte ctx) {
            throw new UnsupportedOperationException("TODO S05 Q10");
        }
    }

    /** Un enfant, répété jusqu'à {@code fois} succès. Le premier statut qui n'est pas un succès remonte. */
    record Repeter(Noeud enfant, int fois) implements Noeud {
        @Override public Statut tick(Contexte ctx) {
            throw new UnsupportedOperationException("TODO S05 Q10");
        }
    }
}
