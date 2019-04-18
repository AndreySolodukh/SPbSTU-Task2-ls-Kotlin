package task

data class Parser(val input: Array<String>) {

    // input format: [-l] [-h] [-r] [-o] <-> ["output.file"] "directory_or_file"

    // <0 until input.size> - последним всегда идет inputFile (+ это проверка на наличие)
    val output = input.indexOf("-o") in 0 until input.size
    val destination = if (output) input[input.indexOf("-o") + 1] else "*"

    // Логика: у нас есть [1] && [2].
    // [1] должно выполняться всегда.
    // [2] при отсутствии output всегда true, так что "indexOf(флаг) != indexOf(-o) <-> != 0" ни на что не влияет.
    // При наличии output оно отсеивает destination, который может иметь вид флага.
    val long = input.indexOf("-l") in 0 until input.size && (!output || input.indexOf("-l") != input.indexOf("-o") + 1)
    val human = input.indexOf("-h") in 0 until input.size && (!output || input.indexOf("-h") != input.indexOf("-o") + 1)
    val reverse = input.indexOf("-r") in 0 until input.size &&
            (!output || input.indexOf("-r") != input.indexOf("-o") + 1)


    val exceptions = when {
        input.isEmpty() -> throw InvalidCommandException()
    // -l и -h - взиамоисключающие друг друга флаги.
        human && long -> throw InvalidCommandException()
    //-o не может быть последним флагом
        input.dropLast(1).lastIndex == input.dropLast(1).indexOf("-o")
        -> throw InvalidCommandException()
    // Последний элемент input - это рабочая директория/файл, поэтому может иметь любой вид (включая -o, -l и т.д.)
    // Проверка на повторяющиеся флаги
        (!output && (input.dropLast(1).toSet().size != input.dropLast(1).size)) ||
                (output && arrayOf("-h", "-r", "-l", "-o").any
                { input.dropLast(1).indexOf(it) != input.indexOf("-o") + 1 &&
                        input.dropLast(1).lastIndexOf(it) != input.indexOf("-o") + 1 &&
                        input.indexOf(it) != input.lastIndexOf(it)})
        -> throw InvalidCommandException()
    // Если "-o" нет, а на местах для флагов есть что-то ещё, то это нарушение
    // ИЛИ
    // Если "-о" есть, а на местах для флагов (кроме позиции после "-о") есть что-то ещё, то это нарушение
        (!output && input.dropLast(1).any { it !in arrayOf("-r", "-h", "-l") }) ||
                (output && input.dropLast(1).any {
                    input.indexOf(it) != input.indexOf("-o") + 1 && it !in arrayOf("-r", "-h", "-l", "-o")
                }) -> throw InvalidCommandException()
        else -> false
    }

    // Он не нужен в exceptions. Пусть стоит здесь.
    val inputFile = input.last()

}