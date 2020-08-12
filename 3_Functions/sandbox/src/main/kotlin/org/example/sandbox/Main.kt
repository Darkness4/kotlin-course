package org.example.sandbox

import kotlin.random.Random

enum class Chocolate {
    WHITE, BLACK, MILK
}

fun giveRandomChocolate(seed: Int? = null): Chocolate {
    val choice = seed?.let {
        Random(it).nextInt(Chocolate.values().size)
    } ?: Random.nextInt(Chocolate.values().size)

    return Chocolate.values()[choice]
}

fun main() {
    println(giveRandomChocolate())

    val sequence = sequenceOf("Hello", "World", "!").map {
        println(it)
        it
    }
    sequence.forEach { println(it) }
}

open class HighLevel
open class MiddleLevel : HighLevel()
final class LowLevel : MiddleLevel()

class Producer<out T : MiddleLevel>(val test: T) {
    fun print(): T {
        return T()
    }
}

class Consumer<in T : MiddleLevel> {
    fun print(test: T) {
        when (test) {
            is LowLevel -> println("LowLevel")
            is MiddleLevel -> println("MiddleLevel")
            is HighLevel -> println("HighLevel")
        }
        return test
    }
}

class Generics<T : MiddleLevel>(val test: T) {
    fun print(): T {
        when (test) {
            is HighLevel -> println("HighLevel")
            is MiddleLevel -> println("MiddleLevel")
            is LowLevel -> println("LowLevel")
        }
        return test
    }
}
