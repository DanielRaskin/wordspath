package words;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WordTest {
    private static WordPathFinder finder;

    @BeforeAll
    public static void beforeAll() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/russian_nouns.txt")) {
            finder = new WordPathFinder(new String(is.readAllBytes()));
        }
    }

    @Test
    public void testNullAndEmpty() {
        assertNull(finder.findPath(null, null));
        assertNull(finder.findPath(null, ""));
        assertNull(finder.findPath("", ""));
        assertNull(finder.findPath("слон", null));
        assertNull(finder.findPath("слон", ""));
    }

    @Test
    public void testIncorrectWord() {
        assertNull(finder.findPath("слон", "123f"));
    }

    @Test
    public void testDifferentLengths() {
        assertNull(finder.findPath("слон", "склон"));
    }

    @Test
    public void testPathFound() {
        assertEquals(2, finder.findPath("сон", "сын").size());
        assertEquals(4, finder.findPath("папа", "мама").size());
        assertEquals(5, finder.findPath("конь", "лань").size());
        assertEquals(5, finder.findPath("поток", "ворон").size());
        assertEquals(11, finder.findPath("тесто", "булка").size());
    }

    @Test
    public void testPathNotFound() {
        assertNull(finder.findPath("колба", "батон"));
        assertNull(finder.findPath("кофта", "спорт"));
        assertNull(finder.findPath("полено", "стрела"));
    }
}
