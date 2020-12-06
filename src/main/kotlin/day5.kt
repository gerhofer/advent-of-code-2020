import kotlin.math.ceil
import kotlin.math.floor

object Day5 {

    fun partOne(filePath: String): Int {
        val seatNumbers = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { getSeatNumber(it) }

        return seatNumbers.maxOf { it.getAbsoluteNumber() }
    }

    fun partTwo(filePath: String): Int {
        val seatNumbers = javaClass.getResource(filePath).readText()
            .split("\r\n")
            .map { getSeatNumber(it) }
            .map { it.getAbsoluteNumber() }

        val max = seatNumbers.max()!!
        val min = seatNumbers.min()!!

        return (min..max).first { !seatNumbers.contains(it) }!!
    }

}

val ROWS = 127
val COLS = 7

data class SeatNumber(
    val row: Int,
    val col: Int
) {

    fun getAbsoluteNumber() : Int {
        return row * 8 + col;
    }

}

fun getSeatNumber(binary: String): SeatNumber {
    return SeatNumber(getRow(binary), getColumn(binary))
}

fun getRow(binary: String) : Int {
    val rowDefinitioin = binary.replace(Regex("[LR]"), "")
    var lowerBound = 0
    var upperBound = ROWS

    for (letter in rowDefinitioin) {
        when (letter) {
            'B' -> {
                lowerBound = ceil(getMiddle(lowerBound, upperBound)).toInt()
            }
            'F' -> {
                upperBound = floor(getMiddle(lowerBound, upperBound)).toInt()
            }
        }
    }

    return lowerBound
}

fun getColumn(binary: String) : Int {
    val columnDefinitioin = binary.replace(Regex("[FB]"), "")
    var lowerBound = 0
    var upperBound = COLS

    for (letter in columnDefinitioin) {
        when (letter) {
            'R' -> {
                lowerBound = ceil(getMiddle(lowerBound, upperBound)).toInt()
            }
            'L' -> {
                upperBound = floor(getMiddle(lowerBound, upperBound)).toInt()
            }
        }
    }

    return lowerBound

}

private fun getMiddle(lowerBound: Int, upperBound: Int) : Double = lowerBound + (upperBound - lowerBound) / 2.0