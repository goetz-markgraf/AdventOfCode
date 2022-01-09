package day15

import util.Matrix2
import util.RC
import util.ResLoader


data class PosCost(
    val pos: RC,
    val cost: Int
)


fun findIndexOfLowestPosition(positions: List<PosCost>): Int {
    var ret = 0
    var min = Int.MAX_VALUE

    for (i in 0 until positions.size) {
        val cost = positions.get(i).cost
        if (cost < min) {
            min = cost
            ret = i
        }
    }

    return ret
}


fun findNewPositions(pos: RC, matrix: Matrix2): List<RC> =
    buildList {
        pos.up().apply { if (matrix.inMatrix(this)) add(this) }
        pos.down().apply { if (matrix.inMatrix(this)) add(this) }
        pos.left().apply { if (matrix.inMatrix(this)) add(this) }
        pos.right().apply { if (matrix.inMatrix(this)) add(this) }
    }


fun inList(list: List<PosCost>, rc: RC) =
    list.find { it.pos == rc } != null


fun findLowestCost(matrix: Matrix2, target: RC, startPos: PosCost): PosCost {
    val searchPositions: MutableList<PosCost> = mutableListOf(startPos)
    var targetFound: PosCost? = null

    while (targetFound == null) {
        val minIndex = findIndexOfLowestPosition(searchPositions)
        val next = searchPositions.get(minIndex)
        searchPositions.removeAt(minIndex)

        if (next.pos == target)
            targetFound = next
        else {
            matrix.set(next.pos, 0)

            val newFields = findNewPositions(next.pos, matrix)
                .filter { matrix.get(it) > 0 }
                .filter { !inList(searchPositions, it) }

            searchPositions.addAll(newFields.map { PosCost(it, next.cost + matrix.get(it)) })
        }
    }

    return targetFound
}


fun findLowestRiskLevel(input: Matrix2): Int {
    val start = PosCost(RC(0, 0), 0)
    input.set(start.pos, 0)

    val result = findLowestCost(
        input,
        RC(input.maxRow - 1, input.maxCol - 1),
        start
    )

    return result.cost ?: -1
}


// ============= Enlarge Matrix =================


fun rowPlus(row: IntArray, plus: Int) =
    row.map { ((it + (plus - 1)) % 9) + 1 }.toIntArray()


fun enlargeHorizontal(rows: Array<IntArray>) =
    rows.map { row ->
        row + rowPlus(row, 1) + rowPlus(row, 2) + rowPlus(row, 3) + rowPlus(row, 4)
    }.toTypedArray()


fun arrayPlus(rows: Array<IntArray>, plus: Int) =
    rows.map { row ->
        rowPlus(row, plus)
    }.toTypedArray()


fun enlarge(matrix: Matrix2): Matrix2 {
    val wide = enlargeHorizontal(matrix.matrix)

    val allRows =
        wide + arrayPlus(wide, 1) + arrayPlus(wide, 2) + arrayPlus(wide, 3) + arrayPlus(wide, 4)

    return Matrix2(allRows)
}


fun main() {
    println("Day15\n============\nRisk Level")
    val input = ResLoader.readlines("input_15")
    var matrix = Matrix2.fromInput(input)

    val riskLevel = findLowestRiskLevel(matrix)

    println("Risk Level: $riskLevel")

    print("Enlarge ... ")
    matrix = Matrix2.fromInput(input)
    val enlargedMatrix = enlarge(matrix)
    println("done")

    val riskLevelEnlarged = findLowestRiskLevel(enlargedMatrix)

    println("Risk Level enlarged: $riskLevelEnlarged")

}
