package day07

import util.ResLoader
import kotlin.math.abs

private fun printPositions(pos: IntArray) {
    for (i in 0 until pos.size)
        if (pos[i] > 0) println("Position: $i -> ${pos[i]}")
}

private fun fuelNeededToMove(from: Int, to: Int): Int {
    val n = abs(to - from)
    return n * (n + 1) / 2
}


private fun calculateFuel(toPosition: Int, positions: IntArray) =
    positions.foldIndexed(0) { i, acc, next -> acc + next * fuelNeededToMove(i, toPosition) }

fun day07() {
    println("Day 07:\nCrab Submarines\n=====================")

    val input = ResLoader.readlines("input_07").first().split(",").map { it.toInt() }
    val maxPos = input.maxOrNull() ?: 0

    val positions = IntArray(maxPos + 1)
    input.forEach {
        positions[it]++
    }

    var smallestFuel = Int.MAX_VALUE
    var smallestPos = -1
    for (i in 0..maxPos) {
        val fuel = calculateFuel(i, positions)
        if (fuel < smallestFuel) {
            smallestFuel = fuel
            smallestPos = i
        }
    }

    println("Smallest Pos: $smallestPos with fuel: $smallestFuel")
}
