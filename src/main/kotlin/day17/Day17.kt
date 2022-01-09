package day17

import java.lang.Integer.min
import kotlin.math.abs


// Part 1

fun findXVel(targetXMin: Int, targetXMax: Int) =
    (1..targetXMin)
        .find { (it * (it + 1) / 2) >= targetXMin && (it * (it + 1) / 2) <= targetXMax } ?: 0

fun findYVel(targetYMin: Int, targetYMax: Int) =
    abs(min(targetYMin, targetYMax)) - 1

fun findYMax(yVel: Int) =
    yVel * (yVel + 1) / 2

fun checkFit(xVel: Int, yVel: Int) =
    (yVel * 2) + 1 > xVel


// Part 2

data class Probe(
    val x: Int,
    val y: Int,
    val xVel: Int,
    val yVel: Int
)

data class ProbeTarget(
    val xRange: IntRange,
    val yRange: IntRange
)

fun nextStep(p: Probe) =
    Probe(
        x = p.x + p.xVel,
        y = p.y + p.yVel,
        xVel = when {
            p.xVel > 0 -> p.xVel - 1
            p.xVel < 0 -> p.xVel + 1
            else -> 0
        },
        yVel = p.yVel - 1
    )

fun isInTarget(p: Probe, t: ProbeTarget) =
    t.xRange.contains(p.x) && t.yRange.contains(p.y)

fun checkIfProbeHitsTarget(p: Probe, t: ProbeTarget): Boolean =
    when {
        isInTarget(p, t) -> true
        p.x > t.xRange.last -> false
        p.y < t.yRange.first -> false
        else -> checkIfProbeHitsTarget(nextStep(p), t)
    }

fun findXVelRange(xRange: IntRange) =
    findXVel(xRange.first, xRange.last)..xRange.last


fun findYVelRange(yRange: IntRange) =
    yRange.first..findYVel(yRange.first, yRange.last)


fun findAllVelocities(t: ProbeTarget) =
    buildList {
        findXVelRange(t.xRange).flatMap { x ->
            findYVelRange(t.yRange).map { y ->
                if (checkIfProbeHitsTarget(
                        Probe(0, 0, x, y),
                        t
                    )
                ) add(x to y)
            }
        }
    }

// main


fun main() {
    println("Day17\nSend a Probe\n================")

    val yResult = findYVel(-179, -124)
    val xResult = findXVel(70, 96)
    val yMax = findYMax(yResult)

    println("Initial yVel: $yResult with initial xVel: $xResult")
    println("Maximum height is: $yMax")

    val allVelocities = findAllVelocities(
        ProbeTarget(
            70..96,
            -179..-124

        )
    )

    println("There a ${allVelocities.size} possible ways to hit the target")
}
