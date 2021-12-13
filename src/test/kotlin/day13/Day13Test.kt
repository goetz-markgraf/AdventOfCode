package day13

import FoldInstruction
import util.Point
import doFold
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import parseTransparentInput
import pointsToString
import util.ResLoader

class Day13Test {
    val input = ResLoader.readlines("input_test_13")

    @Test
    fun `parse test input`() {
        val parseResult = parseTransparentInput(input)

        assertThat(parseResult.points).hasSize(18)
            .last().isEqualTo(Point(9, 0))

        assertThat(parseResult.foldInstructions).hasSize(2)
            .element(0).isEqualTo(FoldInstruction(true, 7))
    }

    @Test
    fun `fold test input`() {
        val parseResult = parseTransparentInput(input)

        val result = doFold(parseResult.points, parseResult.foldInstructions.first())

        assertThat(result).hasSize(17)
    }

    @Test
    fun `fold until letter comes out`() {
        val parseResult = parseTransparentInput(input)

        val result = parseResult.foldInstructions.fold(parseResult.points) {
            points, next ->
            doFold(points, next)
        }

        assertThat(result).hasSize(16)

        val text = pointsToString(result)

        assertThat(text).isEqualTo("""
            █████
            █   █
            █   █
            █   █
            █████
            
        """.trimIndent())
    }
}
