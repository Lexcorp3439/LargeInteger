import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LargeIntegerTest {
    @Test
    void equals() {
        assertTrue(new LargeInteger("").equals(new LargeInteger("")));
        assertTrue(new LargeInteger("12").equals(new LargeInteger("12")));  //-
        assertFalse(new LargeInteger("103423").equals(new LargeInteger("103422")));
        assertFalse(new LargeInteger("103423243243").equals(new LargeInteger("14543523103422")));
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
        assertEquals(new LargeInteger("100"), new LargeInteger("50").addition(new LargeInteger("50")));
        assertEquals(new LargeInteger("148686868686868000"), new LargeInteger("92121212121212000").addition(new LargeInteger("56565656565656000"))); // 17 чисел , 13мс
        assertEquals(new LargeInteger("6037"), new LargeInteger("5424").addition(new LargeInteger("613")));
    }

    @Test
    void subtraction() {
        assertEquals(new LargeInteger("6"), new LargeInteger("8").subtraction(new LargeInteger("2")));
        assertEquals(new LargeInteger("-1"), new LargeInteger("5").subtraction(new LargeInteger("6")));
        assertEquals(new LargeInteger("613"), new LargeInteger("6037").subtraction(new LargeInteger("5424")));
        assertEquals(new LargeInteger("-613"), new LargeInteger("5424").subtraction(new LargeInteger("6037")));
        assertEquals(new LargeInteger("5424000000000000000"), new LargeInteger("6037000000000000000").subtraction(new LargeInteger("613000000000000000")));  //19 чисел , 14мс
        assertEquals(new LargeInteger("-5424"), new LargeInteger("613").subtraction(new LargeInteger("6037")));
    }

}