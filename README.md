## TESTS UNITAIRES EN JAVA
---
Le but ici est d'introduire les tests unitaires en java.
Pour cela, nous allons partir d'un projet __maven__, inclure par la suite une librairie 
de java pour réaliser nos tests unitaires: __JUnit__.

---
1. **Breve introduction a maven** 
---

En de mots simples, [maven](https://maven.apache.org) est un gestionnaire de __build__. C'est un outil developpe par la fondation apache dans le but de faciliter le développement de projet d'envergure en java. En somme, maven permet de gérer les dépendances externes de ton application 
à d'autres librairies, structurer la gestion des versions des applications et surtout l'assemblage 
du code source de ton application en une archive __.jar__ ou __.war__ . 

L'une des plus grande force de maven est la possibilite qu'il offre de developper et/ou d'utiliser 
des plugins. Il existe un ensemble d'instructions inter-dependantes (personnalisables) mis a la 
dispositions dans maven qui sont declenchées à des étapes précises du cycle d'assemblage du projet. Ils sont connus sous le nom de __phases__. A chaque phase, on peut associer plusieurs 
sous-instructions connus sous le nom de __goal__. 

Des details sur les cycles de vie sont disponibles [sur le site web de maven](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)

[Cliquez ici pour telecharger maven](https://maven.apache.org/download.cgi) et [ici pour l'installation](https://maven.apache.org/install.html)

Pour verifier que l'installation s'est bien déroulée, faites `mvn -v`.
Si succès vous verrez un message comme 
```
C:\Users\Kalmogo\training\tutos\java-unit-test>mvn -v
Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T11:41:47-05:00)
Maven home: C:\tools\apache-maven-3.3.9\bin\..
Java version: 1.8.0_131, vendor: Oracle Corporation
Java home: C:\Users\Kalmogo\jdk8
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "dos"

```

---
2. **Creation du projet.**
---

A cette etape, vous devez avoir maven qui marche correctement. 
Pour créer notre projet de base, nous allons exécuter cette commande 

`mvn archetype:generate -DgroupId=io.rsk.tutorial -DartifactId=java-unit-test -DarchetypeArtifactId=maven-archetype-quickstart` 

Ces instructions disent a maven de generer un projet avec *** io.rsk.tutorial *** comme package de 
base et *** java-unit-test *** comme nom de projet.
La strcuture de notre projet est la suivante: 
```
.java-unit-test
 |-- src
 |   |-- main
 |   |   `-- java
 |   |       `-- io.rsk.tutorial   
 |   |           `-- App.java
 |   `-- test
 |       `-- java
 |           `-- io.rsk.tutorial   
 |               `-- AppTest.java
  `-- pom.xml

```
Le repertoire `src` est celui qui contient tout le code source de notre projet (ainsi que les tests unitaires que nous allons bientot aborder).

Ce repertoire contient deux autres `main` et `test`.
Nous ecrirons nos tests unitaires sous `test` et le code que nous voulons tester sous `main`

Le fichier `pom.xml` contient la description de notre projet (version, nom, developpeurs, dependances, instructions de builds, configurations et bien d'autres informations que je ne saurai detailler ici).

Dans le fichier `pom.xml`, nous avons le contenu suivant :
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>io.rsk.tutorial</groupId>
  <artifactId>java-unit-test</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>java-unit-test</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>

```

La section `<dependancies></dependancies>` est celle qui nous interesse. Elle contient toutes les déclarations de librairies dont notre projet a besoin pour fonctionner. Il y'a actuellement une 
dépendance qui vient déjà inclue lorsque nous avons généré notre projet. Curieusement, c'est de cette dernière dont nous avons besoin. 

Cependant, sa version n'est pas à mon goût. Nous allons changer dans le tag `version` `3.8.1` à `4.12` (parce que j'aime bien les nombres paires) pour avoir une version stable de la librairie.

On obtient 
```xml
...
<dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
</dependency>
```

La présence de cette librairie dans le projet généré vient aussi témoigner le degré d'importance d'avoir du code testé dans nos projets. 

Tester la logique de notre application a plusieurs avantages :
- Elle nous procure la confiance que ce que nous écrivons fonctionne très bien.
- Elle nous épargner de mauvaise surprise dans le comportement de nos classes/méthodes
- Sauve des heures de débogage de notre code une fois le projet muri. 

Il est aussi necessaire de souligner le sens réel d'un test unitaire. 

>En POO, l'unité de base d'un programme est la classe. Un test unitaire s'applique donc à une classe et à elle seule. Si la classe dépend d'une autre, alors il faut trouver un moyen d'isoler la classe toute seule afin de tester son comportement. Cela vient ajouter un nouveau goût à l'univers des tests. Cela ne fait cependant pas parti de notre sujet aujourd'hui. [Cliquez ici pour en savoir davantage sur les mocks.](http://www.vogella.com/tutorials/Mockito/article.html).



Pour illustrer les tests unitaires, nous allons créer un Calculateur qui contient une méthode pour calculer la somme de nombres entiers :

```java
class Calculateur{
    public Integer calculerSomme(Integer ...mesNombresAAdditionner){
        return Stream.of(mesNombresAAdditionner)
                     .reduce(0, (nombre1,nombre2) -> nombre1 + nombre2);
    }
}
```
Cette méthode fait usage des streams de java8 et calcule la somme des entiers qui sont dans le tableau passé en paramètre.

Maintenant que notre librairie de test est inclue, notre methode bien ecrite, nous pouvons passer 
à l'écriture des tests unitaires.

Pour écrire un test, il faut écrire une méthode qui soit annoté avec `@Test`.
Dans notre cas, nous voulons tester que notre classe calculateur fait bien ce qu'elle est censee faire, c'est a dire que si un ensemble d'entier lui est fourni, il retourne la somme correspondante. Aussi, voudrons nous savoir aussi ce qui se passe si un tableau vide lui est fourni. 

Mais avant de continuer, il convient de mentionner que les bonnes pratiques stipulent que les class de test soient suivi du mot cle `test`.
Notre classe de test s'intitule alors `CalculateurTest`:
```java
public class ClaculateurTest {
    // ... CONTENU
}
```
Afin d'effectuer nos test sur la classe `Calculateur`, nous avons besoin de l'instancier à l'interieur de la classe de test.
```java
public class CalculateurTest {
    private Calculateur calculateur;
}
```
Pour ne pas nous retrouver avec un `NullPointerException`, il faut initialiser l'attribut que nous venons de declarer. nous allons le faire a l'interieur de notre methode de test.

Nous allons premierement ecrire le test le plus simple pour le comportement de la somme, c'est a dire pour un tableau avec un seul paramètre ( vu que notre méthode à tester prend au moins un paramètre ).

``` java
...
@Test
public void etantDonneUnSeulNombreAAdditionner_quandOnCalculeLaSomme_alorsRetournerLeNombre(){

}
```
Avec cette methode, une simple lecture decrit sans ambiguite ce que nous avons l'intention de faire dans notre methode.

JUnit dispose d'une classe nommée `org.junit.Assert` qui contient un ensemble de methodes utilitaires qui permettent de verifier des assertations. Par exemple, pour verifier qu'une variable donnée est égale à une certaine valeur, nous pouvons faire comme :
```java
    Assert.assertEquals(valeurRecherchee, variableAVerifier);
```
C'est une classe static et il vous est possible de l'ouvrir afin de prendre connaissance de la puissance dont elle met a votre disposition. 

Pour revenir à notre cas, nous voulons vérifier que la somme d'un  élément en paramètre se comporte bien et qu'elle renvoie le même nombre.

```java
@Test
public void etantDonneUnSeulNombreAAdditionner_quandOnCalculeLaSomme_alorsRetournerLeNombre(){
    calculateur = new Calculateur();
    Integer nombre1  = 2;

    Integer resultat = calculateur.calculerSomme(nombre1);

    Assert.assertEquals(nombre1, resultat);
}
```

Nous venons d'ecrire notre premier cas de test.

Pour executer notre test, nous allons laisser maven s'en charger:
`mvn clean test` à la racine du projet devrait faire l'affaire
(Un petit problem de configuration de maven s'est glissé et le la command précédente renvoie des erreurs. Cependant le code fonctionne bien et vous pouvez importer le projet maven dans un IDE comme eclipse et là , faites clique droit sur la classe de test , CalculatorTest, et choisir `run as JUnit test`)

---
3. **Autres ressources**
---


[Bon guide pour maven](http://www.vogella.com/tutorials/ApacheMaven/article.html)