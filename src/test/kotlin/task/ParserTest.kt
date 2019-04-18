package task

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows

class ParserTest {

    @Test
    fun flags() {
        assertTrue(Parser(arrayOf("-l", "-r", "-o", "out.file", "in.directory")).output)
        assertFalse(Parser(arrayOf("-l", "-r", "in.directory")).output)

        assertTrue(Parser(arrayOf("-l", "-r", "-o", "out.file", "in.directory")).long)
        assertFalse(Parser(arrayOf("-r", "-o", "out.file", "in.directory")).long)
        assertFalse(Parser(arrayOf("-o", "-l", "in.directory")).long)

        assertTrue(Parser(arrayOf("-l", "-r", "-o", "out.file", "in.directory")).reverse)
        assertFalse(Parser(arrayOf("-l", "-o", "out.file", "in.directory")).reverse)
        assertFalse(Parser(arrayOf("-o", "-r", "-l", "in.directory")).reverse)

        assertTrue(Parser(arrayOf("-r", "-o", "out.file", "-h", "in.directory")).human)
        assertFalse(Parser(arrayOf("-r", "-o", "out.file", "in.directory")).human)
        assertFalse(Parser(arrayOf("-r", "-o", "-h", "in.directory")).human)
    }

    @Test
    fun desti() {
        assertEquals("out.file", Parser(arrayOf("-o", "out.file", "in.directory")).destination)
        assertEquals("*", Parser(arrayOf("-l", "-r", "in.directory")).destination)
        // То, что стоит после "-о" (если это не inputFile) - всегда destination
        assertEquals("-r", Parser(arrayOf("-o", "-r", "in.directory")).destination)
    }

    @Test
    fun except() {
        assertThrows(InvalidCommandException::class.java) { Parser(arrayOf()) }
        assertThrows(InvalidCommandException::class.java)
        { Parser(arrayOf("-h", "-l", "directory")) }
        assertThrows(InvalidCommandException::class.java) { Parser(arrayOf("-r", "-o", "directory")) }

        Parser(arrayOf("-r", "-o", "-o", "directory"))

        assertThrows(InvalidCommandException::class.java) { Parser(arrayOf("-r", "-r", "-h", "directory")) }

        //Исключений не появляется, т.к. конструкция правильная
        Parser(arrayOf("-o", "-r", "-r", "-l", "directory"))
        Parser(arrayOf("-o", "-o", "-r", "-l", "directory"))
        Parser(arrayOf("-r", "-o", "-r", "-l", "directory"))

        assertThrows(InvalidCommandException::class.java) { Parser(arrayOf("-r", "wrong flag", "-l", "directory")) }
        assertThrows(InvalidCommandException::class.java)
        { Parser(arrayOf("-r", "-o", "out.file", "Tuturu", "directory")) }
    }
}