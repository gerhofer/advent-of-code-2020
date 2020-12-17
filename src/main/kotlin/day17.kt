object Day17 {

    fun partOne(filePath: String): Int {
        var generation = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .flatMapIndexed { row, line ->
                line.split("")
                    .filter { it.isNotEmpty() }
                    .mapIndexed { col, element -> Coordinate(row, col, 0) to (element.trim() == "#") }
            }.toMap()

        (1..6).forEach { cycle ->
            val nextGeneration = nextGeneration(generation)
            println("Cycle #$cycle")
            generation = nextGeneration
            generation.prettyPrint()
        }

        return generation.values.count { it }
    }

    fun partTwo(filePath: String): Long {
        val input = javaClass.getResource(filePath).readText()

        return 0
    }

}

fun nextGeneration(generation: Generation): Generation {
    val minX = generation.keys.minByOrNull { it.x }!!.x - 1
    val maxX = generation.keys.maxByOrNull { it.x }!!.x + 1
    val minY = generation.keys.minByOrNull { it.y }!!.y - 1
    val maxY = generation.keys.maxByOrNull { it.y }!!.y + 1
    val minZ = generation.keys.minByOrNull { it.z }!!.z - 1
    val maxZ = generation.keys.maxByOrNull { it.z }!!.z + 1

    return (minX..maxX).flatMap { x ->
        (minY..maxY).flatMap { y ->
            (minZ..maxZ).map { z ->
                val coordinate = Coordinate(x, y, z)
                val numberOfActiveNeighbours = coordinate.getNeighbours()
                    .map { generation.getState(it) }
                    .count { it }
                val state = generation.getState(coordinate)
                val nextState = nextState(state, numberOfActiveNeighbours)
                //println(
                //    "$coordinate has $numberOfActiveNeighbours neighbours and therfore is ${
                //        if (nextState) {
                //            "active"
                //        } else {
                //            "inactive"
                //        }
                //    } "
                //)
                coordinate to nextState
            }
        }
    }.toMap()
}

fun nextState(state: State, count: Int): State {
    return if (state) {
        count == 2 || count == 3
    } else {
        count == 3
    }
}

typealias Generation = Map<Coordinate, State>
typealias State = Boolean

fun Generation.getState(x: Int, y: Int, z: Int): State {
    return this.getOrDefault(Coordinate(x, y, z), false)
}

fun Generation.getState(coordinate: Coordinate): State {
    return this.getOrDefault(coordinate, false)
}

fun Generation.prettyPrint() {
    val minX = this.keys.minByOrNull { it.x }!!.x
    val maxX = this.keys.maxByOrNull { it.x }!!.x
    val minY = this.keys.minByOrNull { it.y }!!.y
    val maxY = this.keys.maxByOrNull { it.y }!!.y
    val minZ = this.keys.minByOrNull { it.z }!!.z
    val maxZ = this.keys.maxByOrNull { it.z }!!.z

    (minZ..maxZ).forEach { z ->
        println("Z = $z")
        (minX..maxX).forEach { x ->
            (minY..maxY).forEach { y ->
                print(
                    if (this.getState(Coordinate(x, y, z))) {
                        "#"
                    } else {
                        "."
                    }
                )
            }
            println("")
        }
    }
}

data class Coordinate(
    val x: Int,
    val y: Int,
    val z: Int
) {

    fun getNeighbours(): Set<Coordinate> {
        val neighbours = (-1..1).flatMap { xMod ->
            (-1..1).flatMap { yMod ->
                (-1..1).map { zMod ->
                    Coordinate(x + xMod, y + yMod, z + zMod)
                }
            }
        }

        return neighbours.filter { it != this }
            .toSet()
    }

}