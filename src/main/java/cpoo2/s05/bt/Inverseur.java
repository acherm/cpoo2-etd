package cpoo2.s05.bt;

/** Un décorateur : même interface que le nœud qu'il enveloppe, il inverse son résultat. */
public record Inverseur(Noeud enfant) implements Noeud {

    @Override public Statut tick(Contexte contexte) {
        throw new UnsupportedOperationException("TODO S05 Q12");
    }
}
