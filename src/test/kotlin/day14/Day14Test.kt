package day14

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.ResLoader

class Day14Test {
    val input = ResLoader.readlines("input_test_14")

    @Test
    fun `can parse instructions`() {
        val actual = parseInput(input)

        assertThat(actual.polymer.first()).isEqualTo('N')
        assertThat(actual.instructions).hasSize(16)
            .last().isEqualTo(Instruction(source = "CN".toCharArray(), new='C'))
    }

    @Test
    fun `can do one iteration`() {
        val recipe = parseInput(input)

        val actual = doExpansion(recipe.polymer, recipe.instructions)

        assertThat(actual).isEqualTo("NCNBCHB".toCharArray())
    }

    @Test
    fun `can do four iterations`() {
        val recipe = parseInput(input)

        val actual = (1..4).fold(recipe.polymer){
                acc, _ -> doExpansion(acc, recipe.instructions)
        }

        assertThat(actual).isEqualTo("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB".toCharArray())
    }

    @Test
    fun `iterate 10 times and find highest and lowest `() {
        val recipe = parseInput(input)

        val actual = (1..10).fold(recipe.polymer){ acc, next ->
            println (acc.size)
            doExpansion(acc, recipe.instructions)
        }

        val charMap = countLetters(actual)
        val (lowest, highest) = findLowestAndHighest(charMap)

        assertThat(lowest).isEqualTo(161)
        assertThat(highest).isEqualTo(1749)
    }

    @Test
    fun `iterate 10 times and find highest and lowest with groups`() {
        val recipe = parseInput(input)
        val groupedPolymer = createGroupedPolymer(recipe.polymer)

        val actual = (1..10).fold(groupedPolymer.elementGroups){ acc, next ->
            doGroupedExpansion(acc, recipe.instructions)
        }

        val charMap = countLetters(groupedPolymer.firstElement, actual)
        val (lowest, highest) = findLowestAndHighest(charMap)

        assertThat(lowest).isEqualTo(161)
        assertThat(highest).isEqualTo(1749)
    }

    @Test
    fun `iterate 40 times and find highest and lowest with groups`() {
        val recipe = parseInput(input)
        val groupedPolymer = createGroupedPolymer(recipe.polymer)

        val actual = (1..40).fold(groupedPolymer.elementGroups){ acc, next ->
            doGroupedExpansion(acc, recipe.instructions)
        }

        val charMap = countLetters(groupedPolymer.firstElement, actual)
        val (lowest, highest) = findLowestAndHighest(charMap)

        assertThat(lowest).isEqualTo(3849876073L)
        assertThat(highest).isEqualTo(2192039569602L)
    }

}
