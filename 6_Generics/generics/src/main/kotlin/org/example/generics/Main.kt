package org.example.generics

class Destroyer<in T : Weapon> {
    fun destroy(weapon: T) {}
}

fun main() {
    val human = Human(Spear())

    fun killHuman(human: Human<Weapon>) { /* ... */ }
    killHuman(human) // Type safe !

    println(human.hasWeaponOfType<Sword>())
}
