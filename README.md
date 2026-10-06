# CPOO2 — espace de travail étudiant

*Support des séances de **CPOO2** (INSA Rennes, 4INFO, 2026-2027) et des
exercices courts. Les sujets sont distribués à part ; ici, le code.*

**Deux familles de paquets.** `cpoo2/` : les exercices académiques des séances
1 à 4 (Optional, injecteur, fabriques, Pont, les enveloppes de S03 sur le
ChessBall de la boîte, avec la bibliothèque tierce `gridkit/`, Visiteur). `chessball/` :
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

Sur un clone intact, JDK 21, vérifié le 2026-09-29 (après la refonte de S05) :

```
Tests run: 234, Failures: 47, Errors: 128   →  175 rouges, 59 verts
```

| Suite | Séance | Tests | Rouges | Ce que les verts veulent dire |
|---|---|---|---|---|
| `PositionTest`, `EquipeTest`, `CoupsTest`, `ReglesTest` | S01 partie 0 | 21 | 18 | trois verts à vide : `toString` et `equals` engendrés par `record`, et `peek` (question 3.2) |
| `MonOptionalTest` | S01 ex. 1 | 9 | 8 | le seul vert passe **à vide** |
| `InjectorTest` | S01 ex. 2 | 12 | 6 | **six verts à vide**, c'est la question Q12 |
| `FooTest` | S02 ex. 1 (Singleton) | 1 | 1 | |
| `ColourCardTest` | exercice court C6 | 6 | 4 | deux verts à vide |
| `ExpFactoryTest` | S02 ex. 2 | 10 | **0** | la fabrique marche déjà : c'est un **filet**, pas un énoncé |
| `OperateurTest` | S02 ex. 2 | 11 | 10 | un vert à vide (`de` rend vide pour tout) |
| `RappelsTest` | S02 ex. 3 | 5 | 4 | un vert à vide (la boîte d'envoi du stub est vide) |
| `NotificationTest` | S02 ex. 4 | 5 | 5 | le cinquième test lit la structure par réflexion |
| `cpoo2.jeu.NoyauTest` | S03, fourni | 4 | 0 | le noyau du ChessBall de la boîte : ils disent ce qu'on peut attendre de lui |
| `cpoo2.s03.ia.AdaptateurTest` | S03 ex. 1 | 7 | 7 | |
| `cpoo2.s03.ia.DecorateurTest` | S03 ex. 2 | 9 | 9 | |
| `cpoo2.s03.ia.ProxyTest` | S03 ex. 3 | 5 | 5 | |
| `cpoo2.jeu.PartieTest` | S04, fourni | 6 | 0 | la vraie partie, avec son arbitre : ce qu'elle garantit, et le filet de S05 |
| `cpoo2.s04.scenario.ScenarioTest` | S04, fourni | 4 | 0 | la lecture des scénarios |
| `cpoo2.s04.scenario.InterpreteurTest` | S04 ex. 1 | 4 | 4 | |
| `cpoo2.s04.scenario.VisiteursTest` | S04 ex. 2 et 3 | 5 | 5 | |
| `cpoo2.s04.observateur.ObservateurTest` | S04 ex. 5 | 5 | 5 | |
| `cpoo2.jeu.EtatTest` | S05 ex. 1 (État) | 5 | 5 | `PartieTest`, lui, reste vert : c'est le filet de la refonte |
| `cpoo2.s05.entrainement.MementoTest` | S05 ex. 2 | 5 | 4 | un vert à vide : la sauvegarde vide est déjà opaque |
| `cpoo2.s05.bt.BehaviourTreeTest` | S05 ex. 3 | 5 | 5 | |
| `cpoo2.s05.bt.AttaquanteTest` | S05 ex. 3 | 6 | 6 | |
| `cpoo2.s05.ecs.MondeTest` | S05 ex. 4 (l'ECS) | 4 | 4 | |
| `UndoRedoHistoryTest`, `CollectionsTest` | compléments hors séance (Commande et Memento, Poids-mouche) | 20 | 16 | trois verts à vide |
| `chessball.StrategieTest` | S06 Q8 | 5 | 4 | un test structurel passe à vide (le tournoi ne nomme aucune stratégie concrète) |
| `chessball.PlugInTest` | S06 Q9 | 7 | 7 | |
| `chessball.PatronDeMethodeTest` | S06 Q9 bis | 5 | 4 | le test structurel (`final`, trous protégés) passe à vide |
| `chessball.InjectionTest` | S06 Q9 ter | 4 | 3 | le test structurel (aucun `Random` dans l'arbitre) passe à vide |
| `chessball.FabriqueAbstraiteTest` | S06 Q14 | 4 | 3 | le test structurel (`nouvellePartie` final) passe à vide |
| `chessball.LigneDeProduitsTest` | S06 Q17 (facultative) | 4 | 4 | paramétré : quatre configurations de l'échantillon jouent un match |
| `chessball.ConfigurationTest` | S07 ex. 8 | 6 | 5 | un vert à vide : un test de structure |
| `chessball.EtatPartieDTOTest` | S07 ex. 9 | 6 | 5 | un vert à vide : les types des composants |
| `chessball.GenerateurDeCoupsTest`, `ProprietesDuJeuTest`, `MatchTest` | S09, fournis | 11 | 0 | le générateur, cinq propriétés du jeu et le match : ils passent, c'est le filet de l'application |
| `chessball.AdversaireAsynchroneTest` | S09 Q11 bis | 5 | 5 | |
| `chessball.FluxDeCoupsTest` | S09 Q11 ter | 3 | 3 | |

**Un test vert ne prouve pas toujours quelque chose.** Une partie de ces
cinquante-neuf verts passent sur du code qui ne fait rien. Savoir lesquels,
et pourquoi, fait partie du travail (S01 Q12–Q13).

## Carte des exercices

```
src/main/java/cpoo2/
  s01/echauffement/  Position, Equipe, Coup…, Coups, Regles, Variance
                                             ← partie 0 : record, sealed, lambdas, variance
     /optionnel/     MonOptional             ← reconstruire Optional
     /injection/     Inject, Injector        ← réflexion, annotations, cycles
     /testabilite/   Foo, RandomGenerator    ← S02 ex. 1 : le Singleton casse le test
  s02/fabrique/      ExpFactory, Operateur   ← ex. 2 : la cascade de if → un domaine fermé
     /pont/          api, impl, service/Rappels ← ex. 3 : API et implémentation, puis Fabrique abstraite
     /pont/notification/ Notification, Canal… ← ex. 4 : le Pont, notifications × canaux
     /enumeration/   ColourCard              ← exercice court C6 : fabrique depuis une chaîne
     /annuler/       Undoable, UndoRedoHistory ← complément (Commande et Memento) : undo/redo
     /monteur/       Arbre, Chene, Pin       ← rattaché à S07 (Monteur) : monteurs fonctionnels
     /fluide/        RobotFactory, Exemples  ← rattaché à S07 : API fluide à états
     /poidsmouche/   List, ArrayList, Collections ← complément (Poids-mouche) : singletonList / emptyList
  s04/scenario/      Scenario, Instruction, Condition… ← le langage des scénarios (fourni)
                     BallonEn, TraitAux, Vainqueur, Et, Non ← S04 ex. 1 : l'Interpréteur
                     Deplacer … Verifier (accept) ← S04 ex. 2 : le Visiteur greffé
                     Afficheur, Statistiques, Executeur ← S04 ex. 3 : trois visiteurs
                     SurchargeDemo, Demo     ← à exécuter (Q7, et la séance)
     /observateur/   JournalDesCoups, CompteurDeCoups ← S04 ex. 5 : l'Observateur (et Partie.ajouterEcouteur)
  divers/flux/       Boucles                 ← boucles → Stream
        /journal/    Journalisation          ← à exécuter : le log paresseux
        /paresseux/  Foo                     ← lazy val
        /questions/  Q1                      ← à exécuter : getOrDefault et le NPE
  jeu/               Position, Plateau, Coup, Jeu, PartieDEssai… ← le ChessBall de la boîte, le noyau fourni (S03 à S09)
                     Partie, EcouteurDePartie ← la vraie partie, sur l'arbitre de lib/ (S04 : ses abonnés, S05 : État et Memento)
                     EtatPartie, EnJeu, ApresTacle, Gagnee, Nulle ← S05 ex. 1 : l'État
  s03/ia/           IA, IAAleatoire, IAGloutonne, Distances, Rencontre, Exemples, Demo (fournis)
                     DistancesGridkit        ← S03 ex. 1 : l'Adaptateur
                     SansButContreSonCamp, IAJournalisee ← S03 ex. 2 : le Décorateur
                     JeuEnLectureSeule, Rencontre ← S03 ex. 3 : le Proxy
src/main/java/gridkit/  GridPaths             ← S03 ex. 1 : la bibliothèque tierce, ne se touche pas
lib/…/arbitre-chessball-1.0.jar               ← l'arbitre du ChessBall de la boîte, compilé : Maven le trouve seul


src/main/java/chessball/                     ← L'APPLICATION, un patron par séance
  Position, Couleur, Direction, TypePiece, Piece, Placements, Plateau, Verif, Demo
                                             ← le noyau (aligné sur le moteur de CPOO1) : fourni, ne se touche pas
  Motif, MotifGlissant, MotifSauteur, MotifCompose, MotifNul, Motifs
                                             ← les déplacements (fournis)
  Coup, Verdict, Regle, Regles, Jeu, Partie, Journal, EcouteurDePartie
                                             ← la couche jeu (fournie)
  Partie (abonnés), JournalDesCoups, CompteurDeCoups
                                             ← fournis : l'Observateur s'écrit en S04 sur cpoo2.jeu.Partie
  Motifs (partagé), Regles (élémentaires, toutesLes, lUneDes), Coup.executer, Partie.rejouer,
  Partie.Sauvegarde, Historique, Coup.Macro  ← fournis (l'ancienne S05) : S05 se fait sur cpoo2.jeu
  ia/Statut, Contexte, Noeud, AdversaireBT   ← fournis (l'ancien attaquant behaviour tree)
  StrategieAdversaire, CoupsPossibles (fournis), AdversaireAleatoire, AdversaireHeuristique, Tournoi,
  ChargeurDAdversaire, TirageAuSort, Arbitre, ArbitreStandard, ArbitreEnUneMiTemps,
  Variante, VarianteStandard, VarianteGrandTerrain
                                             ← S06 : Stratégie, plug-in, Injection, Patron de méthode, Fabrique abstraite
  Configuration, EtatPartieDTO               ← S07 : le Monteur d'une partie, l'état exporté
  GenerateurDeCoups, Adversaires, AdversaireLent, Main (fournis), AdversaireAsynchrone, FluxDeCoups
                                             ← S09 : Objet actif, Programmation réactive, et le match
src/main/java/cpoo2/s05/entrainement/  Historique ← S05 ex. 2 : le gardien du Memento
                    /bt/  Noeud, Condition, Action, Feuilles (fournis), Sequence, Selecteur, Inverseur, IAAttaquante
                                             ← S05 ex. 3 : le behaviour tree, Composite et Poids-mouche
                    /ecs/ Monde             ← S05 ex. 4 : l'ECS en contrepoint
```

## L'application, séance par séance

| Séance | Ce que vous ajoutez à `chessball/` | Patron |
|---|---|---|
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
et 2). Le nom `heuristique` (S06) branche le vôtre, une fois écrit, et `bt`
l'attaquant behaviour tree fourni. L'IA lente sous délai (S09) enveloppe n'importe lequel d'entre eux.

**Le test d'acceptation de MiniUnit (S07)** : votre lanceur exécute les suites
de l'application et doit rendre le même verdict que Maven.

```bash
mvn -q test-compile dependency:build-classpath -Dmdep.outputFile=target/cp.txt
java -cp "target/classes:target/test-classes:$(cat target/cp.txt)" miniunit.MiniUnit cpoo2.s03.ia.ProxyTest cpoo2.s04.observateur.ObservateurTest
```

Quatre classes se **lancent** au lieu de se tester — ce sont des expériences,
leur sortie est la réponse :

```bash
mvn -q compile
java -cp target/classes cpoo2.s01.echauffement.Variance      # S01 partie 0, ex. 5
java -cp target/classes cpoo2.s04.scenario.SurchargeDemo     # S04 Q7
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
