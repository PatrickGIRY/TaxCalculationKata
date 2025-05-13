# Kata - Calcul de taxes

## Initialisation du projet

Création d'un projet avec le système de construction `gradle` avec un DSL `kotlin`.

Ajout des dépendances :

* `JUnit 5` pour les tests
* `AssertJ` pour les assertions de tests
* `Cucumber` pour les scénarios de tests des fonctionnalités
* `JUnit platform` pour lancer `Cucumber` depuis `junit 5`

Définition du projet comme une application avec plugin `application` et en définissant le point d'entrée.

```kotlin
application {
    mainClass = "katas.tax.calculation.Main"
}
```

On crée le point d'entrée :

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

On lancer l'exécution de l'application avec la commande : 

```shell
./gradlew run
```

On ajout le lanceur de scénarios `cucumber` avec `JUnit` (`katas.tax.calculation.features.FeaturesTest`).

On valide que çà fonctionne avec un fichier de scénarios fictif (`src/test/resources/features/Feature.feature`) :

```gherkin
Feature: Feature
  Scenario: Scenario 1
    Given state
    When action
    Then result
```

Et des définitions d'étapes qui correspondent :

```java
package katas.tax.calculation.features.step.definitions;

public class FeatureStepDefinitions {
    @Given("state")
    public void state() {

    }
    @When("action")
    public void action() {

    }
    @Then("result")
    public void result() {
        assertThat(true).isFalse();
    }
}
```

Avec la commande suivante le scénario fait une erreur sur la dernière étape :

```shell
./gradlew test --info
```

Pour vérifier que `JUnit` fonctionne on ajoute un test fictif : 

```java
class NothingTest {

    @Test
    void is_true() {
        assertThat(true).isFalse();
    }
}
```

On lance la commande pour vérifier que le test est bien rouge :
```shell
./gradlew test --info
```