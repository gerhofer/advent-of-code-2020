object Day9 {

    fun partOne(filePath: String): Long {
        val numbers = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { it.toLong() }
        val frameSize = 5
        val preambleValues = numbers.take(frameSize)
        val possibleSums = preambleValues.mapIndexed { index, _ ->
            // for (let secondIndex = index + 1; secondIndex < frameSize; secondIndex++)
            (index + 1 until frameSize).map { secondIndex ->
                preambleValues[index] + preambleValues[secondIndex]
            }.toMutableList()
        }.toMutableList()

        println(possibleSums)

        numbers.drop(frameSize)
            .forEachIndexed { index, number ->
                if (!possibleSums.flatten().contains(number)) {
                    return number
                }
                possibleSums.forEachIndexed { i, list ->
                    list.add(numbers[index + i] + number)
                }
                possibleSums.removeAt(0)
                possibleSums.add(mutableListOf())
            }

        return 0
    }

    fun partTwo(filePath: String): Long {
        val numbers = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { it.toLong() }
        val seekedSum = 32321523L
        var lower = 0
        var upper = 0
        var sum = 0L

        while (true) {
            if (sum == seekedSum) {
                val range = numbers.subList(lower, upper)
                val min = range.min()!!
                val max = range.max()!!
                return min + max
            } else if (sum > seekedSum) {
                sum -= numbers[lower]
                lower++
            } else {
                sum += numbers[upper]
                upper++
            }
        }
    }

}