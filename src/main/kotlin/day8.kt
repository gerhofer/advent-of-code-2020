object Day8 {

    fun partOne(filePath: String): Int {
        val code = javaClass.getResource(filePath).readText()
            .split("\r\n")

        var accumulator = 0
        var index = 0
        val occurredIndices = mutableSetOf<Int>()

        while (true) {
            if (occurredIndices.contains(index)) {
                return accumulator
            }
            occurredIndices.add(index)
            val codeLine = code[index]
            when {
                codeLine.startsWith("acc") -> {
                    accumulator += getArgument(codeLine)
                    index++
                }
                codeLine.startsWith("nop") -> index++
                codeLine.startsWith("jmp") -> index += getArgument(codeLine)
            }
        }
    }

    private fun getArgument(codeLine: String) = codeLine.split(" ")[1].toInt()

    fun partTwo(filePath: String): Int {
        val code = javaClass.getResource(filePath).readText()
            .split("\r\n")

        var trial = 0

        while (true) {
            val modifiedCode = code.toMutableList()
            val indexToBeSwitched = code.subList(trial, code.size).indexOfFirst { !it.startsWith("acc") }
            modifiedCode[trial + indexToBeSwitched] = if (code[trial +indexToBeSwitched].contains("nop")) {
                code[trial +indexToBeSwitched].replace("nop", "jmp")
            } else {
                code[trial +indexToBeSwitched].replace("jmp", "nop")
            }

            val result = runProgram(modifiedCode)
            if (result != null) {
                return result
            }
            trial++
        }
    }

    // returns null for a loop
    private fun runProgram(code: List<String>) : Int? {
        var accumulator = 0
        var index = 0
        val occurredIndices = mutableSetOf<Int>()

        while (index < code.size) {
            if (occurredIndices.contains(index)) {
                return null
            }
            occurredIndices.add(index)
            val codeLine = code[index]
            when {
                codeLine.startsWith("acc") -> {
                    accumulator += getArgument(codeLine)
                    index++
                }
                codeLine.startsWith("nop") -> index++
                codeLine.startsWith("jmp") -> index += getArgument(codeLine)
            }
        }

        return accumulator;
    }

}

