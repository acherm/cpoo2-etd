# CPOO2 — espace de travail étudiant

*Support des séances de **CPOO2** (INSA Rennes, 4INFO, 2026-2027) et des
exercices courts. Les sujets sont distribués à part ; ici, le code.*

**Deux familles de paquets.** `cpoo2/` : les exercices académiques des séances
1 à 3 (Optional, injecteur, fabriques, Pont, Visiteur). `chessball/` :
**l'application ChessBall**, qui grandit d'une séance à l'autre, un patron à la
fois, et qui est le livrable du ProjetCPOO. Lancez-la telle quelle :

```bash
mvn -q compile
java -cp target/classes chessball.Demo
```

*Clonez, ouvrez dans votre IDE, lancez `mvn test`. **Ça doit être rouge.**
C'est l'énoncé.*

```bash
git clone https://github.com/acherm/cpoo2-etd.git
cd cpoo2-etd
# rouge : normal
mvn test
# rouge, mais le tableau complet s'affiche
mvn test -Dmaven.test.failure.ignore=true
# une seule suite
mvn test -Dtest=InjectorTest
# tout un paquet, ici la partie 0 de S01 (les guillemets protègent l'étoile)
mvn test -Dtest='**/echauffement/*'
```

Java 21. Aucune dépendance en dehors de JUnit 5 et Mockito (jqwik, cité en S09, est facultatif) : le premier
`mvn test` télécharge une quinzaine de mégaoctets, ensuite tout est local.
Pas de Spring, pas de JavaFX, rien à installer d'autre qu'un JDK.

## État de référence

Sur un clone intact, JDK 21, vérifié le 2026-09-23 au soir :

```
Tests run: 218, Failures: 50, Errors: 117   →  167 rouges, 51 verts
```

| Suite | Séance | Tests | Rouges | Ce que les verts veulent dire |
|---|---|---|---|---|
| `PositionTest`, `EquipeTest`, `CoupsTest`, `ReglesTest` | S01 partie 0 | 21 | 18 | trois verts à vide : `toString` et `equals` engendrés par `record`, et `peek` (question 3.2) |
| `MonOptionalTest` | S01 ex. 1 | 9 | 8 | le seul vert passe **à vide** |
| `InjectorTest` | S01 ex. 2 | 12 | 6 | **six verts à vide**, c'est la question Q12 |
| `FooTest` | S02 ex. 1 (Singleton et Instance unique) | 1 | 1 | |
| `ColourCardTest` | exercice court C6 | 6 | 4 | deux verts à vide |
| `ExpFactoryTest` | S02 ex. 2 | 10 | **0** | la fabrique marche déjà : c'est un **filet**, pas un énoncé |
| `OperateurTest` | S02 ex. 2 | 11 | 10 | un vert à vide (`de` rend vide pour tout) |
| `RappelsTest` | S02 ex. 3 | 5 | 4 | un vert à vide (la boîte d'envoi du stub est vide) |
| `NotificationTest` | S02 ex. 4 | 5 | 5 | le cinquième test lit la structure par réflexion |
| `chessball.AdaptateurTest` | S03 ex. 1 | 6 | 5 | le test structurel passe déjà : le squelette détient la bibliothèque, il ne traduit rien |
| `chessball.DecorateurTest` | S03 ex. 2 | 5 | 4 | idem : le champ typé `Jeu` est fourni, la délégation non |
| `chessball.ProxyTest` | S03 ex. 3 | 4 | 4 | |
| `VisiteursTest` | S04 ex. 3 | 5 | 5 | |
| `InterpreteurTest` | S04 ex. 1 (Q3 bis) | 2 | 2 | `evaluer()` dans les nœuds, avant le Visiteur |
| `chessball.ObservateurTest` | S04 ex. 5 | 5 | 5 | tout coup tenté est notifié avec son verdict, c'est l'abonné qui trie |
| `chessball.PoidsMoucheTest` | S05 Q1 bis | 4 | 2 | deux verts à vide |
| `chessball.CompositeReglesTest` | S05 Q2 bis | 5 | 5 | |
| `chessball.CommandeTest` | S05 Q4 bis | 4 | 3 | `arbitrer` est fourni |
| `chessball.MementoTest` | S05 Q5 bis | 4 | 3 | la classe vide est déjà opaque |
| `chessball.MacroTest` | S05 Q5 ter | 3 | 2 | |
| `chessball.ia.BehaviourTreeTest` | S05 Q10 | 5 | 5 | |
| `chessball.ia.AdversaireBTTest` | S05 Q12 | 3 | 3 | |
| `cpoo2.s05.ecs.MondeTest` | TD5 partie C (l'ECS, hors application) | 3 | 3 | |
| `UndoRedoHistoryTest`, `CollectionsTest` | compléments hors séance de S05 | 20 | 16 | trois verts à vide |
| `chessball.StrategieTest` | S06 Q8 | 5 | 4 | un test structurel passe à vide (le tournoi ne nomme aucune stratégie concrète) |
| `chessball.PlugInTest` | S06 Q9 | 7 | 7 | |
| `chessball.PatronDeMethodeTest` | S06 Q9 bis | 5 | 4 | le test structurel (`final`, trous protégés) passe à vide |
| `chessball.InjectionTest` | S06 Q9 ter | 4 | 3 | le test structurel (aucun `Random` dans l'arbitre) passe à vide |
| `chessball.FabriqueAbstraiteTest` | S06 Q14 | 4 | 3 | le test structurel (`nouvellePartie` final) passe à vide |
| `chessball.LigneDeProduitsTest` | S06 Q17 (facultative) | 4 | 4 | paramétré : quatre configurations de l'échantillon jouent un match |
| `chessball.ConfigurationTest` | S07 ex. 8 | 6 | 4 | deux verts à vide : les tests de structure |
| `chessball.EtatPartieDTOTest` | S07 ex. 9 | 6 | 5 | un vert à vide : les types des composants |
| `chessball.GenerateurDeCoupsTest`, `ProprietesDuJeuTest`, `MatchTest` | S09, fournis | 11 | 0 | le générateur, cinq propriétés du jeu et le match : ils passent, c'est le filet de l'application |
| `chessball.AdversaireAsynchroneTest` | S09 Q11 bis | 5 | 5 | |
| `chessball.FluxDeCoupsTest` | S09 Q11 ter | 3 | 3 | le troisième exige l'Observateur de S04 |

**Un test vert ne prouve pas toujours quelque chose.** Une partie de ces
cinquante et un verts passent sur du code qui ne fait rien. Savoir lesquels,
et pourquoi, fait partie du travail (S01 Q12–Q13).

## Carte des exercices

```
src/main/java/cpoo2/
  s01/echauffement/  Position, Equipe, Coup…, Coups, Regles, Variance
                                             ← partie 0 : record, sealed, lambdas, variance
     /optionnel/     MonOptional             ← reconstruire Optional
     /injection/     Inject, Injector        ← réflexion, annotations, cycles
     /testabilite/   Foo, RandomGenerator    ← S02 ex. 1 : l'instance unique casse le test
  s02/fabrique/      ExpFactory, Operateur   ← ex. 2 : la cascade de if → un domaine fermé
     /pont/          api, impl, service/Rappels ← ex. 3 : API et implémentation, puis Fabrique abstraite
     /pont/notification/ Notification, Canal… ← ex. 4 : le Pont, notifications × canaux
     /enumeration/   ColourCard              ← exercice court C6 : fabrique depuis une chaîne
     /annuler/       Undoable, UndoRedoHistory ← rattaché à S05 (Commande et Memento) : undo/redo
     /monteur/       Arbre, Chene, Pin       ← rattaché à S07 (Monteur) : monteurs fonctionnels
     /fluide/        RobotFactory, Exemples  ← rattaché à S07 : API fluide à états
     /poidsmouche/   List, ArrayList, Collections ← rattaché à S05 (Poids-mouche) : singletonList / emptyList
  s04/arbre/         Noeud, Arbre, Visiteur… ← Visiteur et expression problem
                     SurchargeDemo           ← à exécuter (Q6)
  divers/flux/       Boucles                 ← boucles → Stream
        /journal/    Journalisation          ← à exécuter : le log paresseux
        /paresseux/  Foo                     ← lazy val
        /questions/  Q1                      ← à exécuter : getOrDefault et le NPE

src/main/java/chessball/                     ← L'APPLICATION, un patron par séance
  Position, Couleur, Direction, TypePiece, Piece, Placements, Plateau, Verif, Demo
                                             ← le noyau (aligné sur le moteur de CPOO1) : fourni, ne se touche pas
  Motif, MotifGlissant, MotifSauteur, MotifCompose, MotifNul, Motifs
                                             ← les déplacements (fournis ; Motifs devient un Poids-mouche en S05)
  Coup, Verdict, Regle, Regles, Jeu, Partie, Journal, EcouteurDePartie
                                             ← la couche jeu (fournie ; règles composées et coups objets en S05)
  externe/ChessAttackEngine, MotifExterne    ← S03 ex. 1 : l'Adaptateur
  JeuTrace                                   ← S03 ex. 2 : le Décorateur
  VueSpectateur                              ← S03 ex. 3 : le Proxy
  Partie (abonnés), JournalDesCoups, CompteurDeCoups
                                             ← S04 ex. 5 : l'Observateur
  Motifs (partagé), Regles (élémentaires, toutesLes, lUneDes), Coup.executer, Partie.rejouer,
  Partie.Sauvegarde, Historique, Coup.Macro  ← S05 : Poids-mouche, Composite, Commande, Memento
  ia/Statut, Contexte, Noeud, AdversaireBT   ← S05 : l'attaquant behaviour tree
  StrategieAdversaire, CoupsPossibles (fournis), AdversaireAleatoire, AdversaireHeuristique, Tournoi,
  ChargeurDAdversaire, TirageAuSort, Arbitre, ArbitreStandard, ArbitreEnUneMiTemps,
  Variante, VarianteStandard, VarianteGrandTerrain
                                             ← S06 : Stratégie, plug-in, Injection, Patron de méthode, Fabrique abstraite
  Configuration, EtatPartieDTO               ← S07 : le Monteur d'une partie, l'état exporté
  GenerateurDeCoups, Adversaires, AdversaireLent, Main (fournis), AdversaireAsynchrone, FluxDeCoups
                                             ← S09 : Objet actif, Programmation réactive, et le match
src/main/java/cpoo2/s05/ecs/  Monde         ← TD5 partie C : l'ECS en contrepoint, hors application
```

## L'application, séance par séance

| Séance | Ce que vous ajoutez à `chessball/` | Patron |
|---|---|---|
| S03 | `MotifExterne`, `JeuTrace`, `VueSpectateur` | Adaptateur, Décorateur, Proxy |
| S04 | les abonnés de `Partie`, `JournalDesCoups`, `CompteurDeCoups` | Observateur |
| S05 | `Motifs` partagé (Q1 bis), `Regles` en règles élémentaires avec `toutesLes` et `lUneDes` (Q2 bis), `Coup.executer` et `Partie.rejouer` (Q4 bis), `Partie.Sauvegarde` et `Historique` (Q5 bis), `Coup.Macro` (Q5 ter), `chessball.ia` : `Noeud` et `AdversaireBT` (Q10, Q12) | Poids-mouche, Composite, Commande, Memento, plus le BT |
| S06 | `AdversaireAleatoire`, `AdversaireHeuristique`, `Tournoi` (Q8) · `ChargeurDAdversaire` (Q9) · `Arbitre`, `ArbitreStandard`, `ArbitreEnUneMiTemps` (Q9 bis) · `TirageAuSort` (Q9 ter) · `Variante`, `VarianteStandard`, `VarianteGrandTerrain` (Q14). Fournis : `StrategieAdversaire`, `CoupsPossibles` | Stratégie, plug-in, Patron de méthode, Injection, Fabrique abstraite |
| S07 | `Configuration` (Q13), `EtatPartieDTO` (Q15) ; votre MiniUnit exécute les suites `chessball.*Test` | Monteur, DTO |
| S09 | `AdversaireAsynchrone` (Q11 bis), `FluxDeCoups` (Q11 ter) ; votre MiniCheck vérifie les propriétés du jeu ; le match (Q12). Fournis : `GenerateurDeCoups`, `Adversaires`, `AdversaireLent`, `Main` | Objet actif, Programmation réactive |

**Le match.** `Main` joue une partie complète et se termine seul :

```bash
mvn -q compile
java -cp target/classes chessball.Main
java -cp target/classes chessball.Main 7 11 200
java -cp target/classes chessball.Main heuristique bt
```

Par défaut, deux adversaires fournis (« pressé » contre « hasard », graines 1
et 2). Les noms `heuristique` (S06) et `bt` (S05) branchent les vôtres, une fois
écrits. L'IA lente sous délai (S09) enveloppe n'importe lequel d'entre eux.

**Le test d'acceptation de MiniUnit (S07)** : votre lanceur exécute les suites
de l'application et doit rendre le même verdict que Maven.

```bash
mvn -q test-compile dependency:build-classpath -Dmdep.outputFile=target/cp.txt
java -cp "target/classes:target/test-classes:$(cat target/cp.txt)" miniunit.MiniUnit chessball.ProxyTest chessball.ObservateurTest
```

Quatre classes se **lancent** au lieu de se tester — ce sont des expériences,
leur sortie est la réponse :

```bash
mvn -q compile
java -cp target/classes cpoo2.s01.echauffement.Variance      # S01 partie 0, ex. 5
java -cp target/classes cpoo2.s04.arbre.SurchargeDemo        # S04 Q6
java -cp target/classes cpoo2.divers.journal.Journalisation  # § 8
java -cp target/classes cpoo2.divers.questions.Q1            # § 15
```

## Un exercice sans suite JUnit, et c'est voulu

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
- le squelette du Visiteur est en **français**, pour coller au sujet de S04 ;
  le dépôt d'origine le nomme en anglais (`Node`, `PlusNode`, `VisitorTree`).

Les exemples de patrons commentés en cours restent dans le dépôt d'Arnaud :
`src/main/java/<patron>/`. Pour le typage, voir
[`arnobl/structural-typing-examples`](https://github.com/arnobl/structural-typing-examples).
