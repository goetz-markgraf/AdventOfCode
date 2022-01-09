package day05

import util.Point
import util.ResLoader
import java.lang.Math.max
import kotlin.math.abs


val regEx = "^([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)$".toRegex()


private fun parseLine(str: String): Pair<Point, Point>? {
    val result = regEx.find(str) ?: return null

    val values = result.groupValues
    if (values.size != 5) return null

    try {
        val x1 = values[1].toInt()
        val y1 = values[2].toInt()
        val x2 = values[3].toInt()
        val y2 = values[4].toInt()

        return Pair(Point(x1, y1), Point(x2, y2))
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}


private fun straightLine(line: Pair<Point, Point>) =
    line.first.x == line.second.x || line.first.y == line.second.y


private fun findMaxPoint(lines: List<Pair<Point, Point>>) =
    lines.fold(Point(0, 0))
    { acc, next ->
        Point(max(max(acc.x, next.first.x), next.second.x), max(max(acc.y, next.first.y), next.second.y))
    }


private fun createEmptyField(max: Point) =
    (0..max.y).map { IntArray(max.x + 1) }.toTypedArray()


private fun range(from: Int, to: Int) =
    if (from <= to) {
        from..to
    } else {
        to..from
    }

private fun fillStraightLine(field: Array<IntArray>, from: Point, to: Point) {
    for (i in range(from.x, to.x))
        for (j in range(from.y, to.y))
            field[j][i]++

}

private fun fillLines(field: Array<IntArray>, lines: List<Pair<Point, Point>>) {
    lines.forEach {
        val from = it.first
        val to = it.second
        if (straightLine(it)) {
            fillStraightLine(field, from, to)
        } else {
            val distance = abs(to.x - from.x)
            val deltaX = if (from.x < to.x) 1 else -1
            val deltaY = if (from.y < to.y) 1 else -1

            for (i in 0..distance) {
                field[from.y + (deltaY * i)][from.x + (deltaX * i)]++
            }
        }
    }
}

private fun printField(field: Array<IntArray>) {
    field.forEach { line ->
        line.forEach { print("$it,") }
        println("")
    }
}


private fun countValuesOver(field: Array<IntArray>, threshold: Int): Int {
    var ret = 0
    field.forEach { line ->
        line.forEach {
            if (it > threshold) ret += 1
        }
    }

    return ret
}


fun main() {
    println("Day 05:\n=====================")

    val input = ResLoader.readlines("input_05")
    val lines = input.mapNotNull(::parseLine)

//    println("Input:\n$lines")

    val maxXY = findMaxPoint(lines)

//    val straightLines = lines.filter(::straightLine)

    val field = createEmptyField(maxXY)

    fillLines(field, lines)
//    println ("With Lines")
//    printField(field)

    val result = countValuesOver(field, 1)
    println("Result: $result")
}
