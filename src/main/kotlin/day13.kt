object Day13 {

    fun partOne(filePath: String): Long {
        return 0
    }

    fun partTwo(filePath: String): Long {
        val busses = javaClass.getResource(filePath).readText()
            .split("\r\n")[1]
            .split(",")
            .mapIndexed { index, it -> if (it == "x") { null } else { Bus(it.toLong(), index) }}
            .filterNotNull()

        var startingPoint = 0L
        var stepSize = busses[0].id

        for (bus in busses.drop(1)) {
            startingPoint = findFirst(startingPoint, stepSize, bus)
            stepSize *= bus.id
        }

        return startingPoint
    }

    private fun findFirst(currentStartingPoint: Long, stepSize: Long, bus: Bus): Long {
        var point = currentStartingPoint
        while(true) {
            if ((point + bus.offset) % bus.id == 0L) {
                return point
            } else {
                point += stepSize
            }
        }
    }

}

data class Bus(
    val id: Long,
    val offset: Int
) {

}