package day15

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Matrix2
import util.ResLoader

class Day15Test {
    val input = ResLoader.readlines("input_test_15")
    val matrix = Matrix2.fromInput(input)

    @Test
    fun `find path with lowest risk test`() {
        val riskLevel = findLowestRiskLevel(matrix)

        assertThat(riskLevel).isEqualTo(40)
    }

    @Test
    fun `find path with lowest risk in enlarged matrix`() {
        val enlargedMatrix = enlarge(matrix)

        val riskLevel = findLowestRiskLevel(enlargedMatrix)

        assertThat(riskLevel).isEqualTo(315)
    }
}
