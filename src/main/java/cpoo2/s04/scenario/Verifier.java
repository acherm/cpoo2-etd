package cpoo2.s04.scenario;

/** {@code vérifier ballon en e3 et trait aux bleus} : une condition, que l'exécution évalue. */
public record Verifier(Condition condition) implements Instruction {

    @Override public void accept(VisiteurDeScenario visiteur) {
        throw new UnsupportedOperationException("TODO S04 Q6");
    }

    @Override public String toString() { return "vérifier " + condition; }
}
