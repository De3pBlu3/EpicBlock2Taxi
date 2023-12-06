package lists;

// To implement enhanced for-loop capability
import java.util.Iterator;

/**
 * DynamicArray:
 * Similar to java.util.ArrayList in the sense that the size
 * of this array can grow, i.e. there is no specific size limit,
 * assuming you do not go over the (theoretical) maximum Java
 * array size of 2,147,483,647.
 *
 * @author Darragh Luby
 * @param <T>   The type/class of the elements in the list.
 */

@SuppressWarnings({"unused", "UnusedReturnValue", "unchecked"})
public final class DynamicArray<T> implements Iterable<T> {

    private T[] array;
    private int count;

    // ======================== CONSTRUCTORS ========================

    /**
     * Single-param constructor. Useful if you know how many elements will be
     * added to the list. This does not affect the list's ability to grow
     * in size.
     *
     * @param length Length of the initial array (the space allocated
     *               for the array used in the implementation).
     */
    public DynamicArray(int length) {
        if (length < 1)
            throw new IllegalArgumentException("'length' argument cannot be less than 1");

        this.array = (T[]) new Object[length];  // Warning-inducing line (irrelevant)
    }

    /**
     * Single-param constructor.
     *
     * @param elements Array of elements (of type T) to initialise with.
     */
    public DynamicArray(T... elements) {
        this(elements.length);

        for (T element : elements) {
            this.append(element);
        }
    }

    /**
     * No-param constructor.
     */
    public DynamicArray() {
        this(1);
    }

    // ======================== PUBLIC METHODS ========================

    /**
     * Returns the number of elements in the list (the length).
     */
    public int size() {
        return this.count;
    }
    public int length() {
        return this.count;
    }

    /**
     * Returns whether the list is empty.
     */
    public boolean isEmpty() {
        return this.count == 0;
    }

    /**
     * Clears all elements in the list.
     */
    public void clear() {
        if (this.count != 0) {
            this.array = (T[]) new Object[1];  // Warning-inducing line (irrelevant)
            this.count = 0;
        }
    }

    /**
     * Returns the element at the given index.
     *
     * @param index Index/position.
     * @throws ArrayIndexOutOfBoundsException if index out of bounds.
     */
    public T get(int index) {
        if (this.count < 1)
            throw new ArrayIndexOutOfBoundsException("Cannot invoke get() on empty list");

        if (index >= this.count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds for length " + this.count);

        return this.array[index];
    }

    /**
     * Returns the first element of the list.
     *
     * @throws ArrayIndexOutOfBoundsException if empty list.
     */
    public T getFirst() {
        if (this.count < 1)
            throw new ArrayIndexOutOfBoundsException("Cannot invoke getFirst() on empty list");

        return this.array[0];
    }

    /**
     * Returns the last element of the array.
     *
     * @throws ArrayIndexOutOfBoundsException if empty list.
     */
    public T getLast() {
        if (this.count < 1)
            throw new ArrayIndexOutOfBoundsException("Cannot invoke getLast() on empty list");

        return this.array[count - 1];
    }

    /**
     * Appends the given element to the end of the list.
     *
     * @param element Element to be added.
     */
    public void append(T element) {
        if (this.count == array.length)
            this.doubleSize();

        this.array[this.count] = element;
        this.count++;
    }

    /**
     * Appends each element in the given array to the end of the list.
     *
     * @param elements Array of elements to add.
     * @see DynamicArray#append(T)
     */
    public void append(T... elements) {
        for (T element : elements) {
            this.append(element);
        }
    }

    /**
     * Adds the given element at the specified index.
     *
     * @param index Index to insert element at.
     * @param element Element to be inserted.
     * @throws ArrayIndexOutOfBoundsException if index out of bounds.
     */
    public void insert(int index, T element) {
        this.outOfBoundsCheck(index);

        if (this.count == this.array.length)
            this.doubleSize();

        // Shift elements to right starting at given index.
        // Allocates a space for the new insertion and moves
        // each element to their succeeding element's location.
        for (int i = this.count - 1; i >= index; i--) {
            this.array[i + 1] = this.array[i];
        }

        this.array[index] = element;
        this.count++;
    }

    /**
     * Removes the element at the specified index.
     *
     * @param index Index of element to remove.
     * @throws ArrayIndexOutOfBoundsException if index out of bounds.
     * @return Removed element.
     */
    public T remove(int index) {
        this.outOfBoundsCheck(index);

        T toRemove = this.array[index];

        // Shift elements to left starting at given index + 1.
        // Essentially overrides each element with their succeeding element.
        for (int i = index + 1; i < this.count; i++) {
            this.array[i - 1] = this.array[i];
        }

        this.count--;
        return toRemove;
    }

    /**
     * Removes the first occurrence of the given element
     * if found in the list.
     *
     * @param element Element to remove.
     * @return Removed element if found; else null.
     */
    public T removeFirstOccurrence(T element) {
        int index = this.indexOf(element);

        if (index == -1)
            return null;

        return this.remove(index);
    }

    /**
     * Removes the last occurrence of the given element
     * if found in the list.
     *
     * @param element Element to remove.
     * @return Removed element if found; else null.
     */
    public T removeLastOccurrence(T element) {
        int index = this.lastIndexOf(element);

        if (index == -1)
            return null;

        return this.remove(index);
    }

    /**
     * Removes all occurrences of the given element
     * if found in the list.
     *
     * @param element Element to remove.
     * @return Removed element if found; else null.
     */
    public T removeAllOccurrences(T element) {
        int index = this.indexOf(element);

        if (index == -1)
            return null;

        this.remove(index);

        while ((index = this.indexOf(element)) != -1) {
            this.remove(index);
        }

        return element;
    }

    /**
     * Removes the first element (index=0) of the list.
     *
     * @throws ArrayIndexOutOfBoundsException if empty list.
     * @return Removed element.
     */
    public T popLeft() {
        if (this.count < 1)
            throw new ArrayIndexOutOfBoundsException("Cannot invoke popLeft() on empty list");

        return this.remove(0);
    }

    /**
     * Removes the last element (index=size() - 1) of the list.
     *
     * @throws ArrayIndexOutOfBoundsException if list empty.
     * @return Removed element.
     */
    public T pop() {
        if (this.count < 1)
            throw new ArrayIndexOutOfBoundsException("Cannot invoke pop() on empty list");

        this.count--;
        T toRemove = array[this.count];
        this.array[this.count] = null;

        return toRemove;
    }

    /**
     * Returns the index of the first occurrence of the
     * given element.
     *
     * @param element Element to find.
     * @return Last index of element if in list; else -1.
     */
    public int indexOf(T element) {
        for (int i = 0; i < this.count; i++) {
            if (this.array[i].equals(element))
                return i;
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the
     * given element.
     *
     * @param element Element to find.
     * @return Last index of element if in list; else -1.
     */
    public int lastIndexOf(T element) {
        for (int i = this.count - 1; i >= 0; i--) {
            if (this.array[i].equals(element))
                return i;
        }
        return -1;
    }

    /**
     * Returns the current DynamicArray as a regular array.
     * THIS SOMETIMES WORKS?? (i.e. it works for the tests) BUT dijkstraNodes.toArray() DOES NOT WORK ?? what???
     * IF it works for thes tests, it works for me :)
     */
    public T[] toArray() {
        T[] newArray = (T[]) new Object[this.count];  // Warning-inducing line (irrelevant)
        // Copy elements to new array
        System.arraycopy(array, 0, newArray, 0, this.count);
        return newArray;}


    // ======================== OVERRIDDEN METHODS ========================

    /**
     * Returns an iterator for the list.
     * This enables the ability to use enhanced for loops & the forEach method.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private final int size = count;

            @Override
            public boolean hasNext() {
                return index < size && array[index] != null;
            }

            @Override
            public T next() {
                return array[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    /**
     * Returns a string representation of the list,
     * similar to how ArrayLists are printed:
     * [element1, element2, element3, ...]
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        int lastIndex = this.count - 1;
        for (int i = 0; i < this.count; i++) {
            builder.append(array[i]);

            if (i != lastIndex)
                builder.append(", ");

        }

        return builder.append("]").toString();
    }



    // ======================== PRIVATE METHODS ========================

    /**
     * Doubles the size of the allocated memory available for the
     * array that is used in the lists.DynamicArray implementation.
     */
    private void doubleSize() {
        T[] newArray = (T[]) new Object[this.count * 2];  // Warning-inducing line (irrelevant)

        // Copy elements to new array
        System.arraycopy(array, 0, newArray, 0, this.count);
        this.array = newArray;
    }

    /**
     * Checks whether the given index is out of bounds for the list.
     * i.e. it checks if the index is in the permitted range:
     * 0 <= index < size()
     *
     * @param index Index to check.
     * @throws ArrayIndexOutOfBoundsException if index out of bounds.
     */
    private void outOfBoundsCheck(int index) {
        if (index >= this.count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds for length " + this.size());
    }

}
