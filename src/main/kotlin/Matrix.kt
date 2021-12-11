typealias Matrix = Array<IntArray>

data class RC(
    val row: Int,
    val col: Int
) {
    fun up() = this.copy(row = this.row - 1)
    fun down() = this.copy(row = this.row + 1)
    fun left() = this.copy(col = this.col - 1)
    fun right() = this.copy(col = this.col + 1)
    fun upLeft() = this.copy(col = col - 1, row = row - 1)
    fun upRight() = this.copy(col = col - 1, row = row + 1)
    fun downLeft() = this.copy(col = col + 1, row = row - 1)
    fun downRight() = this.copy(col = col + 1, row = row + 1)

    fun inMatrix(matrix: Matrix) =
        row >= 0 && row < matrix.size && col >= 0 && col < matrix[0].size
}

fun Matrix.get(rc: RC) =
    this[rc.row][rc.col]

fun matrixFromInput(input: List<String>) =
    input.map { line ->
        buildList {
            line.trim().forEach {
                add(it.digitToInt())
            }
        }.toIntArray()
    }.toTypedArray()
