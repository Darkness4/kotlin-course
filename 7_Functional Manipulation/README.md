# 7. Manipulation Fonctionnel

## 7.1 Annotations

Les annotations permettent de marquer des objets, variables, fonctions et paramètres.

Exemple :

```kotlin
class Foo @Inject constructor(dependency: MyDependency) { ... }
```

Pour créer des annotations, on utilise `annotation class`, comme dans cet exemple :

```kotlin
annotation class ReplaceWith(val expression: String)

annotation class Deprecated(
        val message: String,
        val replaceWith: ReplaceWith = ReplaceWith(""))

@Deprecated("This function is deprecated, use === instead", ReplaceWith("this === other"))
```

On peut également préciser :

- les cibles de l'annotation avec `@Target`
- s'il est disponible dans la classe compilé et s'il est visible par réflexion avec `@Retention` (vrai par défaut)
- si on peut répéter l'annotation sur une même cible `@Repetable`
- s'il doit être documenté avec `@MustBeDocumented`

Exemple :

```kotlin
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Fancy
```

## 7.2 Breaks labelés

Comme les `return`, il est possible d'indiquer la sortie d'un break dans une boucle :

```kotlin
outerLoop@ for (i in 1..100) {
    print("$i ")
    for (j in 1..100) {
        if (i > 10) break@outerLoop  // breaks to outer loop
    }
}
```

## 7.3 Fonctions sur les itérables/séquences

### Transformations

Les transformations arrivent très souvent dans le monde de la programmation.

#### Mapper

`map{ T -> R }` crée une collection à partir des résultats d'un lambda appliqué sur une autre collection. Il **applique le lambda sur chaque élément de la collection** et retourne une collection à partir des résultats. 

```kotlin
val sequence = sequenceOf(1, 2, 3, 4, 5)
val transformedSequence = sequence.map { "This is ${it * 2}" }
for (i in transformedSequence) {
  println(i)
}

```

```sh
This is 2
This is 4
This is 6
This is 8
This is 10
```

Si l'on souhaite obtenir l'indice de chaque élément, on peut utiliser `mapIndexed{ index:Int, T -> R }`.

Si le lambda génère des `null`, il est possible de filtrer avec `mapNotNull{ T -> R? }` ou `mapIndexedNotNull{ index: Int, T -> R? }`.

#### Zip

`zip(other: Sequence<R>)` ou `zip(other: Sequence<R>) { a: T, b: R -> V }` fusionne 2 collections en une collection de `Pair`.

```kotlin
val sequenceA = sequenceOf(1, 2, 3, 4, 5)
val sequenceB = sequenceOf("One", "Two", "Three", "Four", "Five")
for ((a, b) in sequenceA zip sequenceB) {
  println("My pair: $a, $b")
}

// Avec une fonction, on peut appliquer une transformation
sequenceA.zip(sequenceB) { a, b -> println("My pair: $a, $b") }.toList()
```

```sh
My pair: 1, One
My pair: 2, Two
My pair: 3, Three
My pair: 4, Four
My pair: 5, Five
```

On peut également `unzip()` :

```kotlin
val sequenceAB = sequenceOf(
  1 to "One",
  2 to "Two",
  3 to "Three",
  4 to "Four",
  5 to "Five"
)

val (sequenceA, sequenceB) = sequenceAB.unzip()
```

#### Association

Les *associations* permettent de générer des `Map` en associant les valeurs d'une collection avec les résultats d'un lambda.

`associateWith{ K -> V }` associe les résultats du lambda en tant que valeurs de la Map.

```kotlin
val sequence = sequenceOf(1, 2, 3, 4, 5)
val map = sequence.associateWith { (it * 2).toString(16) }
map.forEach { println(it) }
```

```kotlin
1=2
2=4
3=6
4=8
5=a
```

`associateBy{ T -> K }` associe les résultats du lambda en tant que clés de la Map.

```kotlin
val sequence = sequenceOf(1, 2, 3, 4, 5)
val map = sequence.associateBy { (it * 2).toString(16) }
map.forEach { println(it) }
```

```kotlin
2=1
4=2
6=3
8=4
a=5
```

> Note that `associate()` produces short-living `Pair` objects which may affect the performance. Thus, `associate()` should be used when the performance isn't critical or it's more preferable than other options.

#### Flatten

Il arrive régulièrement que des collections soient imbriqués et qu'on veut qu'une collection de 1 dimension. On utilise alors `flatten()` pour **aplatir** la collection.

```kotlin
val sequence = sequenceOf(listOf(1, 2), listOf(3, 4, 5), listOf(6))
val sequenceFlattened = sequence.flatten()
sequenceFlattened.forEach { println(it) }
```

```sh
1
2
3
4
5
6
```

On peut également faire un `flatMap` pour mapper avant de flatten :

```kotlin
data class Container<T>(val interestingValue: T, val trashValue: String = "FooBar")
val sequence = sequenceOf(
  Container(listOf(1, 2)),
  Container(listOf(3, 4, 5)),
  Container(listOf(6))
)
val sequenceFlattened = sequence.flatMap { it.interestingValue.asSequence() }
sequenceFlattened.forEach { println(it) }
```

#### Représentation String

`joinToString()` transforme la collection en String. Il contient plusieurs paramètres permettant de configurer la transformations :

- `separator: CharSequence = ", "`, séparateur entre chaque valeur
- `prefix: CharSequence = "" et postfix: CharSequence = ""`, préfix et suffixe du résultat final
- `limit: Int = -1` et `truncated: CharSequence = "..."` limite de caractère et affiche `truncated` si la limite est atteinte.
- `transform: ((T) -> CharSequence)? = null` applique une transformation

```kotlin
val sequence = sequenceOf(1, 2, 3, 4, 5)
println(sequence.joinToString(
  separator = " | ",
  prefix = "start :",
  postfix = " end",
  limit = 4,
  truncated = "[...]"
) { (it * 2).toString() })
```

```sh
start: 2 | 4 | 6 | 8 | [...] end
```

### Filtrages

#### Par prédicats

`filter{ T -> Boolean }` filtre en fonction du résultat que donne le lambda.

```kotlin
val sequence = sequenceOf(1, 2, 3, 4, 5)
sequence.filter { it % 2 == 0 }.forEach{ println(it) }
```

```sh
2
4
```

Il existe également `filterIndexed{ index: Int, T -> Boolean }` qui permet d'obtenir l'indice de chaque valeur de la collection.

`filterNot{ T -> Boolean }` filtre si le résultat est `false`.

`filterIsInstance<R>()` filtre en fonction du type.

#### Partitionnage

`partition{ T -> Boolean }` retourne une `Pair` de 2 `List`, où la première est la collection dont le lambda retourne `true` et la second est la collection dont le lambda retourne `false`.

```kotlin
val sequence = sequenceOf(1, 2, 3, 4, 5)
val (pair, impair) = sequence.partition { it % 2 == 0 }
println(pair)
println(impair)
```

```sh
[2, 4]
[1, 3, 5]
```

### Plus et Minus

`+` et `-` accepte une collection ou une valeur. Elle ajoute ou enlève à la collection.

```kotlin
val numbers = listOf("one", "two", "three", "four")

val plusList = numbers + "five"
val minusList = numbers - listOf("three", "four")
println(plusList)
println(minusList)
```

```sh
[one, two, three, four, five]
[one, two]
```

### Groupage

`groupBy{ T -> K }` génère une `Map` dont les clés sont les résultats du lambda :

```kotlin
val sequence = sequenceOf(1, 2, 3, 4, 5)
val map = sequence.groupBy { it % 2 }
println(map)
```

```sh
{1=[1, 3, 5], 0=[2, 4]}
```

### Opérations d'agrégat

#### Communs

Les plus commun sont `min`, `max`, `count`, `average`, `sum`.

```kotlin
val numbers = listOf(6, 42, 10, 4)

println("Count: ${numbers.count()}")
println("Max: ${numbers.max()}")
println("Min: ${numbers.min()}")
println("Average: ${numbers.average()}")
println("Sum: ${numbers.sum()}")
```

```sh
Count: 4
Max: 42
Min: 4
Average: 15.5
Sum: 62
```

On peut également spécifier la caractéristique avec `maxBy`/`minBy` :

```kotlin
val numbers = listOf(5, 42, 10, 4)
val min3Remainder = numbers.minBy { it % 3 }
println(min3Remainder)
```

```sh
42
```

On peut également spécifier le comparateur avec `maxWith`/`manWith` :

```kotlin
val strings = listOf("one", "two", "three", "four")
val longestString = strings.maxWith(compareBy { it.length })
println(longestString)
```

```sh
three
```

Pareil pour la sommation avec `sumBy` :

```kotlin
val numbers = listOf(1, 2, 3, 4)
println(numbers.sumBy { it * 2 })
```

```sh
20
```

#### Fold et reduce

`fold(initial: R) {acc: R, T -> R }` et `reduce{ acc: S, T -> S }` traite la collection séquentiellement et renvoie le résultat accumulé.

`fold` diffère de `reduce` où `fold` prend une valeur initiale alors que `reduce` prend la première valeur de la collection.

```kotlin
val sequence = sequenceOf(1, 2, 3, 4, 5)
val sum = sequence.reduce { acc, it -> acc + it }
println(sum)

val offsetSum = sequence.fold(15) { acc, it -> acc + it }
println(offsetSum)
```

```kotlin
15
30
```

On peut également faire avec l'indice en utilise `foldIndexed(initial: R) { index: Int, acc: R, T -> R }` et `reduceIndexed{ index: Int, acc: R, T -> R }`.

## 7.4 Utiliser des fonctions d'ordre supérieur

`with`, `also`, `apply`, `let` et `run` sont des fonctions d'ordre supérieur.

Chacun diffère sur :

- Le receveur en paramètre ou par extension
- Les paramètres appliqué sur le lambda (avec le receveur en `it` ou en `this`)
- La valeur retourné (Receveur VS valeur du lambda)

![Image for post](https://miro.medium.com/max/504/1*t3hR3BuuWySMGdcN5SNhXg.png)

Leurs implémentations simplifiées :

```kotlin
inline fun <T, R> with(receiver: T, block: T.() -> R): R {
    return receiver.block()
}
inline fun <T> T.also(block: (T) -> Unit): T {
    block(this)
    return this
}
inline fun <T> T.apply(block: T.() -> Unit): T {
    block()
    return this
}
inline fun <T, R> T.let(block: (T) -> R): R {
    return block(this)
}
inline fun <T, R> T.run(block: T.() -> R): R {
    return block()
}
```

Leurs usages :

```kotlin
package org.example.sandbox

import kotlin.random.Random

data class Person(var name: String? = null, var age: Int? = null) {
    companion object {
        fun maybe(): Person? {
            return if (Random.nextBoolean()) {
                Person()
            } else {
                null
            }
        }
    }
}

fun main() {
    val person = Person("John", 32)
    val name = with(person) {  // Return result of the block, doesn't check null
        // This = Person
        println(this.name)
        println(this.age)
        this.name
    }

    // Usecase : Applique le lambda sans modifier de données et retourne l'instance
    val person2 = Person.maybe()?.also {
        // it = Person
        println(it.name)
        println(it.age)
    }

    // Usecase : Modification des propriétés et clonage
    val person3 = Person.maybe()?.apply {
        // This = Person
        this.name = "John"
        this.age = 32
    }

    // Usecase : Applique le lambda / Convertit / Null-check
    val name2 = Person.maybe()?.let{  // Return result of the block
        // it = Person
        print(it.name)
        print(it.age)
        it.name
    }

    // Usecase : Changer de Scope
    val name3 = Person.maybe()?.run{  // Return result of the block
        // This = Person
        print(this.name)
        print(this.age)
        this.name
    }
}
```

