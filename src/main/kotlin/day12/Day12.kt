package day12

import util.ResLoader

typealias CaveSystem = List<Pair<String, String>>

typealias Route = List<String>

fun createCaveSystem(input: Route) =
    input.map { line ->
        line.split("-").let {
            Pair(it[0], it[1])
        }
    }

private fun isRouteReady(route: Route) =
    route.last() == "end"

private fun String.isLowercase() =
    this.all { it.isLowerCase() }

private fun isRouteWrong(route: Route) =
    when {
        route.last() == "start" && route.size > 1 -> true
        else -> false
    }

private fun getCaves(system: CaveSystem, cave: String) =
    system.mapNotNull {
        if (it.first == cave) it.second
        else if (it.second == cave) it.first
        else null
    }

private fun mayVisitThisCave(route: Route, cave: String): Boolean {
    if (!cave.isLowercase()) return true
    if (cave == "end") return true
    val lowercaseCaves = route.filter { it.isLowercase() }.groupBy { it }
    return lowercaseCaves.all { it.value.size == 1 }
            || !lowercaseCaves.keys.contains(cave)
}

fun findRoutes(system: CaveSystem, routes: List<Route>, mayVisitSmallCaveTwice: Boolean = false): List<Route> {
    if (routes.isEmpty()) return emptyList()

    val head = routes.first()
    val tail = routes.drop(1)

    if (isRouteReady(head)) {
        return findRoutes(system, tail, mayVisitSmallCaveTwice) + listOf(head)
    }

    if (isRouteWrong(head)) return findRoutes(system, tail, mayVisitSmallCaveTwice)

    val newCaves = getCaves(system, head.last()).filter {
        it != "start"
    }.filter {
        if (!mayVisitSmallCaveTwice) {
            (!it.isLowercase() || !head.contains(it))
        } else {
            mayVisitThisCave(head, it)
        }
    }

    val newRoutes = newCaves.map {
        head + it
    }
    val allRoutes = newRoutes + tail

    return findRoutes(system, allRoutes, mayVisitSmallCaveTwice)
}

fun main() {
    println("Day12\nCaves\n=================")

    val system = createCaveSystem(
        ResLoader.readlines("input_12")
    )

    val routes = findRoutes(system, listOf(listOf("start")))

    println("Number of Routes: ${routes.size}")

    val routesEnhanced = findRoutes(
        system,
        listOf(listOf("start")),
        true
    )

    println("Number of Routes: ${routesEnhanced.size}")
}
