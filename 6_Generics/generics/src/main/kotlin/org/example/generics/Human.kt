package org.example.generics

class Human<out T : Weapon>(val weapon: T) {
    fun giveWeapon(): T {
        return weapon
    }
    fun destroyWeapon(destroyer: Destroyer<T>) { // Type Safe !
        destroyer.destroy(weapon)
        println("destroyed")
    }
    inline fun <reified R : Weapon> hasWeaponOfType() = weapon is R
}
