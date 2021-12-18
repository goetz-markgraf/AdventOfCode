package day14

import util.ResLoader

typealias Polymer = CharArray

typealias ElementGroup = Map<Pair<Char, Char>, Long>

data class GroupedPolymer(
    val firstElement: Char,
    val elementGroups: ElementGroup
)

data class Instruction(
    val source: Polymer,
    val new: Char
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Instruction

        if (!source.contentEquals(other.source)) return false
        if (new != other.new) return false

        return true
    }

    override fun hashCode(): Int {
        var result = source.contentHashCode()
        result = 31 * result + new.hashCode()
        return result
    }
}

data class Recipe(
    val polymer: Polymer,
    val instructions: List<Instruction>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (!polymer.contentEquals(other.polymer)) return false
        if (instructions != other.instructions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = polymer.contentHashCode()
        result = 31 * result + instructions.hashCode()
        return result
    }
}

private fun parseInstruction(line: String): Instruction {
    val parts = line.split(" -> ")
    return Instruction(
        source = parts.first().toCharArray(),
        new = parts.last().first()
    )
}

fun parseInput(input: List<String>): Recipe {
    val polymerInput = input.first()

    val instructionInput = input.drop(2)

    return Recipe(
        polymer = polymerInput.toCharArray(),
        instructions = instructionInput.map(::parseInstruction)
    )
}

private fun findInsertion(tuple: Polymer, instructions: List<Instruction>) =
    instructions.find { tuple.contentEquals(it.source) }

fun doExpansion(polymer: Polymer, instructions: List<Instruction>): Polymer {
    val ret = mutableListOf<Char>()

    ret.add(polymer[0])

    for (i in 0 until polymer.size - 1) {
        val nextTuple = charArrayOf(polymer[i], polymer[i + 1])
        val insertion = findInsertion(nextTuple, instructions)
        if (insertion != null)
            ret.add(insertion.new)

        ret.add(nextTuple[1])
    }

    return ret.toCharArray()
}

private fun addToGroup(map: MutableMap<Pair<Char, Char>, Long>, tuple: Pair<Char, Char>, value: Long) {
    val existing = map.get(tuple)
    if (existing == null)
        map.put(tuple, value)
    else
        map.put(tuple, existing + value)
}

fun createGroupedPolymer(input: Polymer) =
    GroupedPolymer(
        firstElement = input[0],
        elementGroups = buildMap {
            for (i in 0 until input.size - 1) {
                val tuple = Pair(input[i], input[i + 1])
                addToGroup(this, tuple, 1)
            }
        }
    )

fun doGroupedExpansion(groups: ElementGroup, instructions: List<Instruction>) =
    buildMap {
        groups.forEach { key, value ->
            val insertion = findInsertion(charArrayOf(key.first, key.second), instructions)
            if (insertion == null)
                addToGroup(this, key, value)
            else {
                addToGroup(this, Pair(key.first, insertion.new), value)
                addToGroup(this, Pair(insertion.new, key.second), value)
            }
        }
    }

fun countLetters(input: Polymer) =
    input.groupBy { it }.map { it.key to (it.value.size.toLong()) }.toMap()

fun countLetters(first: Char, elementGroups: ElementGroup) =
    buildMap {
        elementGroups.forEach { key, value ->
            val char = key.second
            val amount = value
            val existing = get(char)
            if (existing != null)
                put(char, amount + (existing as Long))
            else
                put(char, amount)
            0
        }
        val existing = get(first)
        if (existing != null)
            put(first, (existing as Long) + 1)
        else
            put(first, 1)

        0
    }

fun findLowestAndHighest(map: Map<Char, Long>) =
    Pair(map.minOf { it.value }, map.maxOf { it.value })

fun day14() {
    println("\nDay14\nPolymer\n==================")

    val input = ResLoader.readlines("input_14")

    val recipe = parseInput(input)
    val groupedPolymer = createGroupedPolymer(recipe.polymer)


    val actual = (1..40).fold(groupedPolymer.elementGroups) { acc, _ ->
        doGroupedExpansion(acc, recipe.instructions)
    }

    val charMap = countLetters(groupedPolymer.firstElement, actual)
    val (lowest, highest) = findLowestAndHighest(charMap)

    println("Highest: $highest, lowest: $lowest -> Result: ${highest - lowest}")


}
