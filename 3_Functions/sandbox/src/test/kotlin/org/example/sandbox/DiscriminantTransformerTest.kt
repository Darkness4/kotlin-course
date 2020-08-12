package org.example.sandbox

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlin.random.Random

class DiscriminantTransformerTest : WordSpec({
    val mockDiscriminateur = mockk<Discriminateur>()
    val discriminantTransformer = DiscriminantTransformer(mockDiscriminateur)

    afterTest {
        clearMocks(mockDiscriminateur)
    }

    "DiscriminantTransformer" should {
        "convert multiple a List of EquationDegre2 into a List of Discriminants" {
            // Arrange
            every { mockDiscriminateur(any()) } returns Random.nextDouble()
            val input = List(50) { EquationDegre2(Random.nextInt(), Random.nextInt(), Random.nextInt()) }

            // Act
            val result = discriminantTransformer(input)

            // Assert
            result.forEach {
                it.shouldBeTypeOf<Double>()
            }
        }

        "return a list of same size as the input" {
            // Arrange
            every { mockDiscriminateur(any()) } returns Random.nextDouble()
            val input = List(50) { EquationDegre2(Random.nextInt(), Random.nextInt(), Random.nextInt()) }

            // Act
            val result = discriminantTransformer(input)

            // Assert
            result.size shouldBe input.size
        }
    }
})