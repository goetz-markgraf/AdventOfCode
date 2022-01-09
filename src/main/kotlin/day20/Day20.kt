package day20

import util.Point
import util.ResLoader


// Part 1


fun createBoolArray(s: String) =
    BooleanArray(s.length) { s.get(it) == '#' }


fun createBitSet(s: List<String>) =
    buildSet<Point> {
        s.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#')
                    add(Point(x, y))
            }
        }
    }

val translateList =
    buildList<Pair<Int, Int>> {
        for (y in -1..1)
            for (x in -1..1)
                add(x to y)
    }

fun getEnhancementIndexForPixel(p: Point, set: Set<Point>): Int {
    var ret = 0

    translateList.forEachIndexed { i, delta ->
        val pTest = p.translate(delta.first, delta.second)
        if (set.contains(pTest))
            ret += 1 shl (8 - i)
    }

    return ret
}

fun findIndexRangesForEnhancement(set: Set<Point>) =
    Pair(
        set.minOf { it.x } - 1..set.maxOf { it.x } + 1,
        set.minOf { it.y } - 1..set.maxOf { it.y } + 1
    )

private fun calculateEnhancement(ranges: Pair<IntRange, IntRange>, set: Set<Point>, alg: BooleanArray): Set<Point> =
    buildSet {
        for (x in ranges.first)
            for (y in ranges.second) {
                val enhIndex = getEnhancementIndexForPixel(Point(x, y), set)
                if (alg[enhIndex])
                    add(Point(x, y))
            }
    }

fun enhance(set: Set<Point>, alg: BooleanArray): Set<Point> {
    val ranges = findIndexRangesForEnhancement(set)
    return calculateEnhancement(ranges, set, alg)
}

fun printImage(set: Set<Point>) {
    val ranges = findIndexRangesForEnhancement(set)
    for (y in ranges.second) {
        for (x in ranges.first) {
            if (set.contains(Point(x, y)))
                print('#')
            else
                print('.')
        }
        println("")
    }
}


// main

fun main() {
    println("Day20\nPicture Enhancement\n=================")

    val lines = ResLoader.readlines("input_20")

    val alg = createBoolArray(lines.first())
    val initialSet = createBitSet(lines.drop(2))

    val result = (1..2).fold(initialSet) { set, _ ->
        enhance(set, alg)
    }

    println("After 2 enhancements, we have ${result.size} set pixels")
}
