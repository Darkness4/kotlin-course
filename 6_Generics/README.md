# 6. Generics

## Introduction

Les *generics* permettent de travailler sur des types qui vont être *définies plus tard*. Ces types vont être spécifiés en paramètres de la classe.

Pour définir un generic, on met un nom entre chevrons `<Type>`. Généralement, on met `T` par convention :

```kotlin
class Class<T> {
    fun get(): T {
        // DO
    }
    fun addItem(item: T) {
        // DO
    }
}
```

## Faire un nouveau projet

Comme d'habitude :

- **Gradle > Kotlin/JVM**, appelez-le `org.example.generics`

- Ajoutez : 

  ```kotlin
  jar {
      manifest {
          attributes(
              'Main-Class': 'org.example.generics.MainKt'
          )
      }
  
      dependsOn configurations.runtimeClasspath
      from {
          configurations.runtimeClasspath.findAll { it.name.endsWith('jar') }.collect { zipTree(it) }
      }
  }
  
  task runApp(type: JavaExec) {
      classpath = sourceSets.main.runtimeClasspath
  
      main = 'org.example.generics.MainKt'
  }
  ```


## Tester les generics

### Faire des classes

1. Faites un package `org.example.generics`
2. Faites une open class `Weapon` et des sous-classes de `Weapon`
3. Ajoutez également une fonction `attack` dans chaque classe :

   ```kotlin
   open class Weapon {
       open fun attack() {
           println("Punching")
       }
   }
   
   class Sword: Weapon() {
       override fun attack() {
           println("Slashing !")
       }
   }
   
   class Spear: Weapon() {
       override fun attack() {
           println("Piercing !")
       }
   }
   
   class Axe: Weapon() {
       override fun attack() {
           println("Cutting !")
       }
   }
   ```

4. Faites une classe `Human` dépendant de `T` tel que :

   ```kotlin
   // Human.kt
   package org.example.generics
   
   class Human<T>(val weapon: T)
   ```

5. Créez le Main et testez avec un main :

   ```kotlin
   // Main.kt
   package org.example.generics
   
   fun main() {
       val human = Human<Sword>(Sword())  // ou simplement Human(Sword())
       human.weapon.attack()
   }
   ```

Actuellement, `Human` est capable d'accepter tout type dont `null`.

Pour rendre le generic plus précis, on spécifie le type de generic :

```kotlin
class Human<T: Weapon?>(val weapon: T)  // weapon est nullable
```

On va préférer non-null :

```kotlin
class Human<T: Weapon>(val weapon: T)
```

## Covariance, Contravariance et Invariance (`out` et `in`)

### Covariance

La covariance est le fait de pouvoir traiter une sous-classe comme sa super-classe. C'est la forme classique du polymorphisme en OOP, car il se base sur le principe même du sous-typage. 

Par exemple :

- Une `List<Axe>` est une `List<Weapon>`. Ou simplement : `val list: List<Weapon> = listOf<Axe>()`

Comme `Axe` est un sous-type de `Weapon`, on a gardé l'ordre des types. `List<T>` est donc covariant sur `T`.

Dans l'exemple de la partie précédente, on peut assumer que **`Human<Weapon>` est covariant sur `Weapon`.**

Pour marquer la covariance, on marque le generic avec `out`. `out` oblige que le generic est uniquement en sortie de fonction de la classe du generic.

La classe est donc un **producteur**.

```kotlin
class Human<out T: Weapon>(val weapon: T) {
    fun giveWeapon(): T {
        return weapon
    }
    fun takeWeapon(weapon: T) {}  // Error : Type parameter T is declared as 'out' but occurs in 'in' position in type T
}
```

Cela permet également appliquer des fonctions de manière sécurisé sur `Human<Weapon>` :

```kotlin
val human = Human(Sword())  // human: Human<Sword>

fun killHuman(human: Human<Weapon>) { /* ... */ }

killHuman(human)  // Type safe !
```

Sans `out`, on a :

```kotlin
Type mismatch.
Required:
Human<Weapon>
Found:
Human<Sword>  // human est Human<Sword>, car val human = Human(Sword()). 
// On a besoin de cast : val human: Human<Weapon> = Human(Sword())
```

### Contravariance

La contravariance est le faire de pouvoir traiter une super-classe comme sa sous-classe.

Par exemple :

- Une `Action<Weapon>` est une `Action<Axe>`. Ou simplement : `val action: Action<Axe> = Action<Weapon>()`

L'ordre des types est inversé, donc `Action<Weapon>` est contravariant sur `Weapon`.

Dans l'exemple de la partie précédente, on faire un `Destroyer<in Weapon>` avec une méthode `fun destroy(weapon: T)`. `in` est le marqueur pour la contravariance.

La classe devient donc un **consommateur.** Cela permet donc de faire ceci :

```kotlin
class Destroyer<in T: Weapon> {
    fun destroy(weapon: T) {}
}

class Human<out T: Weapon>(val weapon: T) {
    fun giveWeapon(): T {
        return weapon
    }
    fun destroyWeapon(destroyer: Destroyer<T>) {
        destroyer.destroy(weapon)
        println("destroyed")
    }
}
```

Si vous retirez le `in`, on obtient cette erreur :

```kotlin
fun destroyWeapon(destroyer: Destroyer<T>) {  // Type parameter T is declared as 'out' but occurs in 'invariant' position in type Destroyer<T>

// Car, par exemple, si T = Spear, on aurait
fun destroyWeapon(destroyer: Destroyer<Spear>)

// Or, on aimerait mettre un Destroyer<Weapon> en argument.
// Mais comme, il n'y pas de `in`, on ne peut pas faire `destroyer: Destroyer<Spear> = Destroyer<Weapon>`
```

## Fonctions génériques

### Déclaration et appel

Ce n'est pas très compliqué :

```kotlin
fun <T: Weapon> destroy(weapon: T) {}

// Usage
destroy<Sword>(Sword())
```

### Utiliser T dans une méthode générique

Pour cela, on marque le générique avec `reified` (transforme en objet) et on marque la fonction avec `inline`. Normalement, comme les génériques sont accessibles uniquement lors de la compilation, `inline` permet que le type générique soit disponible à l'exécution.

```kotlin
class Human<out T: Weapon>(val weapon: T) {
    inline fun <reified R: Weapon> hasWeaponOfType() = weapon is R
}
```

Si, vous n'utilisez pas `reified`, `R` sera un type effacé lors de la compilation pour assurer la sureté des types. Par exemple, `List<Foo>` devient `List<*>`.