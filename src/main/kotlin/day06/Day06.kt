package day06

import util.ResLoader

val MAX_AGE = 9

private fun processPopulation(population: LongArray) {
    val birth = population[0]
    for (i in 1 until MAX_AGE) {
        population[i - 1] = population[i]
    }
    population[MAX_AGE - 1] = birth
    population[MAX_AGE - 3] += birth
}

fun main() {
    println("Day 06:\nLanternfish\n=====================")

    val input = ResLoader.readlines("input_06").first().split(",").map { it.toInt() }.toList()

    val population = LongArray(MAX_AGE)
    input.forEach {
        population[it]++
    }

    val count = 256

    for (i in 0 until count)
        processPopulation(population)

    println("End Population: ${population.toList()} -> Total: ${population.sum()}")
}
