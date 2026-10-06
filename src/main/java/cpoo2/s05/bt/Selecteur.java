package cpoo2.s05.bt;

import java.util.List;

/** Un composite : essaie ses enfants dans l'ordre, réussit au premier qui réussit. L'ordre est la priorité. */
public record Selecteur(List<Noeud> enfants) implements Noeud {

    public Selecteur {
        enfants = List.copyOf(enfants);
    }

    public static Selecteur de(Noeud... enfants) { return new Selecteur(List.of(enfants)); }

    @Override public Statut tick(Contexte contexte) {
        throw new UnsupportedOperationException("TODO S05 Q12");
    }
}
