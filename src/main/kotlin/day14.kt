object Day14 {

    fun partOne(filePath: String): Long {
        val lines = javaClass.getResource(filePath).readText()
                .split("\r\n")
        val memory: MutableMap<Long, Long> = mutableMapOf()
        var currentBitMask: BitMask? = null

        for (line in lines) {
            val parts = line.split("=")
            if (parts[0].startsWith("mask")) {
                currentBitMask = BitMask(parts[1].trim())
            } else if (parts[0].startsWith("mem")) {
                val address = parts[0].substringAfter("[").substringBefore("]").toLong()
                memory[address] = currentBitMask!!.and(parts[1].trim().toLong())
            }
        }

        println(memory)

        return memory.values.sum()
    }

    fun partTwo(filePath: String): Long {
        val lines = javaClass.getResource(filePath).readText()
                .split("\r\n")
        val memory: MutableMap<Long, Long> = mutableMapOf()
        var currentBitMask: BitMask? = null

        for (line in lines) {
            val parts = line.split("=")
            if (parts[0].startsWith("mask")) {
                currentBitMask = BitMask(parts[1].trim())
            } else if (parts[0].startsWith("mem")) {
                val address = parts[0].substringAfter("[").substringBefore("]").toLong()
                currentBitMask!!.getPossibleAddresses(address)
                        .forEach { currentAddress -> memory[currentAddress] = parts[1].trim().toLong() }
            }
        }

        println(memory)

        return memory.values.sum()
    }

}

data class BitMask(
        val bitMask: String
) {

    fun getXIndices(): List<Int> {
        return bitMask.mapIndexed { index, c ->
            if (c == 'X') {
                index
            } else {
                null
            }
        }.filterNotNull()
    }

    fun and(value: Long): Long {
        return getOnesOnly() or value and getZeroesOnly()
    }

    fun getOnesOnly(): Long {
        return bitMask.replace("X", "0").toLong(2)
    }

    fun getZeroesOnly(): Long {
        return bitMask.replace("X", "1").toLong(2)
    }

    fun getPossibleAddresses(address: Long): List<Long> {
        val startingAddress = getOnesOnly() or address
        return getAddresses(startingAddress, 0)
    }

    private fun getAddresses(address: Long, indexOfIndices: Int): List<Long> {
        val first = address and singleBitForAnd(getXIndices()[indexOfIndices])
        val second = address or singleBitForOr(getXIndices()[indexOfIndices])
        val addresses = mutableListOf(first, second)
        return if (indexOfIndices == getXIndices().size - 1) {
            addresses.toList()
        } else {
            addresses.addAll(getAddresses(first, indexOfIndices + 1))
            addresses.addAll(getAddresses(second, indexOfIndices + 1))
            addresses.toList()
        }
    }

    fun singleBitForOr(bitIndex: Int): Long {
        return StringBuilder("0".repeat(bitMask.length)).also { it.setCharAt(bitIndex, '1') }
                .toString()
                .toLong(2)
    }

    fun singleBitForAnd(bitIndex: Int): Long {
        return StringBuilder("1".repeat(bitMask.length)).also { it.setCharAt(bitIndex, '0') }
                .toString()
                .toLong(2)
    }

}


