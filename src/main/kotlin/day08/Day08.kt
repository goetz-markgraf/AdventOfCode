package day08

import util.ResLoader

private fun subtract(longer: String, shorter: String): List<Char> {
    val ret = mutableListOf<Char>()
    longer.forEach { if (shorter.indexOf(it) < 0) ret.add(it) }

    return ret
}

private fun String.containsAll(chars: List<Char>) =
    chars.all { this.contains(it) }

private fun findContaining(input: List<String>, chars: List<Char>) =
    input.filter { it.containsAll(chars) }

private fun findSegements(input: List<String>): Map<String, Char> {
    // alle inputStrings sortieren
    val sortedInput = input.map { eachStr -> eachStr.toSortedSet().joinToString("") }.sortedBy { it.length }
//    print("Input Sorted: $sortedInput")

    val haeufigkeiten = mutableMapOf(
        'a' to 0,
        'b' to 0,
        'c' to 0,
        'd' to 0,
        'e' to 0,
        'f' to 0,
        'g' to 0
    )
    sortedInput.forEach {
        it.map { char -> haeufigkeiten.put(char, haeufigkeiten[char]?.plus(1) ?: 0) }
    }

    val sortedHaeufigkeiten = haeufigkeiten.toList().sortedBy { it.second }
//    print(" -> ${sortedHaeufigkeiten}")

//    val segA = subtract(sortedInput[1], sortedInput[0]).first()
    val segB = sortedHaeufigkeiten[1].first
    val segE = sortedHaeufigkeiten[0].first
    val segF = sortedHaeufigkeiten[6].first

    val num5Segments = mutableListOf(sortedInput[3], sortedInput[4], sortedInput[5])
    val num6Segments = mutableListOf(sortedInput[6], sortedInput[7], sortedInput[8])


    val num1 = sortedInput[0]
    val num4 = sortedInput[2]
    val num5 = findContaining(num5Segments, listOf(segB, segF)).first().also { num5Segments.remove(it) }
    val num2 = findContaining(num5Segments, listOf(segE)).first().also { num5Segments.remove(it) }
    val num3 = num5Segments.first()
    val num7 = sortedInput[1]
    val num8 = sortedInput[9]

    val num9 = num6Segments.filter { !it.contains(segE) }.first().also { num6Segments.remove(it) }

    val diffChar = subtract(num6Segments[0], num6Segments[1]).first()
    val (num6, num0) = if (haeufigkeiten[diffChar] == 7) Pair(
        num6Segments[0],
        num6Segments[1]
    ) else Pair(num6Segments[1], num6Segments[0])

    val ret = mapOf(
        num1 to '1',
        num2 to '2',
        num3 to '3',
        num4 to '4',
        num5 to '5',
        num6 to '6',
        num7 to '7',
        num8 to '8',
        num9 to '9',
        num0 to '0'
    )
    return ret
}

private fun parse(input: List<String>, map: Map<String, Char>): Int {
    val ret = StringBuffer()

    val sortedInput = input.map { eachStr -> eachStr.toSortedSet().joinToString("") }

    sortedInput.forEach { ret.append(map[it]) }

    return ret.toString().toInt()
}

private fun part2(input: List<String>) {
    val total = input.map {
        val trainingAndTest = it.split("|").toTypedArray()

        val map = findSegements(trainingAndTest[0].trim().split(" "))

        val result = parse(trainingAndTest[1].trim().split(" "), map)

//        println(" -> Input ${trainingAndTest[1]}: Makes: $result")

        result
    }.sum()

    println("Result: $total")
}

private fun part1(input: List<String>) {
    val output = input.flatMap { it.split("|")[1].split(" ").drop(1) }

    val count = output.filter { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }.count()

//    println("Input ${input.size}: $input")
//    println("Output ${output.size}: $output")
    println("Result: $count")

}

fun day08() {
    println("Day 08:\nDigits\n=====================")

    val input = ResLoader.readlines("input_08")

    part1(input)
    part2(input)
}
