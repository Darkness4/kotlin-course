package org.example.helloworld

fun main(args: Array<String>) {
    val name = try {
        args.first()
    } catch (e: NoSuchElementException) {
        "unknown man"
    }
    val greeter = Greeter(name)
    greeter.greet()
}
