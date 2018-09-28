/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.util;

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
    private int remCount;

    public FlockList() {
        this(10);
    }

    public FlockList(int initCapacity) {
        super();
        if (initCapacity < 0) {
            throw new IllegalArgumentException("Illegal size: " + initCapacity);
        }
        this.remCount = 0;
        this.elements = (T[]) new Object[initCapacity];
    }

    /**
     * Appends specified element to end of list
     *
     * @param element
     * @return
     */
    public boolean add(T element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
        return true;
    }

    /**
     * Gets specified
     *
     * @param index
     * @return
     */
    public T get(int index) {
        checkIndex(index);
        T element = elements[index];
        return element;
    }

    /**
     *
     * @return amount of elements in FlockList
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

            if (newCapacity < minimum) {
                newCapacity = minimum;
            }

            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }

            elements = newElements;
        }
    }

    /**
     * Checks if specified index is outside the array
     * @param index
     * @throws IndexOutOfBoundsException
     */
    private void checkIndex(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Returns the index of specified element
     * @param element
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
     * @param index
     * @return
     */
    public T remove(int index) {
        checkIndex(index);
        T element = elements[index];

        // shrink array to compensate for removed element
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        remCount++;

        // shorten array when enough elements have been removed
//        if (size / 10 < remCount && size >= 100) {
//            T[] newElements = (T[]) new Object[size];
//            
//            for (int i = 0; i < size; i++) {
//                newElements[i] = elements[i];
//            }
//            
//            remCount = 0;
//        }
        
        return element;
    }

}
