package task

import java.io.BufferedWriter
import java.io.File
import java.util.*

fun main(input: Array<String>) {
    try {
        val parse = Parser(input)
        val destination = File(parse.destination)
        val start = File(parse.inputFile)

        if (!start.exists()) {
            println("Incorrect input")
            return
        }
        if (parse.output && destination.isDirectory) {
            println("Incorrect output")
            return
        }

        var writer: BufferedWriter? = null
        if (parse.output) writer = destination.bufferedWriter()

        if (!start.isDirectory) {
            val sum = info(start, parse.long, parse.human)
            if (parse.output) {
                writer!!.write(sum)
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
                for (elem in sum) {
                    writer!!.write(elem)
                    writer.newLine()
                }
                writer!!.close()
            } else for (elem in sum) println(elem)
        }
    } catch (e: InvalidCommandException) {
        println("Incorrect command")
    }
}


fun info(input: File, long: Boolean, human: Boolean): String {
    var sum = input.name

    if (human && long) {
        val properties = buildString {
            append(if (input.canRead()) 'R' else '-')
            append(if (input.canWrite()) 'W' else '-')
            append(if (input.canExecute()) 'X' else '-')
        }
        val size = buildString {
            var length = input.length()
            val measures = listOf("B", "Kb", "Mb", "Gb", "Tb")
            for (i in 0..5) {
                if (i != 0) insert(0, ", ")
                insert(0, "${length % 1024} ${measures[i]}")
                if (length < 1024) break
                length /= 1024
            }
        }
        sum += " - $size; ${Date(input.lastModified())} modified; $properties"
        return sum
    }

    if (long) {
        val properties = buildString {
            append(if (input.canRead()) 1 else 0)
            append(if (input.canWrite()) 1 else 0)
            append(if (input.canExecute()) 1 else 0)
        }
        val measures = listOf("B", "Kb", "Mb", "Gb", "Tb")
        var length = input.length().toDouble()
        for (i in 0..4) {
            if (length >= 1024) length /= 1024
            else {
                length = (length * 10).toInt() / 10.0
                sum += " - $length ${measures[i]}; ${Date(input.lastModified())} modified; $properties"
                return sum
            }
        }
    }
    return sum
}