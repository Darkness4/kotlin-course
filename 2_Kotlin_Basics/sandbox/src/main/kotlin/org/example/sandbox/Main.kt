package org.example.sandbox

fun Any.describe(name: String) {
    println("$name = $this as ${javaClass.kotlin.qualifiedName}")
}

fun main() {
    // Numerics
    (1 + 1).describe("1 + 1")
    (1 - 1).describe("1 - 1")
    1.0.describe("1.0")
    1.0f.describe("1.0f")
    1L.describe("1L")
    (32 / 3).describe("32 / 3")
    (32.0 / 3).describe("32.0 / 3")
    var integer1 = 1
    integer1.describe("Before integer1 += 2")
    integer1 += 2
    integer1.describe("After integer1 += 2")

    var integer2 = 1
    integer2.describe("Before integer2--")
    integer2--
    integer2.describe("After integer2--")

    // Boolean
    val truey = true
    truey.describe("truey")
    (truey && false || false).describe("(truey && false || false)")

    // Strings
    'c'.describe("c")
    "String".describe("String")
    "F-String ${41 + 1}".describe("F-String \${41 + 1}")

    // Range
    for (i in 1..20) {
        print(i)
    }
    println()
    (10 in 1..20).describe("(10 in 1..20)")

    // Comparison
    (integer1 < integer2).describe("($integer1 < $integer2)")

    val container1 = listOf("Choco", "Lat")
    val container2 = listOf("Choco", "Lat")
    val container3 = listOf("Cerise", "Rouge")
    val refContainer1 = container1

    (container1 == container2).describe("($container1 == $container2)")
    (container1 == container3).describe("($container1 == $container3)")
    (container1 == refContainer1).describe("($container1 == $refContainer1)")

    (container1 === container2).describe("(c1: $container1 === c2: $container2)")
    (container1 === refContainer1).describe("(c1: $container1 === ref_c1: $refContainer1)")

    // Nullable
    val nonNull = 5
    var nullable: Int? = 5
    nullable = null // Okay
    nullable = 32
    nullable = null

    // Return null if object is null
    var result1: Double? = nullable?.toDouble()

    // Return default if object is null
    var result2: Double = nullable?.toDouble() ?: 5.0

    // Throws if object is null
    try {
        var result3: Double = nullable!!.toDouble()
    } catch (e: KotlinNullPointerException) {
        println("$e")
    }

    try {
        var result4: Int = nullable!!
    } catch (e: KotlinNullPointerException) {
        println("$e")
    }
}
