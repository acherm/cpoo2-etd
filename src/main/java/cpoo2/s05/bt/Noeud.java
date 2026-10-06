package cpoo2.s05.bt;

/** Un nœud de behaviour tree : à chaque tick, il réussit ou il échoue. */
public interface Noeud {
    Statut tick(Contexte contexte);
}
