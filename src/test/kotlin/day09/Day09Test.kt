package day09

import util.RC
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.ResLoader

class Day09Test {
    private val input = ResLoader.readlines("input_test_09")
    private val matrix = createHeightMatrix(input)

    private val testLowSpots = listOf(
        RC(0, 9),
        RC(0, 1),
        RC(2, 2),
        RC(4, 6)
    )


    @Test
    fun `create height matrix`() {

        assertThat(matrix).hasDimensions(5, 10)
        assertThat(matrix[0][0]).isEqualTo(2)
    }

    @Test
    fun `find adjustant fields on corners`() {
        val actualTopLeft = getAdjustantFields(matrix, 0, 0)
        val actualTopRight = getAdjustantFields(matrix, 0, 9)
        val actualBottomLeft = getAdjustantFields(matrix, 4, 0)
        val actualBottomRight = getAdjustantFields(matrix, 4, 9)

        assertThat(actualTopLeft).containsExactlyInAnyOrder(3, 1)
        assertThat(actualTopRight).containsExactlyInAnyOrder(1, 1)
        assertThat(actualBottomLeft).containsExactlyInAnyOrder(8, 8)
        assertThat(actualBottomRight).containsExactlyInAnyOrder(7, 9)
    }

    @Test
    fun `find adjustant fiels on edges`() {
        val actualLeft = getAdjustantFields(matrix, 2, 0)
        val actualRight = getAdjustantFields(matrix, 2, 9)
        val actualTop = getAdjustantFields(matrix, 0, 4)
        val actualBottom = getAdjustantFields(matrix, 4, 4)

        assertThat(actualLeft).containsExactlyInAnyOrder(3, 8, 8)
        assertThat(actualTop).containsExactlyInAnyOrder(9, 8, 4)
        assertThat(actualBottom).containsExactlyInAnyOrder(9, 8, 6)
        assertThat(actualRight).containsExactlyInAnyOrder(1, 9, 9)
    }

    @Test
    fun `find adjustant fields within matrix`() {
        val actual = getAdjustantFields(matrix, 2, 4)

        assertThat(actual).containsExactlyInAnyOrder(6, 8, 8, 8)
    }

    @Test
    fun `find lowest spots`() {
        val actual = findLowestSpotsLocations(matrix)

        assertThat(actual).containsExactlyInAnyOrder(*(testLowSpots.toTypedArray()))
    }

    @Test
    fun `shall calculate correct risk level`() {
        val actual = calculateRiskLevel(matrix, testLowSpots)

        assertThat(actual).isEqualTo(15)
    }

    @TestFactory
    fun `shall find a basin`() =
        listOf(
            Pair(RC(0, 1), 3),
            Pair(RC(0, 9), 9),
            Pair(RC(2, 2), 14),
            Pair(RC(4, 6), 9)
        )
            .map {
                DynamicTest.dynamicTest("find basin size ${it.first}") {
                    val actual = findBasinSize(matrix, it.first.row, it.first.col)

                    assertThat(actual).isEqualTo(it.second)
                }
            }

    @Test
    fun `find largest 3 sizes`() {
        val actual = findLargest3Basins(listOf(3, 9, 14, 9))

        assertThat(actual).containsExactlyInAnyOrder(9, 9, 14)
    }

    @Test
    fun `multiplies correctly`() {
        val actual = calculateSum(listOf(3, 9, 14, 9))

        assertThat(actual).isEqualTo(1134)
    }
}
