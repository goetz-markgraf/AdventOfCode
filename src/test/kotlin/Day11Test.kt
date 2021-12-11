import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11Test {
    private val input = ResLoader.readlines("input_test_11")

    @Test
    fun `process one day of charging`() {
        val matrix = matrixFromInput(input)

        matrix.processCharging()
        assertThat(matrix[0][2]).isEqualTo(9)

        val step1 = matrix.processFlashing()
        assertThat(step1).isEqualTo(0)

        matrix.processCharging()
        assertThat(matrix[0][2]).isEqualTo(10)

        val step2 = matrix.processFlashing()
        assertThat(step2).isEqualTo(35)
        assertThat(matrix[0][2]).isEqualTo(0)
    }

    @Test
    fun `process 10 steps`() {
        val matrix = matrixFromInput(input)

        val result = (0..9).sumOf {
            matrix.processCharging()
            matrix.processFlashing()
        }

        assertThat(result).isEqualTo(204)
    }

    @Test
    fun `process 100 steps`() {
        val matrix = matrixFromInput(input)

        val result = (0..99).sumOf {
            matrix.processCharging()
            matrix.processFlashing()
        }

        assertThat(result).isEqualTo(1656)
    }

    @Test
    fun `find the first step with 100 flashes`() {
        val matrix = matrixFromInput(input)

        var cont = true
        var count = 0
        while (cont) {
            count++
            matrix.processCharging()
            if (matrix.processFlashing() == 100)
                cont = false
        }

        assertThat(count).isEqualTo(195)
    }
}
