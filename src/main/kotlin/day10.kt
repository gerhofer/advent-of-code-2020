import kotlin.math.pow

object Day10 {

    fun partOne(filePath: String): Int {
        val jolts = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { it.toInt() }
            .sorted()

        var singleDiffs = 0
        var tripleDiffs = 1

        jolts.fold(0) { a, b ->
            when (b - a) {
                1 -> singleDiffs++
                3 -> tripleDiffs++
                else -> println("hey this shouldn't happen")
            }
            b
        }

        println("$singleDiffs * $tripleDiffs")
        return singleDiffs * tripleDiffs
    }

    fun partTwo(filePath: String): Long {
        val jolts = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { it.toInt() }
            .sorted()
        val mapOfCounts = mutableMapOf<Int, Int>()
        var groupSize = 1

        jolts.fold(0) { a, b ->
            when (b - a) {
                1 -> groupSize++
                3 -> {
                    mapOfCounts[groupSize] = mapOfCounts.getOrDefault(groupSize, 0) + 1
                    groupSize = 1
                }
                else -> println("hey this shouldn't happen")
            }
            b
        }
        mapOfCounts[groupSize] = mapOfCounts.getOrDefault(groupSize, 0) + 1

        println(mapOfCounts)

        return (((2.0).pow(mapOfCounts[3]?:0)) *
                (4.0.pow(mapOfCounts[4]?:0)) *
                (7.0.pow(mapOfCounts[5]?:0))).toLong()
    }
}