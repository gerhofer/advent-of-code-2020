object Day4 {

    fun partOne(filePath: String): Int {
        val passports : List<Passport> = javaClass.getResource(filePath).readText()
            .split("\r\n\r\n")
            .map { Passport(it) }

        return passports.filter { it.hasRequiredFields() }
            .count()
    }

    fun partTwo(filePath: String): Int {
        val passports = javaClass.getResource(filePath).readText()
            .split("\r\n\r\n")
            .map { Passport(it) }

        return passports.filter { it.hasRequiredFields() && it.isValid() }
            .count()
    }

}

val REQUIRED_FIELDS = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

data class Passport(
    val properties: Map<String, String>
) {
    constructor(input: String) : this(
        input.split(Regex("\\s+"))
            .map { pair ->
                val keyAndValue = pair.split(":")
                keyAndValue[0] to keyAndValue[1]
            }.toMap()
    )

    fun hasRequiredFields(): Boolean {
        return properties.keys.containsAll(REQUIRED_FIELDS)
    }

    fun isValid(): Boolean {
        return birthYearValid() && issueYearValid() && expirationYearValid() &&
                heightValid() && hairColorValid() && eyeColorValid() && passportIdValid()
    }

    fun birthYearValid(): Boolean {
        val birthYear = properties["byr"]?.toInt()
        return birthYear != null && birthYear in 1920..2002
    }

    fun issueYearValid(): Boolean {
        val issueYear = properties["iyr"]?.toInt()
        return issueYear != null && issueYear in 2010..2020
    }

    fun expirationYearValid(): Boolean {
        val expirationYear = properties["eyr"]?.toInt()
        return expirationYear != null && expirationYear in 2020..2030
    }

    fun heightValid(): Boolean {
        val height = properties["hgt"]!!// String? => String
        return when {
            height.endsWith("cm") -> height.substringBefore("cm").toInt() in 150..193
            height.endsWith("in") -> height.substringBefore("in").toInt() in 59..76
            else -> false
        }
    }

    fun hairColorValid(): Boolean {
        val hairColor = properties["hcl"]!!
        return hairColor.matches(Regex("#[A-F0-9a-f]{6}"))
    }

    fun eyeColorValid(): Boolean {
        val eyeColor = properties["ecl"]!!
        return eyeColor in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }

    fun passportIdValid(): Boolean {
        val passportId = properties["pid"]!!
        return passportId.matches(Regex("[0-9]{9}"))
    }
}