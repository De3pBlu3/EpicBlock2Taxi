package lists;

public interface DynamicArrayable<T> {
    /**
     * To have the ability to be used in a DynamicArray,
     * a class must implement the following method, which
     * should return an array of type {that class}, of length {@code length}.
     * <p>
     * This can be done as follows:
     * {@code return new MyClass[length]}
     *
     * @param length Length of the array.
     * @return Newly initialised array.
     */
    T[] newArray(int length);
}
