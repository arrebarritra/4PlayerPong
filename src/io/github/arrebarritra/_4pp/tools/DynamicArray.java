package io.github.arrebarritra._4pp.tools;

import java.util.Arrays;

/**
 * Dynamic array of object
 *
 * @param <Type> Array object type
 */
public class DynamicArray<Type> {

    private Type[] array;

    public DynamicArray() {
        array = (Type[]) new Object[0];
    }

    public DynamicArray(int size) {
        array = (Type[]) new Object[size];
    }

    /**
     * Appends object to array
     *
     * @param object Object to append
     */
    public void add(Type object) {
        this.add(object, array.length);
    }

    /**
     * Adds object to specific position in array and shifts the rest of the
     * array
     *
     * @param object Object to be added
     * @param position Position in array
     * @throws ArrayIndexOutOfBoundsException if position is invalid
     */
    public void add(Type object, int position) throws ArrayIndexOutOfBoundsException {
        Type[] tempArray = (Type[]) new Object[array.length + 1];
        tempArray[position] = object;

        System.arraycopy(array, 0, tempArray, 0, position);
        System.arraycopy(array, position, tempArray, position + 1, array.length - position);

        array = tempArray;
    }

    /**
     * Removes object from array and shifts the rest of the array
     *
     * @param position Position of object being removed
     * @throws ArrayIndexOutOfBoundsException if position doesn't exist
     */
    public void remove(int position) throws ArrayIndexOutOfBoundsException {
        Type[] tempArray = (Type[]) new Object[array.length - 1];

        System.arraycopy(array, 0, tempArray, 0, position);
        System.arraycopy(array, position + 1, tempArray, position, array.length - (position + 1));

        array = tempArray;
    }

    public void remove(Type object) throws ArrayIndexOutOfBoundsException {
        for (int i = 0; i < this.size(); i++) {
            Type tempObject = array[i];

            if (tempObject.equals(object)) {
                this.remove(i);
            }
        }
    }

    /**
     * Replaces object at specific position in the array
     *
     * @param object Object being inserted
     * @param position Position of object being replaced
     * @throws ArrayIndexOutOfBoundsException if position doesn't exist
     */
    public void set(Type object, int position) throws ArrayIndexOutOfBoundsException {
        array[position] = object;
    }

    /**
     * Returns object at specified position in the array
     *
     * @param position Position of object
     * @return Object
     * @throws ArrayIndexOutOfBoundsException if position doesn't exist
     */
    public Type get(int position) throws ArrayIndexOutOfBoundsException {
        return array[position];
    }

    /**
     * Returns size of the array
     *
     * @return Array size
     */
    public int size() {
        return array.length;
    }

    /**
     * Converts DynamicList to array
     *
     * @return DynamicList as array
     */
    public Object[] toArray() {
        return Arrays.copyOf(array, array.length, array.getClass());
    }
}
