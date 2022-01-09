package day11

import util.Matrix
import util.ResLoader
import util.matrixFromInput

fun Matrix.processCharging() {
    for (row in 0 until this.size)
        for (col in 0 until this[0].size)
            this[row][col]++
}

fun Matrix.chargeIfExists(row: Int, col: Int) {
    if (row < 0 || row > (this.size - 1)
        || col < 0 || col > (this[0].size - 1)
    )
        return

    if (this[row][col] > 0)
        this[row][col]++
}

fun Matrix.processFlashing(): Int {
    var repeat = true
    while (repeat) {
        repeat = false
        for (row in 0 until this.size)
            for (col in 0 until this[0].size) {
                if (this[row][col] > 9) {
                    this[row][col] = 0
                    chargeIfExists(row - 1, col - 1)
                    chargeIfExists(row - 1, col)
                    chargeIfExists(row - 1, col + 1)
                    chargeIfExists(row, col - 1)
                    chargeIfExists(row, col + 1)
                    chargeIfExists(row + 1, col - 1)
                    chargeIfExists(row + 1, col)
                    chargeIfExists(row + 1, col + 1)
                    repeat = true
                }
            }
    }

    // count 0s
    var ret = 0
    this.forEach { row ->
        row.forEach {
            if (it == 0) ret++
        }
    }
    return ret
}

fun main() {
    println("Day11\nCharging Squid\n==============")

    val input = ResLoader.readlines("input_11")
    val matrix = matrixFromInput(input)

    val total = (0..99).sumOf {
        matrix.processCharging()
        matrix.processFlashing()
    }

    println("Result after 100 Steps: $total")

    val matrix2 = matrixFromInput(input)

    var cont = true
    var count = 0
    while (cont) {
        count++
        matrix2.processCharging()
        if (matrix2.processFlashing() == 100)
            cont = false
    }

    println("Synchron flashing after $count steps")

}
