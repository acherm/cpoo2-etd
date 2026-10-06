package cpoo2.s03.ia;

import java.util.function.Consumer;

import cpoo2.jeu.Coup;
import cpoo2.jeu.Jeu;

/**
 * S03, exercice 2 : un second <b>Décorateur</b> d'IA. Il enveloppe n'importe quelle {@link IA},
 * lui demande son coup, écrit une ligne au journal, puis rend le coup tel quel. La ligne est de
 * la forme {@code "BLEUS : poussée c5-d4"} : le trait, deux-points, le coup.
 *
 * <p>Le journal est un {@code Consumer<String>} ({@code java.util.function}, Java 8) :
 * {@code System.out::println} pour la console, {@code liste::add} dans un test.</p>
 */
public final class IAJournalisee implements IA {

    // TODO S03 Q10 : l'IA enveloppée et le journal

    public IAJournalisee(IA enveloppee, Consumer<String> journal) {
        // TODO S03 Q10
    }

    @Override public Coup choisirCoup(Jeu jeu) {
        throw new UnsupportedOperationException("TODO S03 Q10");
    }
}
