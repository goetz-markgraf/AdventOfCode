package day20

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.Point
import util.ResLoader

class Day20Test {
    private val enhancementPattern = ResLoader.readlines("input_test_20").first()
    private val inputImage = ResLoader.readlines("input_test_20").drop(2)
    private val initialSet = createBitSet(inputImage)


    @Test
    fun `create a boolean array`() {
        val input = "..##..##"
        val result = createBoolArray(input)

        assertThat(result).hasSize(8).containsExactly(
            false, false,
            true, true,
            false, false,
            true, true
        )
    }

    @Test
    fun `check test input boolean array`() {
        val result = createBoolArray(enhancementPattern)

        assertThat(result).hasSize(512)
        assertThat(result.first()).isFalse()
        assertThat(result.last()).isTrue()
        assertThat(result[34]).isTrue()
    }

    @Test
    fun `create the bit set`() {
        val input = listOf(
            "#..#",
            ".##."
        )

        val result = createBitSet(input)

        assertThat(result).containsExactly(
            Point(0, 0),
            Point(3, 0),
            Point(1, 1),
            Point(2, 1)
        )
    }

    @Test
    fun `check test input pattern`() {
        val result = createBitSet(inputImage)

        assertThat(result).hasSize(10)

        printImage(result)
    }

    @Test
    fun `check enhancement index for center point in pattern`() {
        val result = getEnhancementIndexForPixel(Point(2, 2), initialSet)

        assertThat(result).isEqualTo(34)
    }

    @Test
    fun `get enhancement index for -1 2`() {
        val result = getEnhancementIndexForPixel(Point(2, -1), initialSet)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `find maximum indices to enhance`() {
        val result = findIndexRangesForEnhancement(initialSet)

        assertThat(result).isEqualTo(
            Pair(
                -1..5,
                -1..5
            )
        )
    }


    @Test
    fun `calculate number of lit pixels after two enhancements`() {
        val alg = createBoolArray(enhancementPattern)

        val result = (1..2).fold(initialSet) { set, _ ->
            enhance(set, alg)
        }

        assertThat(result).hasSize(35)

        printImage(result)
    }
}
