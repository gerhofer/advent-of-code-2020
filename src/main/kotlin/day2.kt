object Day2 {

    fun partOne(filePath: String): Int {
        val policyChecks = javaClass.getResource(filePath).readText()
            .split("\n")
            .map { line -> PolicyCheck(line) }

        return policyChecks.filter { it.compliesWithPolicyA() }
            .count()
    }

    fun partTwo(filePath: String): Int {
        val policyChecks = javaClass.getResource(filePath).readText()
            .split("\n")
            .map { line -> PolicyCheck(line) }

        return policyChecks.filter { it.compliesWithPolicyB() }
            .count()
    }

}

data class PolicyCheck(
    val minimum: Int,
    val maximum: Int,
    val character: Char,
    val password: String
) {

    constructor(line: String) :
            this(
                minimum = line.substringBefore("-").toInt(),
                maximum = line.substringAfter("-").substringBefore(" ").toInt(),
                character = line.substringAfter(" ").substringBefore(":").trim()[0],
                password = line.substringAfter(":").trim()
            )

    fun compliesWithPolicyA() : Boolean {
        return (minimum..maximum).contains(password.count { char -> char == character })
    }

    fun compliesWithPolicyB() : Boolean {
        return (password[minimum-1] == character) xor (password[maximum-1] == character)
    }

}