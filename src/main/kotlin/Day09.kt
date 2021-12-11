fun createHeightMatrix(input: List<String>) =
    input.map { line ->
        line.toCharArray().map { it.digitToInt() }.toIntArray()
    }.toTypedArray()

fun getAdjustantFields(matrix: Matrix, row: Int, col: Int): List<Int> {
    val ret = mutableListOf<Int>()

    // is obove free
    if (row > 0)
        ret.add(matrix[row - 1][col])

    // is left free
    if (col > 0)
        ret.add(matrix[row][col - 1])

    // is below free
    if (row < matrix.size - 1)
        ret.add(matrix[row + 1][col])

    // is right free
    if (col < matrix[0].size - 1)
        ret.add(matrix[row][col + 1])

    return ret
}

fun findLowestSpotsLocations(matrix: Matrix): List<RC> {
    val ret = mutableListOf<RC>()

    for (row in matrix.indices)
        for (col in matrix[0].indices) {

            val value = matrix[row][col]
            val adjustantFields = getAdjustantFields(matrix, row, col)
            if (adjustantFields.all { it > value }) ret.add(RC(row, col))
        }

    return ret
}

fun calculateRiskLevel(matrix: Matrix, inNumbers: List<RC>) =
    inNumbers.map { matrix[it.row][it.col] + 1 }.sum()

private fun doIfExistingAndNewAndNot9(matrix: Matrix, rc: RC, found: List<RC>, action: (RC) -> Unit) {
    if (rc.inMatrix(matrix) && !found.contains(rc) && matrix.get(rc) < 9)
        action(rc)
}

private fun findAdjustantRCNotFoundNot9(matrix: Matrix, rc: RC, found: List<RC>) =
    buildList {
        doIfExistingAndNewAndNot9(matrix, rc.up(), found) { add(it) }
        doIfExistingAndNewAndNot9(matrix, rc.down(), found) { add(it) }
        doIfExistingAndNewAndNot9(matrix, rc.left(), found) { add(it) }
        doIfExistingAndNewAndNot9(matrix, rc.right(), found) { add(it) }
    }

private fun recFindBasin(matrix: Matrix, found: List<RC>, toCheck: List<RC>): List<RC> {
    if (toCheck.isEmpty())
        return found

    val head = toCheck.first()
    val tail = toCheck.drop(1)

    if (found.contains(head))
        return recFindBasin(matrix, found, tail)

    val newToCheck = findAdjustantRCNotFoundNot9(matrix, head, found)
    return recFindBasin(matrix, found.plus(head), tail.plus(newToCheck))
}

fun findBasinSize(matrix: Matrix, row: Int, col: Int) =
    recFindBasin(matrix, emptyList(), listOf(RC(row, col))).size

fun findLargest3Basins(basinSizes: List<Int>) =
    basinSizes.sortedDescending().take(3)

fun calculateSum(basinSizes: List<Int>) =
    findLargest3Basins(basinSizes).fold(1L) { acc, next -> acc * next }

fun day09() {
    println("Day 09 – Sea floor hightmap\n=======================")

    val input = ResLoader.readlines("input_09")

    val matrix = createHeightMatrix(input)

    val lowestSpots = findLowestSpotsLocations(matrix)

    val riskLevel = calculateRiskLevel(matrix, lowestSpots)

    println("The Risk Level is: $riskLevel")

    val basinSizes = lowestSpots.map { findBasinSize(matrix, it.row, it.col) }

    val total = calculateSum(basinSizes)

    println("A Total of ${basinSizes.size} basins multiplied -> $total")

}
