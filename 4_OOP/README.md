# 4. Object-Oriented Programming

## 4.1 Terminologie

Les termes de programmation suivants devraient déjà vous être familiers :

- Les **classes** sont des plans d'objets. Par exemple, une classe `Aquarium` est le plan de réalisation d'un objet d'aquarium.
- Les **objets** sont des instances de classes ; un objet aquarium est un véritable `Aquarium`.
- Les **propriétés** sont des caractéristiques des classes, telles que la longueur, la largeur et la hauteur d'un aquarium.
- Les **méthodes**, également appelées fonctions membres, sont les fonctionnalités de la classe. Les méthodes sont ce que vous pouvez "faire" avec l'objet. Par exemple, vous pouvez remplir un objet `Aquarium` avec `fillWithWater()`.
- Une **interface** est une spécification qu'une classe peut implémenter. Par exemple, le nettoyage est commun aux objets autres que les aquariums, et le nettoyage se fait généralement de manière similaire pour différents objets. Vous pouvez donc avoir une interface appelée `Clean` qui définit une méthode `clean()`. La classe `Aquarium` pourrait implémenter l'interface `Clean` pour nettoyer l'aquarium avec une éponge douce.
- Les **packages** sont un moyen de regrouper du code lié pour le garder organisé, ou de créer une bibliothèque de code. Une fois qu'un paquet est créé, vous pouvez importer son contenu dans un autre fichier et réutiliser le code et les classes qu'il contient.

## 4.2 Tâche : Créer un classe

### Faites un nouveau projet

1. **Gradle > Kotlin/JVM**

2. Nommez-le `oop`

3. Allez dans `build.gradle` et ajoutez :

```groovy
jar {
    manifest {
        attributes(
                'Main-Class': 'org.example.oop.MainKt'
        )
    }

    dependsOn configurations.runtimeClasspath
    from {
        configurations.runtimeClasspath.findAll { it.name.endsWith('jar') }.collect { zipTree(it) }
    }
}

task runApp(type: JavaExec) {
    classpath = sourceSets.main.runtimeClasspath

    main = 'org.example.oop.MainKt'
}
```

### Créez un package

- Clic Droit sur `src/main/kotlin` et **New > Package**. Appelez-le, `org.example.oop`.

### Ajoutez le main

- Toujours, dans le panneau de gauche, Clic Droit sur `org.example.oop` et **New > Kotlin File / Class**. Appelez-le, `Main` et faites le `main`

```kotlin
fun main() {
    TODO("test here")
}
```

### Ajoutez une classe

1. Sur le panneau de gauche, **New > Kotlin File / Class**.

2. Appelez-le **Canvas**

3. Ajoutez des **propriétés** `var` `height` et `width`. Initialisez-les à `0` cm.

```kotlin
package org.example.oop

class Canvas {
    var height = 0
    var width = 0
}
```

> Notez que nous utilisons `var` au lieu de `val`, car ces propriétés son mutables.
>
> Généralement, Kotlin préfère les données immutables pour pouvoir prédire l'état d'un programme.

4. Dans le main, instanciez un `Canvas`

```kotlin
package org.example.oop

fun main() {
    val canvas = Canvas()
}
```

### Ajoutez une méthode

1. Dans `Canvas`, ajoutez une **méthode** pour afficher ses dimensions

```kotlin
class Canvas {
    // ...
    fun printSize() {
        println("""Height: $height cm
Width: $width cm""")
    }
}
```

2. Testez dans le main

```kotlin
package org.example.oop

fun main() {
    val canvas = Canvas()
    canvas.printSize()
    canvas.height = 32
    canvas.printSize()
}
```

```kotlin
Height: 0 cm
Width: 0 cm
Height: 32 cm
Width: 0 cm
```

## 4.3 Tâche : Ajouter un constructeur

### Ajoutez un constructeur

On souhaites que les dimensions du Canvas soit réglable à l'instanciation. Pour cela faires un constructeur :

```kotlin
class Canvas constructor(height: Int = 0, width: Int = 0) {
    var height = height
    var width = width

    fun printSize() {
        println(
            """Height : $height cm
Width : $width cm"""
        )
    }
}
```

Cette syntaxe peut être raccourcise en mettant les propriétés directement dans le constructeur et en omettant le terme `constructor` :

```kotlin
class Canvas(var height: Int = 0, var width: Int = 0) {
    fun printSize() {
        println(
            """Height : $height cm
Width : $width cm"""
        )
    }
}
```

### Utilisez le constructeur

Les paramètres du constructeur sont actuellement nommés et optionnels. Il est donc possible d'omettre un paramètre et celui-ci prendra la valeur par défaut.

```kotlin
fun main() {
    val canvas = Canvas(32)
    canvas.printSize()
    val canvas2 = Canvas(width = 32)
    canvas2.printSize()
    val canvas3 = Canvas(width = 64, height = 14)
    canvas3.printSize()
    val canvas4 = Canvas(64, 14)
    canvas4.printSize()
}
```

```sh
Height: 32 cm
Width: 0 cm
Height: 0 cm
Width: 32 cm
Height: 14 cm
Width: 64 cm
Height: 64 cm
Width: 14 cm
```

### Utilisez `init`

Actuellement, le constructeur ne permet pas d'exécuter des déclarations et mais permet uniquement d'assigner des propriétés. Pour répondre à cela, on utilise des blocs `init` :

```kotlin
class Canvas(var height: Int = 0, var width: Int = 0) {
    init {
        println("Initializing Canvas!")
    }

    init {
        println("Area: ${height * width}")
    }

    fun printSize() {
        println(
            """Height: $height cm
Width: $width cm"""
        )
    }
}
```

1. Ajoutez ces blocs et lancez le programme.

```kotlin
Initializing Canvas!
Area: 0
Height: 32 cm
Width: 0 cm
Initializing Canvas!
Area: 0
Height: 0 cm
Width: 32 cm
Initializing Canvas!
Area: 896
Height: 14 cm
Width: 64 cm
Initializing Canvas!
Area: 896
Height: 64 cm
Width: 14 cm
```

Vous remarquez que les blocs `init` sont exécutés après le constructeur, et dans l'ordre de la définition de la classe.

### Ajoutez des constructeurs secondaires

Il est possible de déclarer d'ajouter d'autres constructeurs :

```kotlin
class Canvas(var height: Int = 0, var width: Int = 0) {
    init { /* ... */ }
    init { /* ... */ }

    constructor(area: Int) : this(sqrt(area.toDouble()).toInt(), sqrt(area.toDouble()).toInt())

    fun printSize() { /* ... */ }
}
```

```kotlin
// Main.kt
package org.example.oop

fun main() {
    val canvas = Canvas(25)
    canvas.printSize()
}
```

```sh
Initializing Canvas!
Area: 25
Height: 5 cm
Width: 5 cm
```

Notez que les `init` sont toujours exécutés et que le constructeur secondaire à la priorité sur le constructeur primaire.

**Kotlin ne recommande pas l'approche des constructeurs secondaires, car cela créer plusieurs cheminements de code et augmente la probabilité d'erreur.**

Pour cela, on utilise une fonction [factory](https://kotlinlang.org/docs/reference/coding-conventions.html#factory-functions).

```kotlin
class Canvas(var height: Int = 0, var width: Int = 0) {
    init { /* ... */ }
    init { /* ... */ }

    companion object {
        fun fromArea(area: Int) = Canvas(sqrt(area.toDouble()).toInt(), sqrt(area.toDouble()).toInt())
    }

    fun printSize() { /* ... */ }
}
```

```kotlin
package org.example.oop

fun main() {
    val canvas = Canvas.fromArea(25)
    canvas.printSize()
}
```

`companion object` agit similairement à `static` en Java. Cependant, il diffère du fait qu'un `companion object` peut avoir des super-types. 

### Utilisez des Getters et Setters

Les getters et setters  sont généralement utilisés pour une obtenir/définir une propriété dépendante d'autres propriétés. De plus, elles remplace l'assignation par défaut. Si une propriété a uniquement qu'un getter, celle-ci devient en lecture seule pour les opérations externes.

Prenons un exemple, l'aire dépend de la largeur et hauteur.

1. Ajoutez un getter et setter `area`

   ```kotlin
   package org.example.oop
   
   import kotlin.math.sqrt
   
   class Canvas(var height: Int = 0, var width: Int = 0) {
       var area: Int
           get() = height * width
           set(value) {
               height = sqrt(value.toDouble()).toInt()
               width = sqrt(value.toDouble()).toInt()
           }
   
       // ...
   }
   ```

   

2. Testez :

   ```kotlin
   fun main() {
       val canvas = Canvas.fromArea(25)
       canvas.printSize()
       println("Ths area is ${canvas.area} cm")
       canvas.area = 100
       canvas.printSize()
       println("Ths area is ${canvas.area} cm")
   }
   ```

   ```kotlin
   Initializing Canvas!
   Area: 25
   Height: 5 cm
   Width: 5 cm
   Ths area is 25 cm
   Height: 10 cm
   Width: 10 cm
   Ths area is 100 cm
   ```

## 4.4 Tâche : Utiliser des modificateurs de visibilité

Dans Kotlin, les classes, objets, interfaces, constructeurs, fonctions, propriétés et leurs setters peuvent avoir un modificateur de visibilité :

- `public` signifie visible en dehors de la classe. Tout est public par défaut, y compris les variables et les méthodes de la classe.
- `internal` signifie qu'il ne sera visible qu'à l'intérieur de ce module. Un module est un ensemble de fichiers Kotlin compilés ensemble, par exemple, une bibliothèque ou une application.
- `private` signifie qu'il ne sera visible que dans cette classe (ou dans le fichier source si vous travaillez avec des fonctions).
- `protected` est identique à `private`, mais il sera également visible dans toutes les sous-classes.

Exemple :

```kotlin
var area: Int
        get() = height * width
        private set(value) {  // Lecture seule en dehors de la classe.
            height = sqrt(value.toDouble()).toInt()
            width = sqrt(value.toDouble()).toInt()
        }

private val name: String = "Canvas"

private class Painting
```

## 4.5 Tâche : Faites des sous-classes et utiliser l'héritage

### `open` the class

Pour faire des sous-classes, elle quelle soit héritable. Sur Kotlin, les classes ne peuvent pas être héritées par défaut. Pour cela, on marque une classe `open`. Egalement, pour que les propriétés/méthodes qui peuvent être remplacés (`override`), on utilise également `open`.

1. Dans `Canvas`, appliquez `open` à toute les propriétés et méthodes.

   ```kotlin
   open class Canvas(open var height: Int = 0, open var width: Int = 0) {
       open var area: Int
           get() = height * width
           set(value) {
               height = sqrt(value.toDouble()).toInt()
               width = sqrt(value.toDouble()).toInt()
           }
   
       init {
           println("Initializing Canvas!")
       }
   
       init {
           println("Area: ${height * width}")
       }
   
       companion object {
           fun fromArea(area: Int) = Canvas(sqrt(area.toDouble()).toInt(), sqrt(area.toDouble()).toInt())
       }
   
       open fun printSize() {
           println(
               """Height: $height cm
   Width: $width cm"""
           )
       }
   }
   ```

### Faites une sous-classe

1. Faites une sous-classe `CircularCanvas` qui hérite de `Canvas`. Celui-ci est initialisé avec une seule propriété : `diameter`. On fait passer `diameter` à `height` et `width` de la super-classe. **Comme  `CircularCanvas` hérite de `Canvas`, il possède les même propriétés que `Canvas`**. Pour remplacer les propriétés, on les marquent avec `override`.

   ```kotlin
   class CircularCanvas(var diameter: Int) : Canvas(diameter, diameter)
   ```

2. Remplacez les getters et setters pour calculer l'aire d'un cercle :

   ```kotlin
       override var area: Int
           get() = (Math.PI * (diameter / 2.0).pow(2)).toInt()
           set(value) {
               diameter = (2 * sqrt(value / Math.PI)).toInt()
               height = diameter
               width = diameter
           }
   ```

3. Testez :

   ```kotlin
   fun main() {
       val canvas = CircularCanvas(25)
       canvas.printSize()
       println("Ths area is ${canvas.area} cm")
       canvas.area = 100
       canvas.printSize()
       println("Ths area is ${canvas.area} cm")
   }
   ```

   ```sh
   Initializing Canvas!
   Area: 625
   Height: 25 cm
   Width: 25 cm
   Ths area is 490 cm
   Height: 11 cm
   Width: 11 cm
   Ths area is 95 cm
   ```

## 4.6 Tâche : Utiliser les classes abstraites et interfaces

### Différence et similarités entre classes abstraites et interfaces

- Une classe abstraite et une interface **ne peuvent pas être instanciés**.
- Une classe abstraite est **hérité par une sous-classe**. Une interface est **implémenté par une classe (abstraite ou non)**.
- Une classe abstraite peut contenir un **constructeur** et peut stocker un **état**.
- Une interface **ne peut hériter** d'une classe
- Une classe ne peut être concrète uniquement lorsque que toutes ses méthodes sont implémentées. Sinon, la classe doit être abstraite (voire interface s'elle hérite d'une interface et qu'elle ne contient pas d'état)

On utilise généralement une interface lorsque l'on connait uniquement ses cas d'utilisations mais pas ses implémentations. Par exemple, une source de donnée contient généralement les méthodes `fetch`, `delete`, `update`, `create`, cependant, on ne connait pas comment on va l'implémenter (cela va dépendre de la source de donnée en question).

On utilise généralement une classe abstraite lorsque l'on ne veut pas instancier une classe.

```kotlin
abstract class Person(val name: String) {  // Sure

    var age: Int = 40  // Okay

    fun displaySSN(ssn: Int) {
        println("My SSN is $ssn.")
    }

    abstract fun displayJob(description: String)
}
```

```kotlin
// Equivalent, without state and constructor
interface IPerson {
    var age: Int
    val name: String

    fun displaySSN(ssn: Int) {
        println("My SSN is $ssn.")
    }

    fun displayJob(description: String)
}
```

### Créez une interface et une abstract class

1. Faites une interface `Image`
   1. Elle contient les propriétés `height: Int` et `width: Int`
   2. Elle contient un getter `size: Int = height * width`
   3. Elle contient une fonction `editImage(x: Int, y: Int, color: Int)`
   
   ```kotlin
   interface Image {
       val height: Int
       val width: Int
       val size: Int
           get() = height * width
   
       fun editImage(x: Int, y: Int, color: Int)
   }
   ```
   
2. Faites une classe `ImageImpl` qui implémente `Image`
   
   1. Le constructeur initialise les propriétés `height`, `width`.
   2. Elle contient les `pixels = IntArray(height * width)`
   3. Elle implémente `editImage` (pensez à `throw`)
   
   ```kotlin
   class ImageImpl(
       val name: String,
       override val height: Int,
       override val width: Int
   ) : Image {
       private val pixels = IntArray(size)
   
       override fun editImage(x: Int, y: Int, color: Int) {
           if (x !in 0 until width || y !in 0 until height) {
               throw IllegalArgumentException("Pixel out of bounds.")
           }
           pixels[x + y * width] = color
       }
   }
   ```
   
3. Faites une abstract class de `Image` et appelez-le `SquareImage` :

   1. Son `constructor` initialise uniquement `length: Int`
   2. Elle `final override`  `width` et `height` par un getter qui donne `length`. `final` va empêcher d'autre sous-classe de remplacer ces propriétés.

   ```kotlin
   abstract class SquaredImage(val length: Int) : Image {
       override val height: Int
           get() = length
       override val width: Int
           get() = length
   }
   ```

4. Faites une classe `SquareImageImpl` qui implémente `SquareImage` de la même manière que `ImageImpl`

   ```kotlin
   class SquaredImageImpl(length: Int) : SquaredImage(length) {
       private val pixels = IntArray(size)
   
       override fun editImage(x: Int, y: Int, color: Int) {
           if (x !in 0 until length || y !in 0 until length) {
               throw IllegalArgumentException("Pixel out of bounds.")
           }
           pixels[x + y * length] = color
       }
   }
   ```

   **Quand utiliser une classe abstraite ou une interface**

   Dans l'exemple ci-dessus, l'interface a été utilisée pour **composer une classe**. C'est généralement dans ce cas que l'on utilise les interfaces. La composition conduit souvent à une meilleure encapsulation, un couplage plus faible (interdépendance), des interfaces plus propres et un code plus utilisable.

   Une classe abstraite permet d'étendre l'interface, de remplacer l'interface ou permet de l'implémenter sans avoir à l'instancier.

   De plus, Kotlin autorise à avoir plusieurs interfaces contrairement à une classe abstraite.

## 4.7 Tâche : Utiliser des délégation d'interface

### Faire un `object`

Un `object` est une class qui n'a qu'une seule instance. C'est ce que l'on appelle donc un singleton. Par conséquent, l'`object` n'a pas de constructeur.

`companion object` d'une classe est une `object`. La différence est que `companion object` est lié à une classe.

```kotlin
class SingletonClass internal constructor(var data: Int) {
    companion object Factory {
        private val instance = SingletonClass(0)

        fun getInstance() = instance
    }
}
```

```kotlin
// Equivalent
class SingletonClass internal constructor(var data: Int)

object SingletonClassFactory {
    private val instance = SingletonClass(0)

    fun getInstance() = instance
}
```

Comme les `object`s sont des singletons, il est possible de créer des Factories qui permet de contenir l'instance d'une classe.

L'appel d'un factory retournera la même instance.

```kotlin
fun main() {
    // Using companion object
    val singleton = SingletonClass.getInstance()
    println("singleton ${singleton.data}")
    singleton.data = 5
    println("singleton ${singleton.data}")
    val singleton2 = SingletonClass.getInstance()
    println("singleton2 ${singleton2.data}")
    println("Is it the same reference ? ${singleton === singleton2}")
}
```

```sh
singleton 0
singleton 5
singleton2 5
Is it the same reference ? true
```

Avec un `object` :

```kotlin
fun main() {
    // Using object
    val singleton = SingletonClassFactory.getInstance()
    println("singleton ${singleton.data}")
    singleton.data = 5
    println("singleton ${singleton.data}")
    val singleton2 = SingletonClassFactory.getInstance()
    println("singleton2 ${singleton2.data}")
    println("Is it the same reference ? ${singleton === singleton2}")
}
```

```sh
singleton 0
singleton 5
singleton2 5
Is it the same reference ? true
```

### Faire une délégation d'interface

Grâce aux `object`s, il est également possible d'implémenter des interface par délégation.

En prenant l'exemple d'une `Person` et de ses "équipements" :

```kotlin
interface LeftArm {
    fun actLeft()
}

interface RightArm {
    fun actRight()
}

class Person : LeftArm, RightArm {
    override fun actLeft() {
        TODO("Not yet implemented")
    }

    override fun actRight() {
        TODO("Not yet implemented")
    }
}
```

Au lieu d'implémenter un par un, on peut utiliser des `object` : 

```kotlin
object Shield : LeftArm {
    override fun actLeft() {
        print("Protecting !")
    }
}

object Sword: RightArm {
    override fun actRight() {
        print("Cutting !")
    }
}
```

Alors, on peut déléguer les implémentations des divers interfaces :

```kotlin
class Person : LeftArm by Shield, RightArm by Sword
// class body is empty
```

Ceci est actuellement possible, car les `object`s sont des singletons et n'ont pas de constructeurs.

Cependant, il peut arriver que l'on veut modifier l'implémentation. Dans ce cas, on peut faire passer la délégation en constructeur :

```kotlin
class Person(leftArm: LeftArm = Shield, rightArm: RightArm = Sword) : LeftArm by leftArm, RightArm by rightArm
```

```kotlin
object Lance : RightArm {
    override fun actRight() {
        print("Piercing !")
    }
}

fun main() {
    Person(rightArm = Lance)  // Simple
}
```

## 4.8 Tâche : Faites des data classes

Les `data class`es sont des classes dont certaines fonctionnalités ont été pré-implémentées pour les classes ne contenants que des données.

Cela inclut : 

- L'opérateur `equals`, dont le `hashCode()`, pour comparer en terme de valeur (et non en terme d'instance)
- `toString()` qui affiche proprement les données, comme `"User(name=John, age=42)"`
- Les fonctions `componentN()`, qui récupère la propriété par ordre de définition de la classe (`component1` = `name` dans l'exemple). Cela permet la destructuration.
- `copy()` pour copier les données et créer une instance

### Faire une data class

1. `data class Person` qui porte `nom: String`, `age: Int`, `address: String`

   ```kotlin
   data class Person(val nom: String, val age: Int, val address: String)
   ```

2. Testez `toString`

   ```kotlin
   fun main() {
       val person = Person("Smith", 18, "Earth")
       println(person)
   }
   ```

   ```sh
   Person(nom=Smith, age=18, address=Earth)
   ```

3. Testez `equals`

   ```kotlin
       val person = Person("Smith", 18, "Earth")
       val person2 = Person("Smith", 18, "Earth")
       val person3 = Person("NotSmith", 666, "Earth")
       println("person == person2: ${person == person2}")
       println("person == person3: ${person == person3}")
   ```

   ```sh
   person == person2: true
   person == person3: false
   ```

4. Testez la destructuration.

   ```kotlin
   val (nom, age, address) = person
   ```

## 4.9 Tâche : Faites des singletons, enums et sealed classes

### Léger rappel

Un singleton est un `object`, car il n'a qu'une instance.

### Faire un Enum

Les `enum class` peuvent contenir des valeurs :

```kotlin
enum class Color(val rgb: Int) {
   RED(0xFF0000), GREEN(0x00FF00), BLUE(0x0000FF);
}
```

Comme il n'y a pas de `switch..case`, l'utilisation avec `when` se fait de la sorte :

```kotlin
// x: Color
when (x) {
    Color.RED -> TODO()
    Color.BLUE  -> TODO()
    Color.GREEN -> TODO()
}
```

Si `x` n'est pas un `enum class`, mais porte la valeur :

```kotlin
when (x) {
    Color.RED.rgb -> TODO()
    Color.BLUE.rgb  -> TODO()
    Color.GREEN.rgb -> TODO()
    else -> TODO()
}
```

### Faire des Sealed Classes

Les `sealed class` sont actuellement des `enum class` extrêmement puissant. Au lieu de porter des valeurs, ils portent des classes.

Une `sealed class` ne peut pas être une sous-classe.

Cette propriété permet de faire ceci :

```kotlin
sealed class Result {
    data class Success(val data: Any?): Result()
    data class Error(val e: Throwable): Result()
}

fun fetchData(): Result = try {
    TODO("Fetch data")
    Result.Success("Yay")
} catch (e: Throwable) {
    Result.Error(e)
}
```

```kotlin
fun main() {
    fetchData().let {
        when (it) {
            is Result.Success -> println(it.data)
            is Result.Error -> println(it.e)
        }
    }
}
```



