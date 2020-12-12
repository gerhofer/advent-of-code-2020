import kotlin.math.abs

object Day12 {

    fun partOne(filePath: String): Int {
        val navigationInstructions = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { Instruction(it) }
        var point = Point(0, 0, Direction.EAST)

        for (instruction in navigationInstructions) {
            //println("${point} moved by ${instruction} leads to ${point.move(instruction)}")
            point = point.move(instruction)
        }

        return point.getManhatten()
    }

    fun partTwo(filePath: String): Int {
        val navigationInstructions = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { Instruction(it) }
        var point = Ship(0, 0, WayPoint(10, 1))

        for (instruction in navigationInstructions) {
            println("${point} moved by ${instruction} leads to ${point.move(instruction)}")
            point = point.move(instruction)
        }

        return point.getManhatten()    }

}

data class Ship(
    val x: Int,
    val y: Int,
    val wayPoint: WayPoint
) {
    fun move(instruction: Instruction): Ship {
        return when (instruction.action) {
            'F' -> Ship(x + wayPoint.x * instruction.value, y + wayPoint.y * instruction.value, wayPoint)
            else -> Ship(x, y, wayPoint.move(instruction, this))
        }
    }

    fun getManhatten(): Int {
        return abs(x) + abs(y);
    }
}

data class WayPoint(
    val x: Int,
    val y: Int
) {

    fun move(instruction: Instruction, ship: Ship): WayPoint {
        return when (instruction.action) {
            'L' -> {
                var wayPoint = this
                (0 until (instruction.value / 90)).map {
                    wayPoint = wayPoint.turnLeftAround()
                }
                wayPoint
            }
            'R' -> {
                var wayPoint = this
                (0 until (instruction.value / 90)).map {
                    wayPoint = wayPoint.turnRightAround()
                }
                wayPoint
            }
            'N' -> WayPoint(x, y + instruction.value)
            'E' -> WayPoint(x + instruction.value, y)
            'S' -> WayPoint(x, y - instruction.value)
            'W' -> WayPoint(x - instruction.value, y)
            else -> {
                println("shouldnt happen")
                WayPoint(-1, -1)
            }
        }
    }

    fun turnLeftAround(): WayPoint {
        return WayPoint(-1 * y, this.x)
    }

    fun turnRightAround(): WayPoint {
        return WayPoint(this.y, -1 * this.x)
    }
}

data class Point(
    val x: Int,
    val y: Int,
    val direction: Direction,
) {
    fun move(instruction: Instruction): Point {
        return when (instruction.action) {
            'L' -> Point(x, y, direction.left(instruction.value))
            'R' -> Point(x, y, direction.right(instruction.value))
            'F' -> when (direction) {
                Direction.EAST -> Point(x + instruction.value, y, direction)
                Direction.NORTH -> Point(x, y + instruction.value, direction)
                Direction.WEST -> Point(x - instruction.value, y, direction)
                Direction.SOUT -> Point(x, y - instruction.value, direction)
            }
            'N' -> Point(x, y + instruction.value, direction)
            'E' -> Point(x + instruction.value, y, direction)
            'S' -> Point(x, y - instruction.value, direction)
            'W' -> Point(x - instruction.value, y, direction)
            else -> {
                println("shouldnt happen")
                Point(-1, -1, Direction.EAST)
            }
        }
    }

    fun getManhatten(): Int {
        return abs(x) + abs(y);
    }
}

data class Instruction(
    val action: Char,
    val value: Int
) {
    constructor(string: String) : this(
        string[0],
        string.substring(1).toInt()
    )
}

enum class Direction {
    EAST,
    SOUT,
    WEST,
    NORTH;

    fun left(degrees: Int): Direction {
        return values()[(4 + this.ordinal - degrees / 90) % 4]
    }

    fun right(degrees: Int): Direction {
        return values()[(4 + this.ordinal + degrees / 90) % 4]
    }
}