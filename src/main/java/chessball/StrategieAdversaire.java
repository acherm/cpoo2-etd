package chessball;

/**
 * Ce que sait faire un acteur du match, humain ou machine : choisir un coup. Il reçoit une
 * <b>copie</b> du plateau et le trait, jamais la partie : il ne peut donc pas tricher.
 *
 * <p>C'est la <b>Stratégie</b> de la séance 6 : la partie (ou le tournoi) détient un adversaire
 * et lui délègue le choix, sans savoir lequel.</p>
 */
@FunctionalInterface
public interface StrategieAdversaire {

    Coup choisirCoup(Plateau plateau, Couleur trait);
}
