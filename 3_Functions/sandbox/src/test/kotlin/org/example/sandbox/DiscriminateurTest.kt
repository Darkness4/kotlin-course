package org.example.sandbox

import io.kotest.core.spec.style.WordSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class DiscriminateurTest : WordSpec({
    val discriminateur = Discriminateur()

    "Discriminateur" should {
        "should calculateur the right Discriminant" {
            forAll(
                row(EquationDegre2(1, 2, 3), -8.0),
                row(EquationDegre2(1389247, 124, 3), -16655588.0),
                row(EquationDegre2(0, 1, 1), 1),
                row(EquationDegre2(0, -1, 0), 1),
                row(EquationDegre2(-1, -1, 1), 5),
                row(EquationDegre2(1, -1, 5), -19)
            ) { equation, expected ->
                // Act
                val result = discriminateur(equation)

                // Assert
                result shouldBe expected
            }
        }
    }
})