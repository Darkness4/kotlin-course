# Extensions

1. Créez votre projet **Gradle > Kotlin/JVM** et nommez-le `org.example.extensions`

2. Ajoutez dans `build.gradle`

```groovy
jar {
    manifest {
        attributes(
            'Main-Class': 'org.example.extensions.MainKt'
        )
    }

    dependsOn configurations.runtimeClasspath
    from {
        configurations.runtimeClasspath.findAll { it.name.endsWith('jar') }.collect { zipTree(it) }
    }
}

task runApp(type: JavaExec) {
    classpath = sourceSets.main.runtimeClasspath

    main = 'org.example.extensions.MainKt'
}
```

3. Créez votre package `org.example.extensions` et fichier `Main.kt`

## Savoir utiliser des paires et des triples

Les paires et les triples sont des `data class` conçu pour 2 ou 3 propriétés. Elles été conçu pour la destructuration. Il peut arriver qu'une fonction retourne plusieurs valeurs. Les `Pair` et `Triple` sont donc utiles dans ces cas d'utilisations.

Pair :

```kotlin
fun main() {
    val pair: Pair<String, String> = "key" to "value"
    val coordinates = ("x" to 32) to ("y" to 64)
    println("${coordinates.first.second} = ${coordinates.second.second}")
  
    // Destructurer
    val (x, y) = coordinates
}
```

Triple : 

```kotlin
fun main() {
    val triple = Triple("x", "y", "z")
    val coordinates = Triple("x" to 32, "y" to -14, "z" to 23)
    println("${coordinates.first.second}x + ${coordinates.second.second}y + ${coordinates.third.second}z = 0")

    // Destructurer
    val (x, y, z) = coordinates
}
```

## Savoir utiliser les collections

### List

Les `List` et sa version mutable `MutableList` sont des collections dont les éléments sont **ordonnés**.

Pour créer une `List<T>`, on utilise `listOf` :

```kotlin
val list = listOf("value1", "value2")
```

Pour créer une `MutableList<T>`, on utilise `mutableListOf` :

```kotlin
val list = mutableListOf("value1", "value2")
```

### Map

Les `Map` et sa version mutable `MutableMap` sont des collections dont les éléments sont des **paires de clés et valeurs**.

Pour créer une `Map<K, V>`, on utilise `mapOf` :

```kotlin
val map = mapOf("key1" to "value1", "key2" to "value2")
```

Pour créer une `MutableMap<K, V>`, on utilise `mutableMapOf` :

```kotlin
val map = mutableMapOf("key1" to "value1", "key2" to "value2")
```

## Constants

`const val` permet de faire des constantes lors de la compilation. Les constantes sont toujours de haut niveau (en dehors d'une fonction et d'une instance). Elles peuvent donc être initialisé dans un ficher :

```kotlin
const val EARTH_RADIUS = 6371 // km

fun main() {
    // Do...
}
```

Ou dans un `object` :

```kotlin
object Dimensions {
    const val EARTH_RADIUS = 6371 // km
}

// Ou

class Gps {
    companion object {
        const val EARTH_RADIUS = 6371 // km
    }
}
```

`val` vs `const val` diffère du fait que la valeur de `val`  est connu lors de l'exécution du programme, alors que `const val` est connu lors de la compilation.

## Extensions

Les fonctions d'extension vous permettent d'ajouter des fonctions à une classe existante sans avoir à accéder à son code source. Par exemple, vous pouvez les déclarer dans un fichier Extensions.kt qui fait partie de votre paquet. Cela ne modifie pas réellement la classe, mais cela vous permet d'utiliser la notation par points (`.method()`) lorsque vous appelez la fonction sur des objets de cette classe.

Par exemple :

```kotlin
fun String.hasSpaces(): Boolean {
    val found = this.find { it == ' ' }
    return found != null
}
```

Ce qui permet d'écrire (après import) :

```kotlin
println("Does it have spaces?".hasSpaces())
```

Note : Les extensions n'ont pas accès aux propriétés `private`.

Notez que les extensions sont résolues à la compilation. Par conséquent, les extensions sont exécutés en fonction du type déclaré :

```kotlin
open class AquariumPlant(val color: String, private val size: Int)

class GreenLeafyPlant(size: Int) : AquariumPlant("green", size)

fun AquariumPlant.print() = println("AquariumPlant")
fun GreenLeafyPlant.print() = println("GreenLeafyPlant")

val plant = GreenLeafyPlant(size = 10)
plant.print()
println("\n")
val aquariumPlant: AquariumPlant = plant
aquariumPlant.print()  // what will it print?
```

```kotlin
GreenLeafyPlant
AquariumPlant
```

Ici, `plant.print()` utilise la méthode de `GreenLeafyPlant`. Cependant, `aquariumPlant.print()` utilise la méthode de `AquariumPlant` malgré que `plant` soit un sous-type de `AquariumPlant`. En effet, comme le type résolu pour `aquariumPlant` est `AquariumPlant`, `aquariumPlant` utilise `AquariumPlant.print()`.

On peut également faire un getter :

```kotlin
val AquariumPlant.isGreen: Boolean
   get() = color == "green"
```

### Receveurs nullable

La classe que l'on étend avec une extension est une *receveur*. Il est possible de faire également des receveur nullable :

```kotlin
fun AquariumPlant?.pull() {
    this?.apply {
        println("removing $this")
    }
    println("Done.")
}

val plant: AquariumPlant? = null
plant.pull()
```

```kotlin
Done.
```

La fonction est donc exécuté malgré que `plant` soit `null`.

Les extensions sont une des fonctionnalités majeurs de Kotlin et la majorité de la librairie standard de Kotlin est implémenté avec des extensions.

