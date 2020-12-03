object Day3 {

    fun partOne(filePath: String): Int {
        val treeMap = TreeMap(javaClass.getResource(filePath).readText())

        val xJump = 3
        val yJump = 1

        val stepCount = treeMap.numberOfRows() / yJump
        val searchedCells = (1..stepCount).map { Cell((it * xJump) % treeMap.numberOfColumns(), yJump * it, false) }

        return treeMap.cells
            .filter { cell -> searchedCells.contains(cell) }
            .filter { cell -> cell.hasTree }
            .count()
    }

    fun partTwo(filePath: String): Long {
        val treeMap = TreeMap(javaClass.getResource(filePath).readText())
        return listOf(
            Step(1, 1), Step(3, 1), Step(5, 1), Step(7, 1), Step(1, 2)
        ).map { step ->
            val stepCount = treeMap.numberOfRows() / step.y
            val searchedCells =
                (1..stepCount).map { Cell((it * step.x) % treeMap.numberOfColumns(), step.y * it, false) }
            treeMap.cells
                .filter { cell -> searchedCells.contains(cell) }
                .filter { cell -> cell.hasTree }
                .count()
                .toLong()
        }.reduce { a, b -> a * b }
    }

}

data class TreeMap(
    val cells: List<Cell>
) {
    constructor(input: String) : this(input.split("\r\n")
        .mapIndexed { row, s ->
            s.split("")
                .filter { it.isNotEmpty() }
                .mapIndexed { column, character -> Cell(column, row, character == "#") }
        }
        .flatten()
    )

    fun numberOfColumns(): Int {
        return cells.map { it.x }.max()!! + 1
    }

    fun numberOfRows(): Int {
        return cells.map { it.y }.max()!! + 1
    }
}

data class Step(
    val x: Int,
    val y: Int
)

data class Cell(
    val x: Int,
    val y: Int,
    val hasTree: Boolean
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cell

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}
