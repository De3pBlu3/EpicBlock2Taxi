package lists;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;

public class DynamicArrayTest {

    private static final Integer test1 = 1;
    private static final Integer test2 = 2;
    private static final Integer test3 = 3;
    private static final Integer test4 = 4;
    private static final Integer test5 = 5;

    @Ignore
    private static void appendItems(DynamicArray<Integer> list, int times) {
        for (int i = 0; i < times; i++) {
            list.append(i);
        }
    }

    @Ignore
    private static DynamicArray<Integer> newList(int length) {
        return new DynamicArray<>(length);
    }

    @Ignore
    private static DynamicArray<Integer> newList() {
        return newList(1);
    }
    @Test
    public void testConstructorLengthLessThanOne() {
        assertThrows(
                IllegalArgumentException.class,
                () -> newList(-1)
        );
    }

    @Test
    public void testListSizeReturnsValidIntegerOnEmpty() {
        DynamicArray<Integer> test = newList();

        assertEquals(test.size(), 0);
    }

    @Test
    public void testListSizeReturnsValidIntegerOnNotEmpty() {
        DynamicArray<Integer> test = newList();

        appendItems(test, 3);
        assertEquals(test.size(), 3);
    }

    @Test
    public void testIsEmptyOnEmptyList() {
        DynamicArray<Integer> test = newList();

        assertTrue(test.isEmpty());
    }

    @Test
    public void testIsEmptyOnNotEmpty() {
        DynamicArray<Integer> test = newList();

        appendItems(test, 3);
        assertFalse(test.isEmpty());
    }

    @Test
    public void testMethodsAfterClear() {
        DynamicArray<Integer> test = newList();

        appendItems(test, 5);
        test.clear();
        assertTrue(test.isEmpty());
        assertEquals(test.size(), 0);
    }

    @Test
    public void testGetMethodOnEmpty() {
        DynamicArray<Integer> test = newList();

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> test.get(0)
        );
    }

    @Test
    public void testGetMethodsOnNotEmpty() {
        DynamicArray<Integer> test = newList();

        appendItems(test, 5);
        assertEquals(test.get(3), Integer.valueOf(3));

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> test.get(69)
        );
    }

    @Test
    public void testToString() {
        DynamicArray<Integer> test = newList();

        test.append(test4);
        test.append(test2);

        assertEquals(
                "[" + test4 + ", " + test2 + "]",
                test.toString()
        );
    }

    @Test
    public void testIterator() {
        DynamicArray<Integer> test = newList();

        test.append(test1);
        test.append(test2);

        int count = 0;
        for (Integer item : test) {
            count++;
        }

        assertEquals(count, 2);
    }

    @Test
    public void testIndexOf() {
        DynamicArray<Integer> test = newList();

        test.append(test1);
        test.append(test2);
        test.append(test3);
        test.append(test1);

        assertEquals(
                0,
                test.indexOf(test1)
        );
    }

    @Test
    public void testLastIndexOf() {
        DynamicArray<Integer> test = newList();

        test.append(test1);
        test.append(test2);
        test.append(test3);
        test.append(test1);

        assertEquals(
                3,
                test.lastIndexOf(test1)
        );
    }

    @Test
    public void testRemoveAllOccurrences() {
        DynamicArray<Integer> test = newList();

        test.append(test1);
        test.append(test2);
        test.append(test3);
        test.append(test1);
        test.removeAllOccurrences(test1);

        assertEquals(test.size(), 2);
    }

    @Test
    public void testRemove() {
        DynamicArray<Integer> test = newList();

        test.append(test1);
        test.remove(0);

        assertEquals(0, test.size());
    }

    @Test
    public void testPop() {
        DynamicArray<Integer> test = newList();

        test.append(test5);
        test.pop();

        assertEquals(0, test.size());
    }

    @Test
    public void testInsert() {
        DynamicArray<Integer> test = newList();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> test.insert(69, test1)
        );

        test.append(test1);
        test.append(test2);
        test.insert(1, test3);

        assertEquals(
                test3,
                test.get(1)
        );
    }
}
