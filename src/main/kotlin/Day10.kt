import java.util.*

private val openings = arrayOf(
    '(', '[', '{', '<'
)

private val closings = arrayOf(
    ')', ']', '}', '>'
)

private val errorRankings = arrayOf(
    3L,
    57L,
    1197L,
    25137L
)

private val closingRanking = arrayOf(
    1L,
    2L,
    3L,
    4L
)

private fun Stack<Char>.process(input: Char): Boolean {
    if (openings.contains(input)) {
        this.push(input)
        return true
    } else {
        if (empty())
            throw RuntimeException("Stack is empty")

        val index = closings.indexOf(input)
        if (index < 0)
            throw RuntimeException("neither opening nor closing")

        val corrOpening = openings[index]
        return if (corrOpening == peek()) {
            pop()
            true
        } else {
            false
        }
    }
}

fun calculateErrorRankings(error: List<Char>) =
    error.sumOf { errorRankings[closings.indexOf(it)] }

fun findError(input: String): Char {
    val stack = Stack<Char>()

    val error = input.find { !stack.process(it) }

    return error ?: ' '
}


fun discardErrorLines(input: List<String>) =
    input.filter { findError(it) == ' ' }


fun findCompletionSequence(input: String): String {
    val stack = Stack<Char>()

    input.find { !stack.process(it) }

    // From here, the stack tells me how to complete it
    return buildString {
        var amountOpenings = 0
        while (!stack.empty()) {
            val ch = stack.pop()
            if (closings.contains(ch)) {
                amountOpenings++
            } else {
                if (amountOpenings > 0) {
                    amountOpenings--
                } else {
                    val index = openings.indexOf(ch)
                    append(closings[index])
                }
            }
        }
    }
}

fun calculateClosingRanking(input: String) =
    input.fold(0L) { acc, next ->
        acc * 5 + closingRanking[closings.indexOf(next)]
    }

fun findMiddleValue(input: List<Long>): Long {
    val sorted = input.sorted()
    val middle = (sorted.size - 1) / 2

    return sorted.elementAt(middle)
}

fun day10() {
    println("Day10\nNested Paranthese\n===================")

    val input = ResLoader.readlines("input_10")

    val errors = input
        .map(::findError)
        .filter { it != ' ' }

    val errorRanking = calculateErrorRankings(errors)

    println("${errors.size} error result in a total Ranking of: $errorRanking")

    val incomplete = discardErrorLines(input)

    val rankings = incomplete
        .map(::findCompletionSequence)
        .map(::calculateClosingRanking)

    val middleValue = findMiddleValue(rankings)

    println("${incomplete.size} lines completed: Result: $middleValue")
}
