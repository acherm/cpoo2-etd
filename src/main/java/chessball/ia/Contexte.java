package chessball.ia;

import chessball.Couleur;
import chessball.Partie;

import java.util.HashMap;
import java.util.Map;

/** Ce que voit un nœud à chaque tick : la partie, le camp qui réfléchit, et un tableau noir partagé. */
public record Contexte(Partie partie, Couleur camp, Map<String, Object> tableauNoir) {

    public Contexte(Partie partie, Couleur camp) {
        this(partie, camp, new HashMap<>());
    }

    public void noter(String cle, Object valeur) { tableauNoir.put(cle, valeur); }

    public <T> T lire(String cle, Class<T> type) { return type.cast(tableauNoir.get(cle)); }
}
