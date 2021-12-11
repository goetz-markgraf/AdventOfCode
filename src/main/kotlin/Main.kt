import java.io.File

class ResLoader {
    companion object {
        fun readlines(str: String) =
            File(ResLoader::class.java.getResource(str).toURI()).readLines()

        fun readText(str: String) =
            File(ResLoader::class.java.getResource(str).toURI()).readText()
    }
}


fun main(args: Array<String>) {
//    day05()
//    day06()
//    day07()
//    day08()
//    day09()
//    day10()
    day11()
}
