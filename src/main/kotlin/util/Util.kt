package util

import java.io.File


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ResLoader {
    companion object {
        fun readlines(str: String) =
            File(ResLoader::class.java.getResource("/$str").toURI()).readLines()

        fun readText(str: String) =
            File(ResLoader::class.java.getResource("/$str").toURI()).readText()
    }
}

data class Point(
    val x: Int,
    val y: Int
) {
    fun translate(dx: Int, dy: Int) =
        Point(x + dx, y + dy)
}
