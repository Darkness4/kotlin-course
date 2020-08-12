package org.example.sandbox

import io.kotest.core.spec.style.WordSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class ChocolateDrawerTest : WordSpec() {
    lateinit var blackChocolates: MutableList<Chocolate>
    lateinit var whiteChocolates: MutableList<Chocolate>
    lateinit var milkChocolates: MutableList<Chocolate>
    lateinit var chocolateDrawer: ChocolateDrawer

    init {
        beforeTest() {
            blackChocolates = mutableListOf()
            whiteChocolates = mutableListOf()
            milkChocolates = mutableListOf()
            chocolateDrawer = ChocolateDrawer(
                blackChocolates,
                whiteChocolates,
                milkChocolates
            )
        }

        "fillIn" should {
            "sort and fill 3 types of chocolate into 3 list" {
                val input = Array(20) { row(List(100) { giveRandomChocolate() }) }

                forAll(*input) { listOfChocolate ->
                    // Act
                    chocolateDrawer.fillIn(listOfChocolate)

                    // Assert
                    whiteChocolates.size shouldBe listOfChocolate.count { it == Chocolate.WHITE }
                    blackChocolates.size shouldBe listOfChocolate.count { it == Chocolate.BLACK }
                    milkChocolates.size shouldBe listOfChocolate.count { it == Chocolate.MILK }
                }
            }
        }
    }
}