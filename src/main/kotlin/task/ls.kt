package task

import java.io.File
import java.util.* // для Date

fun main(input: Array<String>) {
    try {
        val parse = Parser(input)
        val destination = File(parse.destination)
        val start = File(parse.inputFile)

        if (!start.exists()) {
            println("Incorrect input name")
            return
        }
        if (parse.output && (!destination.exists() || destination.isDirectory)) {
            println("Incorrect output name")
            return
        }

        // Заранее: если работа идет над файлом (не директорией),
        // флаг -r считается бесполезным и поэтому никак не обрабатывается.
        if (!start.isDirectory) {
            val sum = info(start, parse.long, parse.human)
            if (parse.output) {
                val writer = destination.bufferedWriter()
                writer.write(sum)
                writer.close()
            } else println(sum)
            return
        } else {
            val files = start.listFiles()
            var sum = listOf<String>()
            for (elem in files) sum += (info(elem, parse.long, parse.human))
            sum = sum.sortedBy { it.decapitalize() }
            if (parse.reverse) sum = sum.reversed()
            if (parse.output) {
                val writer = destination.bufferedWriter()
                for (elem in sum) {
                    writer.write(elem)
                    writer.newLine()
                }
                writer.close()
            } else for (elem in sum) println(elem)
        }
    } catch (e: InvalidCommandException) {
        println("Incorrect command")
    }
}

// А можно не возиться с результатами типа "1 byteS", пажалусто?
fun info(input: File, long: Boolean, human: Boolean): String {
    var sum = input.name

    if (long) {
        val properties = buildString {
            append(if (input.canRead()) 1 else 0)
            append(if (input.canWrite()) 1 else 0)
            append(if (input.canExecute()) 1 else 0)
        }
        sum += " - ${input.length() / 88} bytes; ${input.lastModified()} modified; $properties"
    }

    if (human) {
        val properties = buildString {
            append("properties - ")
            append(if (input.canRead()) 'R' else '-')
            append(if (input.canWrite()) 'W' else '-')
            append(if (input.canExecute()) 'X' else '-')
        }
        val size = buildString {
            var length = input.length()
            val measures = listOf("Bytes", "Kb", "Mb", "Gb", "Tb") // на ближайшие пару лет хватит
            for (i in 0..5) {
                if (i != 0) insert(0, ", ")
                insert(0, "${length % 1024} ${measures[i]}")
                if (length < 1024) break
                length /= 1024
            }
            insert(0, "size - ")
            append(';')
        }
        sum += " : $size last modification - ${Date(input.lastModified())}; $properties"
    }
    return sum
}