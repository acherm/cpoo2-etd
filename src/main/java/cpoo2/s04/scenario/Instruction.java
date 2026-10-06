package cpoo2.s04.scenario;

/**
 * Une ligne d'un scénario : un coup joué par une équipe, ou une vérification. S04, exercices 2 et
 * 3 : le <b>Visiteur</b>. Les opérations sur un scénario (l'afficher, le compter, le jouer) ne
 * vivent pas dans les instructions, mais dans des visiteurs.
 */
public sealed interface Instruction permits Deplacer, Pousser, Tacler, Sauter, Verifier {

    /** Q6 : une ligne, et une seule, dans chaque classe concrète. */
    void accept(VisiteurDeScenario visiteur);
}
