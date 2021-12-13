data class FoldInstruction(
    val horizontal: Boolean,
    val position: Int
)

data class TransparentResult(
    val foldInstructions: List<FoldInstruction>,
    val points: List<Point>
)

private fun parseFold(line: String): FoldInstruction {
    if (!line.startsWith("fold along "))
        return FoldInstruction(true,-1)
    val rest = line.substring(11)

    val (direction, positionString) = rest.split("=")

    return FoldInstruction(
        horizontal = direction.lowercase()=="y",
        position = positionString.toInt()
    )
}

fun parseTransparentInput(input: List<String>): TransparentResult {
    val inputPoints = input.takeWhile { it.trim() != "" }
    val inputFolds = input.drop(inputPoints.size+1)

    return TransparentResult(
        foldInstructions = inputFolds.map(::parseFold),
        points = inputPoints.map { line ->
            line.split(",")
        }.map { Point(it.first().toInt(), it.last().toInt()) }
    )
}

fun foldPoint(p: Point, fold: FoldInstruction) =
    when {
        fold.horizontal && p.y >= fold.position ->
            Point(p.x, p.y - 2*(p.y-fold.position))
        !fold.horizontal && p.x >= fold.position ->
            Point(p.x-2*(p.x-fold.position), p.y)
        else -> p
    }

fun doFold(input: List<Point>, fold: FoldInstruction) =
    input.map { foldPoint(it, fold)  }.distinct()

fun isPoint(points: List<Point>, x: Int, y: Int) =
    points.any { it.x == x && it.y == y }

fun pointsToString(p: List<Point>): String {
    val xmax = p.maxOf { it.x }
    val ymax = p.maxOf { it.y }

    return buildString {
        for (y in 0..ymax) {
            for (x in 0..xmax)
                append(if (isPoint(p, x, y)) "█" else " ")

            append('\n')
        }
    }
}

// LKREBPRK

fun day13() {
    println ("Day13\nFolding\n==============")

    val input = ResLoader.readlines("input_13")
    val parseInput = parseTransparentInput(input)

    val result = doFold(parseInput.points, parseInput.foldInstructions.first())

    println("After one Fold: ${result.size}")

    val parseInput2 = parseTransparentInput(input)

    val finalResult = parseInput2.foldInstructions.fold(parseInput2.points) {
        points, next -> doFold(points, next)
    }

    println ("Message:\n${pointsToString(finalResult)}")
}
