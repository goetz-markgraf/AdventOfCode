package day12

import createCaveSystem
import findRoutes
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import util.ResLoader

class Day12Test {
    val inputA = ResLoader.readlines("input_test_12_a")
    val inputB = ResLoader.readlines("input_test_12_b")
    val inputC = ResLoader.readlines("input_test_12_c")

    @Test
    fun `create cave System A`() {
        val system = createCaveSystem(inputA)

        assertThat(system).hasSize(7)
            .element(0).isEqualTo("start" to "A")
    }

    @TestFactory
    fun `shall find all routes in the given cave systems`() =
        listOf(
            Pair(inputA, 10),
            Pair(inputB, 19),
            Pair(inputC, 226)
        ).map { DynamicTest.dynamicTest("${it.second}") {
            val system = createCaveSystem(it.first)

            val routes = findRoutes(system, listOf(listOf("start")))
            assertThat(routes).hasSize(it.second)
        } }

    @TestFactory
    fun `shall find all routes in the given cave systems in the enhanced way`() =
        listOf(
            Pair(inputA, 36),
            Pair(inputB, 103),
            Pair(inputC, 3509)
        ).map { DynamicTest.dynamicTest("${it.second}") {
            val system = createCaveSystem(it.first)

            val routes = findRoutes(
                system,
                listOf(listOf("start")),
                true
            )
            assertThat(routes).hasSize(it.second)
        } }
}
