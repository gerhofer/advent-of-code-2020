object Day1 {

    fun partOne(filePath: String): Int {
        val numbers = javaClass.getResource(filePath).readText()
            .split("\n")
            .map { line -> line.trim().toInt() }

        for (i in numbers.indices) {
            for (j in i + 1 until numbers.size) {
                if (numbers[i] + numbers[j] == 2020) {
                    return numbers[i] * numbers[j]
                }
            }
        }

        return -1
    }

    fun partTwo(filePath: String): Int {
        val numbers = javaClass.getResource(filePath).readText()
            .split("\n")
            .map { line -> line.trim().toInt() }

        for (i in numbers.indices) {
            for (j in i + 1 until numbers.size) {
                for (k in j + 1 until numbers.size) {
                    if (numbers[i] + numbers[j] + numbers[k] == 2020) {
                        return numbers[i] * numbers[j] * numbers[k]
                    }
                }
            }
        }

        return -1
    }

}