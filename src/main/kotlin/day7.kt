object Day7 {

    fun partOne(filePath: String): Int {
        val bagMapping = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { bagDefintion ->
                val (bag, containedBagList) = bagDefintion.split(" contain ")
                val containedBags = containedBagList.split(",")
                    .filter { !it.contains("no other bags") }
                    .map {
                        it.replace(Regex("[0-9]*"), "")
                            .replace(Regex("bags|bag"), "")
                            .replace(".", "")
                            .trim()
                    }
                bag.replace(Regex("bags$"), "").trim() to containedBags
            }.toMap()

        val occurances = findOccurances(bagMapping, "shiny gold")

        println(occurances)

        return occurances.size
    }

    fun partTwo(filePath: String): Int {
        val bagMapping = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { bagDefintion ->
                val (bag, containedBagList) = bagDefintion.split(" contain ")
                val containedBags = containedBagList.split(",")
                    .filter { !it.contains("no other bags") }
                    .map { Bag(it) }
                bag.replace(Regex("bags$"), "").trim() to containedBags
            }.toMap()

        println(bagMapping)

        return count(bagMapping, "shiny gold")
    }

    fun count(bagMap: Map<String, List<Bag>>, bag: String) : Int {
        var count = 0
        for (containedBag in bagMap[bag]!!) {
            count += containedBag.amount + containedBag.amount * count(bagMap, containedBag.color)
        }
        return count
    }

    fun findOccurances(bagMap: Map<String, List<String>>, searchedBag: String): Set<String> {
        val bagsContainingShiny = mutableSetOf<String>()
        for ((currentBag, containedBags) in bagMap) {
            if (containedBags.contains(searchedBag)) {
                bagsContainingShiny.add(currentBag);
                bagsContainingShiny.addAll(findOccurances(bagMap, currentBag))
            }
        }
        return bagsContainingShiny
    }

}

data class Bag(
    val color: String,
    val amount: Int
) {
    constructor(input: String) : this(
        input.trim().substringAfter(" ").replace(Regex("bags\\.|bag\\.|bags|bag"), "").trim(),
        input.trim().substringBefore(" ").toInt()
    )
}