object Day11 {

    fun partOne(filePath: String): Int {
        var startGeneration = SeatArrangement(javaClass.getResource(filePath).readText())

        while (true) {
            val nextGeneration = nextGeneration(startGeneration)
            if (startGeneration == nextGeneration) {
                break
            } else {
                startGeneration = nextGeneration
            }
        }

        return startGeneration.seats.flatten().count { it == "#" }
    }

    fun partTwo(filePath: String): Int {
        var startGeneration = SeatArrangement(javaClass.getResource(filePath).readText())

        while (true) {
            val nextGeneration = nextGeneration2(startGeneration)
            if (startGeneration == nextGeneration) {
                break
            } else {
                startGeneration = nextGeneration
            }
        }

        return startGeneration.seats.flatten().count { it == "#" }    }

    private fun nextGeneration(generation: SeatArrangement): SeatArrangement {
        val nextGeneration = SeatArrangement(generation.toString())

        for (row in 0 until generation.seats.size) {
            for (col in 0 until generation.seats[0].size) {
                val takenNeighbouringSeats = generation.directNeighbouringseats(row, col)
                if (generation.isEmpty(row, col) && takenNeighbouringSeats <= 0) {
                    nextGeneration.seats[row][col] = "#"
                } else if (generation.isTaken(row, col) && takenNeighbouringSeats >= 4) {
                    nextGeneration.seats[row][col] = "L"
                }
            }
        }

        return nextGeneration
    }

    private fun nextGeneration2(generation: SeatArrangement): SeatArrangement {
        val nextGeneration = SeatArrangement(generation.toString())

        for (row in 0 until generation.seats.size) {
            for (col in 0 until generation.seats[0].size) {
                val takenNeighbouringSeats = generation.indirectNeighbouringseats(row, col)
                if (generation.isEmpty(row, col) && takenNeighbouringSeats <= 0) {
                    nextGeneration.seats[row][col] = "#"
                } else if (generation.isTaken(row, col) && takenNeighbouringSeats >= 5) {
                    nextGeneration.seats[row][col] = "L"
                }
            }
        }

        return nextGeneration
    }

}

data class SeatArrangement(
    val seats: MutableList<MutableList<String>>
) {

    constructor(input: String) : this(
        input.split("\r\n")
            .map {
                it.split("")
                    .filter { it.isNotEmpty() }
                    .toMutableList()
            }.toMutableList()
    )

    fun directNeighbouringseats(row: Int, col: Int): Int {
        return (-1..1).flatMap { rowModifier ->
            (-1..1).map { colModifier ->
                if (rowModifier != 0 || colModifier != 0) {
                    isTaken(row + rowModifier, col + colModifier)
                } else {
                    false
                }
            }
        }.count { it }
    }

    fun indirectNeighbouringseats(row: Int, col: Int): Int {
        return (-1..1).flatMap { rowModifier ->
            (-1..1).map { colModifier ->
                if (rowModifier != 0 || colModifier != 0) {
                    var currentRowModifier = rowModifier
                    var currentColModifier = colModifier
                    var returnValue: Boolean? = null
                    while (returnValue == null) {
                        val current = get(row + currentRowModifier, col + currentColModifier)
                        if (current == "#") {
                            returnValue = true
                        } else if (current == "L" || current == null) {
                            returnValue = false
                        } else {
                            currentRowModifier += rowModifier
                            currentColModifier += colModifier
                        }
                    }
                    returnValue
                } else {
                    false
                }
            }
        }.count{ it }
    }

    fun get(row: Int, col: Int): String? {
        return if (row in 0 until seats.size && col in 0 until seats[0].size) {
            seats[row][col]
        } else {
            null
        }
    }

    fun isTaken(row: Int, col: Int): Boolean {
        return if (row in 0 until seats.size && col in 0 until seats[0].size) {
            seats[row][col] == "#"
        } else {
            false
        }
    }

    fun isEmpty(row: Int, col: Int): Boolean {
        return if (row in 0 until seats.size && col in 0 until seats[0].size) {
            seats[row][col] == "L"
        } else {
            false
        }
    }

    override fun toString(): String {
        return seats.map { it.joinToString("") }.joinToString("\r\n")
    }

}