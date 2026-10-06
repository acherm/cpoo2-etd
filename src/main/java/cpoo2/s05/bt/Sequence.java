package cpoo2.s05.bt;

import java.util.List;

/** Un composite : réussit si tous ses enfants réussissent, dans l'ordre. S'arrête au premier échec. */
public record Sequence(List<Noeud> enfants) implements Noeud {

    public Sequence {
        enfants = List.copyOf(enfants);
    }

    public static Sequence de(Noeud... enfants) { return new Sequence(List.of(enfants)); }

    @Override public Statut tick(Contexte contexte) {
        throw new UnsupportedOperationException("TODO S05 Q12");
    }
}
