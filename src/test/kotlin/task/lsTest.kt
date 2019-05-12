package task

import org.junit.Test
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.assertEquals

class Ls {

    private fun clear(name: String) {
        val file = File(name).bufferedWriter()
        file.write("")
        file.close()
    }

    private fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent, content)
    }

    @AfterTest
    fun c() = clear("output\\out.txt")


    @Test
    fun infoTest() {
        assertEquals("EmptyDirectory",
                info(File("input\\EmptyDirectory"),
                        false, false))
        assertEquals("EmptyDirectory",
                info(File("ls\\input\\EmptyDirectory"),
                        false, true))
        assertEquals(
                "math.html - 46.3 Kb; Tue Jan 22 20:38:24 MSK 2019 modified; RWX",
                info(File("input\\math.html"),
                        true, true))
        assertEquals("Blank.docx - 29834 B; Sun Mar 24 15:46:19 MSK 2019 modified; 111",
                info(File("input\\Blank.docx"),
                        true, false))
    }

    @Test
    fun outFileTest() {

        main(arrayOf("-o", "output\\out.txt",
                "input"))
        assertFileContent("output\\out.txt",
                "1. В input лежит вся моя корзина, лучше ничего не открывать\n" +
                        "adder1.qar\n" +
                        "Blank.docx\n" +
                        "EmptyDirectory\n" +
                        "ImmaOnlyRead.txt\n" +
                        "math.html\n" +
                        "tmb_25708_6017.jpg\n" +
                        "VoicemodSetup.exe\n" +
                        "ФЫЗЫКАЛ ЭДУКАТИОН.txt")

        main(arrayOf("-r", "-o", "output\\out.txt",
                "input"))
        assertFileContent("output\\out.txt",
                "ФЫЗЫКАЛ ЭДУКАТИОН.txt\n" +
                        "VoicemodSetup.exe\n" +
                        "tmb_25708_6017.jpg\n" +
                        "math.html\n" +
                        "ImmaOnlyRead.txt\n" +
                        "EmptyDirectory\n" +
                        "Blank.docx\n" +
                        "adder1.qar\n" +
                        "1. В input лежит вся моя корзина, лучше ничего не открывать")

        main(arrayOf("-r", "-o", "output\\out.txt", "-l",
                "input"))
        assertFileContent("output\\out.txt",
                "ФЫЗЫКАЛ ЭДУКАТИОН.txt - 595047 B; Sat Mar 09 15:39:29 MSK 2019 modified; 111\n" +
                        "VoicemodSetup.exe - 18155936 B; Sat Mar 23 19:16:10 MSK 2019 modified; 111\n" +
                        "tmb_25708_6017.jpg - 144964 B; Sun Dec 23 19:57:06 MSK 2018 modified; 111\n" +
                        "math.html - 47424 B; Tue Jan 22 20:38:24 MSK 2019 modified; 111\n" +
                        "ImmaOnlyRead.txt - 0 B; Wed Apr 17 03:55:09 MSK 2019 modified; 101\n" +
                        "EmptyDirectory - 0 B; Mon Apr 15 14:16:42 MSK 2019 modified; 111\n" +
                        "Blank.docx - 29834 B; Sun Mar 24 15:46:19 MSK 2019 modified; 111\n" +
                        "adder1.qar - 16543 B; Sun Mar 24 14:21:24 MSK 2019 modified; 111\n" +
                        "1. В input лежит вся моя корзина, лучше ничего не открывать - 0 B; " +
                        "Thu Apr 18 00:48:29 MSK 2019 modified; 111")

        main(arrayOf("-l", "-h", "-o", "output\\out.txt",
                "input"))
        assertFileContent("output\\out.txt",
                "1. В input лежит вся моя корзина, лучше ничего не открывать - 0.0 B; " +
                        "Thu Apr 18 00:48:29 MSK 2019 modified; RWX\n" +
                        "adder1.qar - 16.1 Kb; Sun Mar 24 14:21:24 MSK 2019 modified; RWX\n" +
                        "Blank.docx - 29.1 Kb; Sun Mar 24 15:46:19 MSK 2019 modified; RWX\n" +
                        "EmptyDirectory - 0.0 B; Mon Apr 15 14:16:42 MSK 2019 modified; RWX\n" +
                        "ImmaOnlyRead.txt - 0.0 B; Wed Apr 17 03:55:09 MSK 2019 modified; R-X\n" +
                        "math.html - 46.3 Kb; Tue Jan 22 20:38:24 MSK 2019 modified; RWX\n" +
                        "tmb_25708_6017.jpg - 141.5 Kb; Sun Dec 23 19:57:06 MSK 2018 modified; RWX\n" +
                        "VoicemodSetup.exe - 17.3 Mb; Sat Mar 23 19:16:10 MSK 2019 modified; RWX\n" +
                        "ФЫЗЫКАЛ ЭДУКАТИОН.txt - 581.1 Kb; Sat Mar 09 15:39:29 MSK 2019 modified; RWX")
    }
}