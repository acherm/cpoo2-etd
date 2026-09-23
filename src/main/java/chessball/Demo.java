package chessball;

import java.util.List;

/**
 * Point d'entrée du noyau : {@code javac chessball/*.java && java chessball.Demo}.
 * Vérifie que tout compile et montre ce que le noyau sait déjà faire, et ce qu'il ne sait pas encore.
 */
public final class Demo {

    public static void main(String[] args) {
        Plateau plateau = Placements.standard();
        System.out.println("Placement initial (majuscule = Bleus, minuscule = Rouges) :");
        System.out.println(plateau.enTexte());

        // Engagement : le ballon est posé libre sur une case centrale (J2).
        plateau.poserBallonLibre(Position.of("d4"));
        Verif.que(plateau.ballonEstLibre(), "le ballon posé sur une case vide est libre");

        // Le cavalier bleu saute en d4 et s'empare du ballon (J5).
        plateau.deplacer(Position.of("e2"), Position.of("d4"));
        Verif.que(!plateau.ballonEstLibre(), "une pièce arrivée sur le ballon s'en empare");
        Verif.egal("bC", plateau.porteuse().orElseThrow().nom(), "le cavalier est porteur");
        Verif.egal(Position.of("d4"), plateau.positionDuBallon().orElseThrow(), "le ballon suit sa porteuse");

        System.out.println("Après l'engagement et le premier tour (* = porteuse du ballon) :");
        System.out.println(plateau.enTexte());

        // Géométrie : ce que le noyau sait dire, et qui servira aux passes et aux tirs.
        Verif.que(Plateau.alignees(Position.of("d4"), Position.of("d8")), "d4 et d8 sont alignées");
        Verif.que(!Plateau.alignees(Position.of("d4"), Position.of("e6")), "d4 et e6 ne sont pas alignées");
        Verif.egal(List.of(Position.of("d5"), Position.of("d6"), Position.of("d7")),
                Plateau.entreDeux(Position.of("d4"), Position.of("d8")), "cases entre d4 et d8");

        Verif.bilan();
        System.out.println("""
                Le noyau ne sait rien d'autre : ni motif de déplacement, ni passe, ni tir,
                ni tour de jeu, ni score. La couche « jeu » (Partie, Regles, Coup) commence à le savoir :
                c'est elle que les séances 2 à 8 font grandir, un patron à la fois.""");
    }
}
