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
    val name = with(person) { // Return result of the block, doesn't check null
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
    val name2 = Person.maybe()?.let { // Return result of the block
        // it = Person
        print(it.name)
        print(it.age)
        it.name
    }

    // Usecase : Changer de Scope
    val name3 = Person.maybe()?.run { // Return result of the block
        // This = Person
        print(this.name)
        print(this.age)
        this.name
    }
}
