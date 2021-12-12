package cc.ut;


import com.cc.ut.Largest;
import junit.framework.TestCase;

public class LargestTest extends TestCase {

    public LargestTest(String name) {
        super(name);
    }

    //test numbers
    public void testNumss() {

        //general test
        assertEquals(9, Largest.largest(new int[]{7, 8, 9}));

        //order test
        assertEquals(9, Largest.largest(new int[]{7, 9, 8}));
        assertEquals(9, Largest.largest(new int[]{9, 8, 7}));

        //repetition test
        assertEquals(9, Largest.largest(new int[]{7, 9, 8, 9}));

        //single test
        assertEquals(9, Largest.largest(new int[]{9}));

        //negative test
        assertEquals(-7, Largest.largest(new int[]{-7, -8, -9}));
    }

    //test empty
    public void testisEmpty() {
        try {
            Largest.largest(new int[]{});
//            int i = 1 / 0;
            fail("An exception should been thrown!");
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}