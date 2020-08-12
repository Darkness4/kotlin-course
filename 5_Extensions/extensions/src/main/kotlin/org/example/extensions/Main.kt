package org.example.extensions

class AquariumPlant

fun main() {
    fun AquariumPlant?.pull() {
        this?.apply {
            println("removing $this")
        }
        println("Done.")
    }

    val plant: AquariumPlant? = null
    plant.pull()
}
