object Day16 {

    fun partOne(filePath: String): Int {
        val (rules, _, otherTickets) = javaClass.getResource(filePath).readText()
            .split("\r\n\r\n")
        val ranges = rules.split("\r\n")
            .map { it.split(":")[1].trim() }
            .map { Range("", it) }
        val ticketValues = otherTickets.split("\r\n")
            .drop(1)
            .flatMap { it.trim().split(",") }
            .map { it.toInt() }
        val ticketScanningErrorRate = ticketValues
            .filter { ranges.none { range -> range.contains(it) } }
            .sum()

        return ticketScanningErrorRate
    }

    fun partTwo(filePath: String): Long {
        val (rules, myTicket, otherTickets) = javaClass.getResource(filePath).readText()
            .split("\r\n\r\n")
        val ranges = rules.split("\r\n")
            .map { Range(it.split(":")[0].trim(), it.split(":")[1].trim()) }

        val personalTicket = myTicket.split("\r\n")[1]
            .split(",")
            .map { it.toInt() }

        val validTickets = otherTickets.split("\r\n")
            .drop(1)
            .map { it.trim().split(",").map { nr -> nr.toInt() } }
            .filter {
                it.all { x -> ranges.any { range -> range.contains(x) } }
            }

        val ticketValueMap: MutableMap<Int, MutableList<Range>> = mutableMapOf()
        for (i in personalTicket.indices) {
            for (range in ranges.toMutableList()) {

                val validForAllTickets = validTickets.map { ticket -> ticket[i] }
                    .all { range.contains(it) }
                if (validForAllTickets) {
                    val list = ticketValueMap.getOrDefault(i, mutableListOf())
                    list.add(range)
                    ticketValueMap[i] = list
                }
            }
        }

        println(ticketValueMap.map { it -> "${it.key} => ${it.value.map { it.name }}" })

        for (entry in ticketValueMap.entries.sortedBy { it.value.size }) {
            if (entry.value.size != 1) {
                println("problem")
            } else {
                ticketValueMap.entries
                    .filter { entry != it }
                    .forEach { it.value.remove(entry.value[0])}
            }
        }

        println(personalTicket)
        println(ticketValueMap.map { it -> "${it.key} => ${it.value.map { it.name }}" })

        return ticketValueMap.filter { it.value[0].name.startsWith("departure") }
            .map { it.key }
            .map { personalTicket[it] }
            .fold(1L) {a, b -> a * b.toLong()}
    }

}

data class Range(
    val name: String,
    val ranges: List<SingleRange>
) {

    constructor(name: String, string: String) : this(name,
        string.split("or")
            .map { it.trim() }
            .map { SingleRange(it) }
    )

    fun contains(x: Int): Boolean {
        return ranges.any { it.contains(x) }
    }

}

data class SingleRange(
    val lower: Int,
    val upper: Int
) {

    constructor(string: String) : this(
        string.split("-")[0].trim().toInt(),
        string.split("-")[1].trim().toInt()
    )

    fun contains(x: Int): Boolean {
        return x in lower..upper;
    }
}