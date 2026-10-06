package cpoo2.s04.observateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.Coup;
import cpoo2.jeu.EcouteurDePartie;

/** S04, Q16 : un abonné qui note tout, « BLEUS : poussée c5-d4 », puis « fin : vainqueur BLEUS » ou « fin : nulle ». */
public final class JournalDesCoups implements EcouteurDePartie {

    private final List<String> lignes = new ArrayList<>();

    public List<String> lignes() { return List.copyOf(lignes); }

    @Override public void coupJoue(Couleur equipe, Coup coup) {
        throw new UnsupportedOperationException("TODO S04 Q16");
    }

    @Override public void partieTerminee(Optional<Couleur> vainqueur) {
        throw new UnsupportedOperationException("TODO S04 Q16");
    }
}
