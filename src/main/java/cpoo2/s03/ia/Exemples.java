package cpoo2.s03.ia;

import cpoo2.jeu.Couleur;
import cpoo2.jeu.PartieDEssai;
import cpoo2.jeu.Plateau;

/**
 * Les parties d'essai de la séance. Leurs coups légaux ont été calculés par un arbitre complet
 * du ChessBall de la boîte, puis recopiés ici.
 */
public final class Exemples {

    private Exemples() {}

    /**
     * Le mur. Bleus au trait. Le défenseur bleu de f6 semble à deux pas de la case de poussée d6,
     * mais le mur rouge e5-e6 l'en sépare. L'attaquant bleu de a2 est plus loin, et rien ne
     * l'arrête.
     */
    public static PartieDEssai leMur() {
        return PartieDEssai.de(Plateau.depuis(
                        "....dD.",   // rang 6
                        "...od..",   // rang 5
                        ".......",   // rang 4
                        ".......",   // rang 3
                        "A......",   // rang 2
                        "......."),  // rang 1
                Couleur.BLEUS,
                "déplacement f6-f5", "déplacement f6-g6", "tacle f6-e6", "déplacement f6-g5", "tacle f6-e5",
                "déplacement a2-a3", "déplacement a2-a1", "déplacement a2-b2", "déplacement a2-b3", "déplacement a2-b1");
    }

    /**
     * Le ballon devant son propre but. Bleus au trait, ballon en d5, juste devant leur ligne de
     * but (le rang 6). L'attaquant bleu de d4 peut pousser au nord : le ballon part en d6, et les
     * Rouges ont gagné : un but contre son camp compte pour l'adversaire.
     */
    public static PartieDEssai leBallonDevantSonBut() {
        return PartieDEssai.de(Plateau.depuis(
                        ".D...D.",   // rang 6
                        "...o...",   // rang 5
                        "..aA...",   // rang 4
                        "...D.a.",   // rang 3
                        "..A....",   // rang 2
                        ".d.d.d."),  // rang 1
                Couleur.BLEUS,
                "déplacement b6-b5", "déplacement b6-c6", "déplacement b6-a6", "déplacement b6-c5", "déplacement b6-a5",
                "déplacement f6-f5", "déplacement f6-g6", "déplacement f6-e6", "déplacement f6-g5", "déplacement f6-e5",
                "poussée d4-d5", "saut d4-d6", "saut d4-d2", "déplacement d4-e4", "saut d4-b4", "déplacement d4-e5",
                "déplacement d4-c5", "déplacement d4-e3", "déplacement d4-c3",
                "déplacement d3-d2", "déplacement d3-e3", "déplacement d3-c3", "déplacement d3-e4", "tacle d3-c4",
                "déplacement d3-e2",
                "déplacement c2-c3", "déplacement c2-c1", "déplacement c2-d2", "déplacement c2-b2", "saut c2-e4",
                "déplacement c2-b3");
    }
}
