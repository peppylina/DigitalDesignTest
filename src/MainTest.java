import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void takeStringFromBrackets() {

        assertEquals("xyxyxy", Main.takeStringFromBrackets("3[xy]"));
        assertEquals("xxyxyxy", Main.takeStringFromBrackets("x3[xy]"));
        assertEquals("xxyxyxyz", Main.takeStringFromBrackets("x3[xy]z"));
        assertEquals("xtttxytttxyp", Main.takeStringFromBrackets("x2[3[t]xy]p"));
        assertEquals("xyyzzxyyzzxyyzz", Main.takeStringFromBrackets("3[x2[y]2[z]]"));
        assertEquals("qwerttswerttsy", Main.takeStringFromBrackets("q2[w1[e]r2[t]s]y"));
        assertEquals("aaa", Main.takeStringFromBrackets("aaa"));

    }

    @Test
    void badCredentials() {

        //скобочная последовательность
        assertThrows(WrongBracketsOrderException.class, () -> Main.isValid("2[3[e]"));
        assertTrue(Main.isValid("s3[r4[g]e4[d]dfdf]f"));
        assertThrows(WrongBracketsOrderException.class, () -> Main.isValid("sd3[f]]3[f][f"));

        //символы между числом и скобкой
        assertThrows(SymbolBetwenNumberAndBracketException.class, () -> Main.isValid("3[e2d[f]]"));
        assertTrue(Main.isValid("a2[]"));

        //когда скобки без числа, или числа без скобок
        assertThrows(BadCredentialsException.class, () -> Main.isValid("3"));
        assertThrows(BadCredentialsException.class, () -> Main.isValid("2[r]sd[]"));
        assertThrows(BadCredentialsException.class, () -> Main.isValid("[]2[dfd[]]"));
    }
}