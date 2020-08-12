## 2. Kotlin Basics

## 2.1 Tâche : Importer le projet

Importez `sandbox` dans IntelliJ IDEA.

Le `build.gradle` est réglé pour compiler avec `gradlew build`.

De plus, il est possible de compiler+lancer avec `gradlew runApp`

## 2.1 Tâche : Explorer les types et opérateurs

Les types :

- Nombres : `Byte`, `Short`, `Int`, `Long`, `Float`, `Double`
- String : `String`, `Char`
- Collections : `Array`, `List`, `MutableList`, `Set`, `MutableSet`, `Map`, `MutableMap`

Les opérateurs :

> Kotlin supports the following operators and special symbols:
>
> - `+`, `-`, `*`, `/`, `%` - mathematical operators
>   - `*` is also used to [pass an array to a vararg parameter](https://kotlinlang.org/docs/reference/functions.html#variable-number-of-arguments-varargs)
> - `=`
>   - assignment operator
>   - is used to specify [default values for parameters](https://kotlinlang.org/docs/reference/functions.html#default-arguments)
> - `+=`, `-=`, `*=`, `/=`, `%=` - [augmented assignment operators](https://kotlinlang.org/docs/reference/operator-overloading.html#assignments)
> - `++`, `--` - [increment and decrement operators](https://kotlinlang.org/docs/reference/operator-overloading.html#increments-and-decrements)
> - `&&`, `||`, `!` - logical 'and', 'or', 'not' operators (for bitwise operations, use corresponding [infix functions](https://kotlinlang.org/docs/reference/basic-types.html#operations))
> - `==`, `!=` - [equality operators](https://kotlinlang.org/docs/reference/operator-overloading.html#equals) (translated to calls of `equals()` for non-primitive types)
> - `===`, `!==` - [referential equality operators](https://kotlinlang.org/docs/reference/equality.html#referential-equality)
> - `<`, `>`, `<=`, `>=` - [comparison operators](https://kotlinlang.org/docs/reference/operator-overloading.html#comparison) (translated to calls of `compareTo()` for non-primitive types)
> - `[`, `]` - [indexed access operator](https://kotlinlang.org/docs/reference/operator-overloading.html#indexed) (translated to calls of `get` and `set`)
> - `!!` [asserts that an expression is non-null](https://kotlinlang.org/docs/reference/null-safety.html#the--operator)
> - `?.` performs a [safe call](https://kotlinlang.org/docs/reference/null-safety.html#safe-calls) (calls a method or accesses a property if the receiver is non-null)
> - `?:` takes the right-hand value if the left-hand value is null (the [elvis operator](http://kotlinlang.org/docs/reference/null-safety.html#elvis-operator))
> - `::` creates a [member reference](https://kotlinlang.org/docs/reference/reflection.html#function-references) or a [class reference](https://kotlinlang.org/docs/reference/reflection.html#class-references)
> - `..` creates a [range](https://kotlinlang.org/docs/reference/ranges.html)
> - `:` separates a name from a type in declarations
> - `?` marks a type as [nullable](https://kotlinlang.org/docs/reference/null-safety.html#nullable-types-and-non-null-types)
> - `->`
>   - separates the parameters and body of a [lambda expression](https://kotlinlang.org/docs/reference/lambdas.html#lambda-expression-syntax)
>   - separates the parameters and return type declaration in a [function type](https://kotlinlang.org/docs/reference/lambdas.html#function-types)
>   - separates the condition and body of a [when expression](http://kotlinlang.org/docs/reference/control-flow.html#when-expression) branch
> - `@`
>   - introduces an [annotation](https://kotlinlang.org/docs/reference/annotations.html#usage)
>   - introduces or references a [loop label](https://kotlinlang.org/docs/reference/returns.html#break-and-continue-labels)
>   - introduces or references a [lambda label](https://kotlinlang.org/docs/reference/returns.html#return-at-labels)
>   - references a ['this' expression from an outer scope](https://kotlinlang.org/docs/reference/this-expressions.html#qualified)
>   - references an [outer superclass](https://kotlinlang.org/docs/reference/classes.html#calling-the-superclass-implementation)
> - `;` separates multiple statements on the same line
> - `$` references a variable or expression in a [string template](https://kotlinlang.org/docs/reference/basic-types.html#string-templates)
> - `_`
>   - substitutes an unused parameter in a [lambda expression](https://kotlinlang.org/docs/reference/lambdas.html#underscore-for-unused-variables-since-11)
>   - substitutes an unused parameter in a [destructuring declaration](http://kotlinlang.org/docs/reference/multi-declarations.html#underscore-for-unused-variables-since-11)

Observer et tester les effets dans le Main.kt.

1. Regardez et testez vos connaissances mathématique avec les nombres

   ```kotlin
   1 + 1  // 2: Int
   1 - 1  // 0: Int
   1.0  // 1.0: Double
   1.0f  // 1.0: Float
   1L  // 1: Float
   32 / 3  // 10: Int
   32.0 / 3 // 10.666666666666666: Double
   var integer = 1
   integer += 2  // interger1 = 3
   integer++  // interger1 = 4
   
   val truey = true
   truey && false || false  // && = AND, || = OR
   ```

2. Testez et observez la différence entre `val` et `var`. `val` déclare une variable **immutable** (qui ne peut être assigné qu'une seule fois).

   ```kotlin
   var integer = 1
   integer = 2
   val integerImmutable = 1
   integerImmutable = 2  // Error
   ```

3. Testez les String et format-String

   ```kotlin
   'c'  // 'c': Char
   "String"  // "String": String
   val string = """Multi-line
   String"""
   val string2 = "F-String $string"
   ```

4. Testez l'opérateur range `..`

   ```kotlin
   for (i in 1..20) {
       // Do
   }
   
   if (i in 1..20) {  // if [1, 20] contains i, then
       // Do
   }
   ```

5. **Testez l'égalité par données et l'égalité par références**. La première vérifie qu'un containeur ait les même données qu'un autre. La deuxième vérifie que l'instance soit la même.

   ```kotlin
   val data1 = listOf(1..50)
   val data2 = listOf(1..50)
   val data3 = listOf(1..49)
   val sameData1 = data1
   
   // Data Equality
   val dataTruey = data1 == data2
   dataTruey.describe("data1 == data2")
   
   val dataFalsey = data1 == data3
   dataFalsey.describe("data1 == data3")
   
   // Reference Equality
   val referenceFalsey = data1 === data2
   referenceFalsey.describe("data1 === data2")
   
   val referenceTruey = sameData1 == data1
   referenceTruey.describe("sameData1 === data1")
   ```

   Ici, referenceTruey est `true`, car modifier `sameData1 ` revient à modifier `data1`

6. Testez les comparaisons `<`, `<=`, `==`, `>=`, `>`

7. **Testez et observez bien `Nullable`**. Chaque variable est non-null par défaut. En clair, aucune variable n'accepte `null`. Pour qu'une variable puisse accepter `null`, il faut ajout un `?`.

   ```kotlin
   val nonNullableByDefault: Int = null // Error: Null can not be a value of a non-null type Int
   val nonNullableByDefault: Int = 4 // Yes
   
   var nullable: Int? // Initialisé null
   nullable = 32
   nullable = null // Okay
   ```

   Pour traiter `null`, il existe plusieurs operateurs permettant d'éviter de nombreux soucis :

   ```kotlin
   // Bad in general
   var output: Int? = null
   if (nullable != null) {
       val data = nullable.method()
       if (data != null) {
           output = data.method()
       } else {
           throw KotlinNullPointerException
       }
   } else {
       throw KotlinNullPointerException
   }
   if (output == null) {
     throw KotlinNullPointerException
   }
   
   // Bad answer
   // object?.method() exécute method is object n'est pas null. Sinon, n'exécute pas et retourne null.
   val output: Int? = nullable?.method()?.method()  // C'est quand même nullable.
   if (output == null) {
       throw KotlinNullPointerException
   }
   
   // Equivalent
   // object!!.method() exécute method is object n'est pas null. Sinon, throws.
   // object!! vérifie que object est non-null. Sinon, throws.
   val outputNonNullable: Int = nullable!!.method()!!.method()!!  // throws if null
   
   // Autre cas d'utilisation
   // Bad
   val output: Int? = nullable?.method()?.method()  // C'est quand même nullable.
   if (output == null) {
       output = 3
   }
   
   // Good
   val output: Int = nullable?.method()?.method() ?: 3  // Si null, retourne 3
   ```

8. Testez et apprenez les divers structures de données. Par défaut, les collections sont **immutables** (ne peuvent s'agrandir/rétrécir). Une collection mutable est précédé de **Mutable**

   ```kotlin
   val myAlphabetWhenIWas5 = listOf("a", "b", "c")
   val myAlphabetGrown = mutableListOf("a", "b", "c")
   myAlphabetGrown.add("d") // Okay
   ```

   Les **Arrays** n'ont pas de version mutable. De plus, l'égalité par défaut est sur l'instance. Pour comparer en terme de données, il faut utiliser la méthode `contentEquals`.

   Les **Sets** sont des collections non ordonnées.

   Les **Maps (HashMap, TreeMap, LinkedHashMap)** sont des collections de paires clés-valeurs. **Map** est l'interface immutable. **MutableMap** est l'interface mutable. **HashMap** est une implémentation de MutableMap, où l'ordre d'insertion est pris en compte. **TreeMap** est une implémentation de MutableMap, où l'ordre des valeurs est triées en fonction de la clé.

   La fonction `mapOf` retourne une Map (immutable).

   La fonction `mutableMapOf` retourne une MutableMap.

   

   