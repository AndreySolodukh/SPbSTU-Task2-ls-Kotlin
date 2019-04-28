package task

data class Parser(val input: Array<String>) {

    // input format: [-l] [-h] [-r] [-o] <-> ["output.file"] "directory_or_file"

    var destination = "*"
    var long = false
    var human = false
    var reverse = false
    var output = false

    init {
        if (input.isEmpty()) throw InvalidCommandException()
        var isDesti = false
        for (i in 0 until input.lastIndex)
            when {
                isDesti -> {
                    destination = input[i]
                    isDesti = false
                }
                input[i] == "-l" && !long -> long = true
                input[i] == "-r" && !reverse -> reverse = true
                input[i] == "-h" && !human -> human = true
                input[i] == "-o" && !output -> {
                    output = true
                    isDesti = true
                }
                else -> throw InvalidCommandException()
            }
        if (output && isDesti) throw InvalidCommandException()
    }

    // Записать inputFile сразу нельзя, сначала нужно провести проверку на пустую строку
    val inputFile = input.last()
}