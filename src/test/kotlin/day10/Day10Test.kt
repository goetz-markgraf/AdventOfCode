package day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.ResLoader

class Day10Test {
    @Test
    fun `shall find error`() {
        val actual = findError("[>")

        assertThat(actual).isEqualTo('>')
    }

    @Test
    fun `shall find no error`() {
        val actual = findError("[]")

        assertThat(actual).isEqualTo(' ')
    }

    @Test
    fun `shall find no error in incomplete`() {
        val actual = findError("[")

        assertThat(actual).isEqualTo(' ')
    }

    @Test
    fun `find no error in complex example`() {
        val actual = findError("[()]<<>><<>()>{{}[]}[{}[]]")

        assertThat(actual).isEqualTo(' ')
    }

    @Test
    fun `find no error in complex incomplete example`() {
        val actual = findError("[()]<<>><<>()>{{}[]}[{}[]((")

        assertThat(actual).isEqualTo(' ')
    }

    @Test
    fun `shall find test errors`() {
        val input = ResLoader.readlines("input_test_10")

        val actual = input
            .map { findError(it) }
            .filter { it != ' ' }

        assertThat(actual).containsExactlyInAnyOrder(
            ')', ')',
            ']',
            '>',
            '}'
        )
    }

    @Test
    fun `shall calculate correct total error ranking`() {
        val actual = calculateErrorRankings(
            listOf(
                ')', ')',
                ']',
                '>',
                '}'
            )
        )

        assertThat(actual).isEqualTo(26397L)
    }

    @Test
    fun `discard all lines with errors`() {
        val input = ResLoader.readlines("input_test_10")

        val actual = discardErrorLines(input)

        assertThat(actual)
            .containsExactlyInAnyOrder(
                "[({(<(())[]>[[{[]{<()<>>",
                "[(()[<>])]({[<{<<[]>>(",
                "(((({<>}<{<{<>}{[]{[]{}",
                "{<[[]]>}<{[{[{[]{()[[[]",
                "<{([{{}}[<[[[<>{}]]]>[]]"
            )
    }

    @TestFactory
    fun `shall find completion sequence`() =
        listOf(
            Pair("(", ")"),
            Pair("(()", ")"),
            Pair("([", "])"),
            Pair("([](", "))")
        ).map {
            DynamicTest.dynamicTest(it.first) {
                val actual = findCompletionSequence(it.first)

                assertThat(actual).isEqualTo(it.second)
            }
        }

    @Test
    fun `find correct test closings`() {
        val input = ResLoader.readlines("input_test_10")

        val rankings = input
            .filter { findError(it) == ' ' }
            .map(::findCompletionSequence)
            .map(::calculateClosingRanking)

        assertThat(rankings).containsExactlyInAnyOrder(
            288957L,
            5566L,
            1480781L,
            995444L,
            294
        )
    }

    @Test
    fun `find the middle value`() {
        val input = listOf(
            288957L,
            5566L,
            1480781L,
            995444L,
            294
        )

        val actual = findMiddleValue(input)

        assertThat(actual).isEqualTo(288957)
    }
}
