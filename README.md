# CPOO2 — espace de travail étudiant

*Support des séances **S01 à S03** et des exercices courts de **CPOO2** (INSA
Rennes, 4INFO, 2026-2027). Les sujets sont distribués à part ; ici, le code.*

*Clonez, ouvrez dans votre IDE, lancez `mvn test`. **Ça doit être rouge.**
C'est l'énoncé.*

```bash
git clone https://github.com/acherm/cpoo2-etd.git
cd cpoo2-etd
mvn test                                    # rouge : normal
mvn test -Dmaven.test.failure.ignore=true   # rouge, mais le tableau complet s'affiche
mvn test -Dtest=InjectorTest                # une seule suite
```

Java 21. Aucune dépendance en dehors de JUnit 5 et Mockito : le premier
`mvn test` télécharge une quinzaine de mégaoctets, ensuite tout est local.
Pas de Spring, pas de JavaFX, rien à installer d'autre qu'un JDK.

## État de référence

Sur un clone intact — JDK 21, vérifié le 2026-08-25 :

```
Tests run: 63, Failures: 28, Errors: 12   →  40 rouges, 23 verts
```

| Suite | Séance | Tests | Rouges | Ce que les verts veulent dire |
|---|---|---|---|---|
| `MonOptionalTest` | S01 ex. 1 | 9 | 8 | le seul vert passe **à vide** |
| `InjectorTest` | S01 ex. 2 | 12 | 6 | **six verts à vide** — c'est la question Q12 |
| `FooTest` | S01 ex. 3 | 1 | 1 | |
| `ColourCardTest` | S02 ex. 1 | 6 | 4 | deux verts à vide |
| `ExpFactoryTest` | S02 ex. 2 | 10 | **0** | la fabrique marche déjà : c'est un **filet**, pas un énoncé |
| `UndoRedoHistoryTest` | S02 ex. 3 | 14 | 11 | trois verts à vide |
| `CollectionsTest` | S02 ex. 6 | 6 | 5 | |
| `VisiteursTest` | S03 | 5 | 5 | |

**Un test vert ne prouve pas toujours quelque chose.** Vingt-trois de ces
soixante-trois tests passent sur du code qui ne fait rien. Savoir lesquels, et
pourquoi, fait partie du travail (S01 Q12–Q13).

## Carte des exercices

```
src/main/java/cpoo2/
  s01/optionnel/     MonOptional             ← reconstruire Optional
     /injection/     Inject, Injector        ← réflexion, annotations, cycles
     /testabilite/   Foo, RandomGenerator    ← l'instance unique casse le test
  s02/enumeration/   ColourCard              ← fabrique depuis une chaîne
     /fabrique/      ExpFactory, ArithmExp   ← la cascade de if → Commande
     /annuler/       Undoable, UndoRedoHistory ← undo/redo
     /pont/          Client                  ← Pont puis Fabrique abstraite (à créer)
     /monteur/       Arbre, Chene, Pin       ← monteurs fonctionnels
     /fluide/        RobotFactory, Exemples  ← API fluide à états
     /poidsmouche/   List, ArrayList, Collections ← singletonList / emptyList
  s03/arbre/         Noeud, Arbre, Visiteur… ← Visiteur et expression problem
                     SurchargeDemo           ← à exécuter (Q6)
  divers/flux/       Boucles                 ← boucles → Stream
        /journal/    Journalisation          ← à exécuter : le log paresseux
        /paresseux/  Foo                     ← lazy val
        /questions/  Q1                      ← à exécuter : getOrDefault et le NPE
```

Trois classes se **lancent** au lieu de se tester — ce sont des expériences,
leur sortie est la réponse :

```bash
mvn -q compile
java -cp target/classes cpoo2.s03.arbre.SurchargeDemo      # S03 Q6
java -cp target/classes cpoo2.divers.journal.Journalisation # § 8
java -cp target/classes cpoo2.divers.questions.Q1           # § 15
```

## Deux exercices sans suite JUnit, et c'est voulu

- `s02/pont/` : exercice de **conception**. Il n'y a rien à compléter, tout est
  à créer. Votre livrable est le code client, qui ne doit contenir aucun `new`
  de classe concrète.
- `s02/fluide/` : **le compilateur est le test.** Votre API est correcte quand
  les usages légitimes compilent et que les trois usages interdits refusent de
  compiler. Aucun `assert` ne peut vérifier ça.

## Provenance

Ces exercices dérivent du dépôt d'Arnaud Blouin,
[`arnobl/designPattern-INSA`](https://github.com/arnobl/designPattern-INSA)
(`src/main/java/exercises/`), et du cahier de TD CPOO2 2025-2026. Ce qui a
changé ici :

- **plus de Spring Boot ni de JavaFX** — le projet d'origine en hérite pour ses
  exemples de cours, dont les exercices n'ont pas besoin ;
- les paquets sont nommés d'après les **séances** et non par numéro d'exercice
  (la numérotation du dépôt d'origine a déjà bougé une fois, en 2025) ;
- les deux suites que le dépôt d'origine livrait **en commentaire**
  (`OptionalTest`, `TestColourCard`) sont **actives** ;
- deux exercices **supprimés en 2025** sont revenus : le poids-mouche
  (`Collections.singletonList`) et la testabilité de l'instance unique ;
- le squelette du Visiteur est en **français**, pour coller au sujet de S03 ;
  le dépôt d'origine le nomme en anglais (`Node`, `PlusNode`, `VisitorTree`).

Les exemples de patrons commentés en cours restent dans le dépôt d'Arnaud :
`src/main/java/<patron>/`. Pour le typage, voir
[`arnobl/structural-typing-examples`](https://github.com/arnobl/structural-typing-examples).
