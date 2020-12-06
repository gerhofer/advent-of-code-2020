object Day6 {

    fun partOne(filePath: String): Int {
        val uniqueAnswersPerGroup = javaClass.getResource(filePath).readText()
            .split("\r\n\r\n")
            .map { it.split(Regex("\\s+"))
                .flatMap { singlePersonAnswers -> singlePersonAnswers.split("") }
                .filter { singleAnswer -> singleAnswer.isNotEmpty() }
                .toSet()
            }

        return uniqueAnswersPerGroup.fold(0) { sum, singleSet -> sum + singleSet.size }
    }

    fun partTwo(filePath: String): Int {
        val allAnsweredWithYesPerGroup = javaClass.getResource(filePath).readText()
            .split("\r\n\r\n")
            .map { it.split(Regex("\\s+"))
                .map { singlePersonAnswers -> singlePersonAnswers.split("")
                    .filter { singleAnswer -> singleAnswer.isNotEmpty() }
                    .toSet() }
                .reduce { first, second -> first intersect second }
            }

        return allAnsweredWithYesPerGroup.fold(0) { sum, singleSet -> sum + singleSet.size }
    }

}