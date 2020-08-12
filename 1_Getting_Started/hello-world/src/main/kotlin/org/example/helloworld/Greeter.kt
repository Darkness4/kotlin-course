package org.example.helloworld

class Greeter(private val nom: String) {
    fun greet() {
        println("Hello $nom!")
    }
}
