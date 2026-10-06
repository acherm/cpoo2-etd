package cpoo2.s04.scenario;

/**
 * Une opération sur les instructions d'un scénario, une méthode par sorte d'instruction.
 *
 * <p>Les cinq méthodes ont des <b>noms distincts</b>. Elles pourraient toutes s'appeler
 * {@code visiter} : c'est l'objet de la Q7, et {@link SurchargeDemo} vous laisse en faire
 * l'expérience.</p>
 */
public interface VisiteurDeScenario {
    void visiterDeplacer(Deplacer d);
    void visiterPousser(Pousser p);
    void visiterTacler(Tacler t);
    void visiterSauter(Sauter s);
    void visiterVerifier(Verifier v);
}
