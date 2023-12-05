package lists;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;
import lists.DynamicArray;
import lists.DynamicArrayable;

import java.util.Objects;

public class DynamicArrayTest {

    private static final TestObject test1 = new TestObject("Joe");
    private static final TestObject test2 = new TestObject("Steve");
    private static final TestObject test3 = new TestObject("Mary");
    private static final TestObject test4 = new TestObject("Jane");
    private static final TestObject test5 = new TestObject("Jack");

    @Ignore
    static class TestObject implements DynamicArrayable<TestObject> {

        String name;

        TestObject() {}

        TestObject(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof TestObject)
                return o.toString().equals(this.toString());

            return false;
        }

        @Override
        public String toString() {
            return "TestObject['" + this.name + "']";
        }

        @Override
        public TestObject[] newArray(int length) {
            return new TestObject[length];
        }
    }

    @Ignore
    private static void appendItems(DynamicArray<TestObject> list, int times) {
        for (int i = 0; i < times; i++) {
            list.append(new TestObject());
        }
    }

    @Ignore
    private static DynamicArray<TestObject> newList(int length) {
        return new DynamicArray<>(new TestObject(), length);
    }

    @Ignore
    private static DynamicArray<TestObject> newList() {
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
        DynamicArray<TestObject> test = newList();

        assertEquals(test.size(), 0);
    }

    @Test
    public void testListSizeReturnsValidIntegerOnNotEmpty() {
        DynamicArray<TestObject> test = newList();

        appendItems(test, 3);
        assertEquals(test.size(), 3);
    }

    @Test
    public void testIsEmptyOnEmptyList() {
        DynamicArray<TestObject> test = newList();

        assertTrue(test.isEmpty());
    }

    @Test
    public void testIsEmptyOnNotEmpty() {
        DynamicArray<TestObject> test = newList();

        appendItems(test, 3);
        assertFalse(test.isEmpty());
    }

    @Test
    public void testMethodsAfterClear() {
        DynamicArray<TestObject> test = newList();

        appendItems(test, 5);
        test.clear();
        assertTrue(test.isEmpty());
        assertEquals(test.size(), 0);
    }

    @Test
    public void testGetMethodOnEmpty() {
        DynamicArray<TestObject> test = newList();

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> test.get(0)
        );
    }

    @Test
    public void testGetMethodsOnNotEmpty() {
        DynamicArray<TestObject> test = newList();

        appendItems(test, 1);
        assertEquals(test.get(0), new TestObject());

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> test.get(69)
        );
    }

    @Test
    public void testToString() {
        DynamicArray<TestObject> test = newList();

        test.append(test4);
        test.append(test2);

        assertEquals(
                "[TestObject['Jane'], TestObject['Steve']]",
                test.toString()
        );
    }

    @Test
    public void testIterator() {
        DynamicArray<TestObject> test = newList();

        test.append(test1);
        test.append(test2);

        int count = 0;
        for (TestObject _t : test) {
            count++;
        }

        assertEquals(count, 2);
    }

    @Test
    public void testIndexOf() {
        DynamicArray<TestObject> test = newList();

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
        DynamicArray<TestObject> test = newList();

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
        DynamicArray<TestObject> test = newList();

        test.append(test1);
        test.append(test2);
        test.append(test3);
        test.append(test1);
        test.removeAllOccurrences(test1);

        assertEquals(test.size(), 2);
    }

    @Test
    public void testRemove() {
        DynamicArray<TestObject> test = newList();

        test.append(test1);
        test.remove(0);

        assertEquals(0, test.size());
    }

    @Test
    public void testPop() {
        DynamicArray<TestObject> test = newList();

        test.append(test5);
        test.pop();

        assertEquals(0, test.size());
    }

    @Test
    public void testInsert() {
        DynamicArray<TestObject> test = newList();

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
