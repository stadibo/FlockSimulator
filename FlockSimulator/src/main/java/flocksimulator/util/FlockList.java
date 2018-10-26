package flocksimulator.util;

import java.util.RandomAccess;

/**
 * ArrayList like list implementation for FlockSimulator
 *
 * @author peje
 * @param <T> type of object to store in list
 */
public class FlockList<T> implements RandomAccess {

    private T[] elements;
    private int size;

    public FlockList() {
        this(10);
    }

    public FlockList(int initCapacity) {
        super();
        if (initCapacity < 0) {
            throw new IllegalArgumentException("Illegal size: " + initCapacity);
        }
        this.elements = (T[]) new Object[initCapacity];
    }

    /**
     * Appends specified element to end of list
     *
     * @param element to add to list
     * @return true
     */
    public boolean add(T element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
        return true;
    }

    /**
     * Gets specified
     *
     * @param index from which to get element
     * @return element
     */
    public T get(int index) {
        checkIndex(index);
        T element = elements[index];
        return element;
    }

    /**
     * amount of elements in FlockList
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Checks if array containing elements has space to add another element, if
     * not, grows the array by about 1.5 to accommodate more elements
     *
     * @param minimum required amount of space needed in FlockList
     */
    private void ensureCapacity(int minimum) {
        int oldCapacity = elements.length;
        if (oldCapacity < minimum) {
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            T[] newElements = (T[]) new Object[newCapacity];

            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }

            elements = newElements;
        }
    }

    /**
     * Checks if specified index is outside the array
     * @param index index to check
     * @throws IndexOutOfBoundsException
     */
    private void checkIndex(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Returns the index of specified element
     * @param element for which to get index in list
     * @return index of element if it exists in array or -1
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (element == elements[i] || elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes specified element from array
     * @param index from which index to remove
     * @return element that was removed
     */
    public T remove(int index) {
        checkIndex(index);
        T element = elements[index];

        // shrink array to compensate for removed element
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        
        return element;
    }
    
    /**
     * Empties the list of elements
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

}
