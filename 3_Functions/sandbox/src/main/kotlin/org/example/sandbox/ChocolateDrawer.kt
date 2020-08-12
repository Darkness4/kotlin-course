package org.example.sandbox

data class ChocolateDrawer(
    val blackChocolates: MutableList<Chocolate>,
    val whiteChocolates: MutableList<Chocolate>,
    val milkChocolates: MutableList<Chocolate>
) {
    fun fillIn(chocolates: List<Chocolate>) {
        TODO("Not yet implemented. Use 'when' and 'forEach'")
    }
}
