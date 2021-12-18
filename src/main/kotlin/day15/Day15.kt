package day15

import util.Matrix2
import util.RC
import util.ResLoader
import kotlin.math.pow

fun countBits(trail: Long): Int {
    var shifter = trail
    var count = 0

    while (shifter > 0) {
        if ((shifter and 1) > 0) count++
        shifter = shifter shr 1
    }

    return count
}

fun checkIfTrailIsValid(trail: Long, matrix: Matrix2) =
    countBits(trail) == matrix.maxCol - 1

private fun calculateRiskLevel(matrix: Matrix2, trail: Long, numBits: Int): Int {
    if (!checkIfTrailIsValid(trail, matrix))
        return numBits * 9

    var ret = 0
    var p = RC(0, 0)

    (0 until numBits).forEach { bit ->
        val set = (trail and (2.0.pow(bit).toLong())) > 0
        if (set)
            p = p.right()
        else
            p = p.down()

        if (!matrix.inMatrix(p))
            return numBits * 9

        ret += matrix.get(p)
    }

    return ret
}

fun findLowestPath(matrix: Matrix2): Int {
    val numBits = matrix.maxCol - 1 + matrix.maxRow - 1

    val numTrails = 2.0.pow(numBits).toLong()

    var min = numBits * 9

    (0..numTrails).forEach { trailNumber ->
        val riskLevel = calculateRiskLevel(matrix, trailNumber, numBits)
        if (trailNumber % 100_000_000 == 0L) println("$trailNumber from $numTrails (Value: $min)")

        if (riskLevel < min)
            min = riskLevel
    }

    return min
}

fun day15() {
    val input = ResLoader.readlines("input_15")
    val matrix = Matrix2.fromInput(input)

    val riskLevel = findLowestPath(matrix)

    println("Risk Level: $riskLevel")
}
