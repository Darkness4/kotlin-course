package org.example.generics

open class Weapon {
    open fun attack() {
        println("Punching")
    }
}

class Sword : Weapon() {
    override fun attack() {
        println("Slashing !")
    }
}

class Spear : Weapon() {
    override fun attack() {
        println("Piercing !")
    }
}

class Axe : Weapon() {
    override fun attack() {
        println("Cutting !")
    }
}
