package task

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class Ls {

    /**
     * Я не знаю, как проверить, правду ли программа пишет в properties.
     * Не нашел пока ни одного файла, у которого бы не было хотя бы одного из прав.
     * Даже файл без расширения можно прочитать, переписать и исполнить.
     **/

    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @Test
    fun infoTest() {
        assertEquals("EmptyDirectory",
                info(File("C:\\Users\\solod\\IdeaProjects\\ls\\input\\EmptyDirectory"),
                        false, false))
        assertEquals(
                "math.html : size - 46 Kb, 320 Bytes; last " +
                        "modification - Tue Jan 22 20:38:24 MSK 2019; properties - RWX",
                info(File("C:\\Users\\solod\\IdeaProjects\\ls\\input\\math.html"),
                        false, true))
        assertEquals("Blank.docx - 3729 bytes; 1553431579769 modified; 111",
                info(File("C:\\Users\\solod\\IdeaProjects\\ls\\input\\Blank.docx"),
                        true, false))
    }

    @Test
    fun outFileTest() {
        // Функция main сама по себе чистит output-файл, но для красоты можно добавить:
        //  fun clear(name: String) {
        //      val file = File(name).bufferedWriter()
        //      file.write("")
        //      file.close()
        //  }
        //  clear("C:\\Users\\solod\\IdeaProjects\\ls\\output\\out.txt")

        main(arrayOf("-o", "C:\\Users\\solod\\IdeaProjects\\ls\\output\\out.txt",
                "C:\\Users\\solod\\IdeaProjects\\ls\\input"))
        assertFileContent("C:\\Users\\solod\\IdeaProjects\\ls\\output\\out.txt",
                "1. В input лежит вся моя корзина, лучше ничего не открывать\n" +
                        "adder1.qar\n" +
                        "Blank.docx\n" +
                        "EmptyDirectory\n" +
                        "ImmaOnlyRead.txt\n" +
                        "math.html\n" +
                        "tmb_25708_6017.jpg\n" +
                        "VoicemodSetup.exe\n" +
                        "ФЫЗЫКАЛ ЭДУКАТИОН.txt")

        main(arrayOf("-r", "-o", "C:\\Users\\solod\\IdeaProjects\\ls\\output\\out.txt",
                "C:\\Users\\solod\\IdeaProjects\\ls\\input"))
        assertFileContent("C:\\Users\\solod\\IdeaProjects\\ls\\output\\out.txt",
                "ФЫЗЫКАЛ ЭДУКАТИОН.txt\n" +
                        "VoicemodSetup.exe\n" +
                        "tmb_25708_6017.jpg\n" +
                        "math.html\n" +
                        "ImmaOnlyRead.txt\n" +
                        "EmptyDirectory\n" +
                        "Blank.docx\n" +
                        "adder1.qar\n" +
                        "1. В input лежит вся моя корзина, лучше ничего не открывать")

        main(arrayOf("-r", "-o", "C:\\Users\\solod\\IdeaProjects\\ls\\output\\out.txt", "-l",
                "C:\\Users\\solod\\IdeaProjects\\ls\\input"))
        assertFileContent("C:\\Users\\solod\\IdeaProjects\\ls\\output\\out.txt",
                "ФЫЗЫКАЛ ЭДУКАТИОН.txt - 74380 bytes; 1552135169576 modified; 111\n" +
                        "VoicemodSetup.exe - 2269492 bytes; 1553357770458 modified; 111\n" +
                        "tmb_25708_6017.jpg - 18120 bytes; 1545584226013 modified; 111\n" +
                        "math.html - 5928 bytes; 1548178704438 modified; 111\n" +
                        "ImmaOnlyRead.txt - 0 bytes; 1555462509287 modified; 111\n" +
                        "EmptyDirectory - 0 bytes; 1555327002647 modified; 111\n" +
                        "Blank.docx - 3729 bytes; 1553431579769 modified; 111\n" +
                        "adder1.qar - 2067 bytes; 1553426484592 modified; 111\n" +
                        "1. В input лежит вся моя корзина, лучше ничего не открывать - " +
                        "0 bytes; 1555537709898 modified; 111")

        main(arrayOf("-h", "-o", "C:\\Users\\solod\\IdeaProjects\\ls\\output\\out.txt",
                "C:\\Users\\solod\\IdeaProjects\\ls\\input"))
        assertFileContent("C:\\Users\\solod\\IdeaProjects\\ls\\output\\out.txt",
                "1. В input лежит вся моя корзина, лучше ничего не открывать : size - 0 Bytes; " +
                        "last modification - Thu Apr 18 00:48:29 MSK 2019; properties - RWX\n" +
                        "adder1.qar : size - 16 Kb, 159 Bytes; " +
                        "last modification - Sun Mar 24 14:21:24 MSK 2019; properties - RWX\n" +
                        "Blank.docx : size - 29 Kb, 138 Bytes; " +
                        "last modification - Sun Mar 24 15:46:19 MSK 2019; properties - RWX\n" +
                        "EmptyDirectory : size - 0 Bytes; " +
                        "last modification - Mon Apr 15 14:16:42 MSK 2019; properties - RWX\n" +
                        "ImmaOnlyRead.txt : size - 0 Bytes; " +
                        "last modification - Wed Apr 17 03:55:09 MSK 2019; properties - RWX\n" +
                        "math.html : size - 46 Kb, 320 Bytes; " +
                        "last modification - Tue Jan 22 20:38:24 MSK 2019; properties - RWX\n" +
                        "tmb_25708_6017.jpg : size - 141 Kb, 580 Bytes; " +
                        "last modification - Sun Dec 23 19:57:06 MSK 2018; properties - RWX\n" +
                        "VoicemodSetup.exe : size - 17 Mb, 322 Kb, 416 Bytes; " +
                        "last modification - Sat Mar 23 19:16:10 MSK 2019; properties - RWX\n" +
                        "ФЫЗЫКАЛ ЭДУКАТИОН.txt : size - 581 Kb, 103 Bytes; " +
                        "last modification - Sat Mar 09 15:39:29 MSK 2019; properties - RWX")

    }
}