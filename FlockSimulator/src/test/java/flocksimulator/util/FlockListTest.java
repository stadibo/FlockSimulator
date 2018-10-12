/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flocksimulator.util;

import flocksimulator.util.FlockList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class FlockListTest {

    public FlockListTest() {
    }

    @Test
    public void doesNotAllowNegativeCapacity() {
        boolean created = false;
        try {
            FlockList<Integer> list1 = new FlockList<>(-100);
            created = true;
            assertFalse(created);
        } catch (Exception e) {
            assertFalse(created);
        }
    }
    
    @Test
    public void canAddElementToList() {
        FlockList<Integer> list1 = new FlockList<>();
        list1.add(2);
        assertTrue("Added element is not at right index", list1.get(0) == 2);
    }
    
    @Test
    public void arrayGrowsWhenAddingMoreElements() {
        FlockList<Integer> list1 = new FlockList<>();
        for (int i = 0; i <= 16; i++) {
            list1.add(i);
        }
        assertTrue(list1.size() == 17);
        assertTrue(list1.get(16) == 16);
        assertTrue(list1.get(0) == 0);
    }
    
    @Test
    public void cannotGetElementsFromEmptyList() {
        FlockList<Integer> list1 = new FlockList<>(10);
        try {
            list1.get(0);
            assertTrue("Getting element from array should throw error", false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void cannotGetElementFromNegativeIndex() {
        FlockList<Integer> list1 = new FlockList<>(10);
        list1.add(100);
        try {
            list1.get(-1);
            assertTrue("Getting element outside array indexes should throw error", false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void canRemoveElementFromList() {
        FlockList<Integer> list1 = new FlockList<>(10);
        list1.add(100);
        list1.add(200);
        try {
            list1.remove(1);
            list1.get(1);
            assertTrue("Element was not removed from specified position", false);
        } catch (Exception e) {
            assertTrue(true);
            
        }
    }
    
    @Test
    public void indexOfReturnsCorrectIndex() {
        FlockList<Integer> list1 = new FlockList<>(10);
        list1.add(100);
        list1.add(200);
        assertTrue("Index given for element is not correct index", list1.indexOf(200) == 1);
    }
    
    @Test
    public void listCompensatesForRemovedElementByMovingElementsLeft() {
        FlockList<Integer> list1 = new FlockList<>(10);
        list1.add(100);
        list1.add(200);
        list1.add(300);
        list1.remove(1);
        assertTrue("Element was removed", list1.get(1) == 300);
    }
}
