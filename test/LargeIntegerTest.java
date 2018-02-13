import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LargeIntegerTest {
    @Test
    void equals() {
        assertTrue(new LargeInteger("").equals(new LargeInteger("")));
        assertTrue(new LargeInteger("12").equals(new LargeInteger("12")));  //-
        assertFalse(new LargeInteger("103423").equals(new LargeInteger("103422")));  //-
    }

    @Test
    void bigger() {
        assertFalse(new LargeInteger("12321").bigger(new LargeInteger("123123")));
        assertTrue(new LargeInteger("12").bigger(new LargeInteger("10")));
        assertTrue(new LargeInteger("103423").bigger(new LargeInteger("103422")));
    }

    @Test
    void addition() {
        assertEquals(new LargeInteger("6"), new LargeInteger("2").addition(new LargeInteger("4")));
        assertEquals(new LargeInteger("10"), new LargeInteger("5").addition(new LargeInteger("5")));
    }

    @Test
    void subtraction() {
        assertEquals(new LargeInteger("6"), new LargeInteger("8").subtraction(new LargeInteger("2")));
        assertEquals(new LargeInteger("-1"), new LargeInteger("5").subtraction(new LargeInteger("6")));
    }

}