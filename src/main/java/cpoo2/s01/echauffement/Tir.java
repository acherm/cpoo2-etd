package cpoo2.s01.echauffement;

/** Un tir depuis la porteuse, avec une force (exercice 2.1). */
public record Tir(Position porteuse, int force) implements Coup { }
