package TS_ACO_TSP;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

//TODO documentation
public class DLLTest {

    DLL dllList;

    @Before
    public void setup(){
        dllList = new DLL();
    }

    @Test
    public void testSize(){
        Assert.assertEquals(0, dllList.size());
        dllList.insertFirst(1);
        Assert.assertEquals(1, dllList.size());
        dllList.insertFirst(2);
        dllList.insertFirst(3);
        dllList.insertFirst(4);
        Assert.assertEquals(4, dllList.size());
        dllList.removeFirst();
        Assert.assertEquals(3, dllList.size());
        dllList.removeFirst();
        dllList.removeFirst();
        dllList.removeFirst();
        Assert.assertEquals(0, dllList.size());
    }

    @Test
    public void testIsEmpty(){
        Assert.assertTrue(dllList.isEmpty());
        dllList.insertFirst(1);
        Assert.assertFalse(dllList.isEmpty());
        dllList.insertFirst(2);
        Assert.assertFalse(dllList.isEmpty());
        dllList.removeFirst();
        Assert.assertFalse(dllList.isEmpty());
        dllList.removeFirst();
        Assert.assertTrue(dllList.isEmpty());
    }

    @Test
    public void makeDeepCopyOfTest(){
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        dllList.insertFirst(5);
        dllList.insertFirst(10);
        dllList.insertFirst(8);
        dllList.insertFirst(3);
        dllList.insertFirst(9);
        dllList.insertFirst(1);
        dllList.insertFirst(4);
        dllList.insertFirst(7);
        dllList.insertFirst(6);
        dllList.insertFirst(2);
        //dllList = [2,6,7,4,1,9,3,8,10,5]
        DLL newDll = new DLL();
        newDll.makeDeepCopyOf(dllList);
        Assert.assertEquals(dllList.getElements(), newDll.getElements());
    }

    @Test
    public void testInsertFirst(){
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        dllList.insertFirst(1);
        Assert.assertEquals(1, dllList.getHead().getElement());
        Assert.assertEquals(1, dllList.getTail().getElement());
        dllList.insertFirst(2);
        Assert.assertEquals(2, dllList.getHead().getElement());
        dllList.removeFirst();
        Assert.assertEquals(1, dllList.getHead().getElement());
        Assert.assertEquals(1, dllList.getTail().getElement());
        dllList.removeFirst();
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
    }

    @Test
    public void testInsertLast(){
        Assert.assertNull(dllList.getTail());
        Assert.assertNull(dllList.getHead());
        dllList.insertLast(1);
        Assert.assertEquals(1, dllList.getTail().getElement());
        Assert.assertEquals(1, dllList.getHead().getElement());
        dllList.insertLast(2);
        Assert.assertEquals(2, dllList.getTail().getElement());
        dllList.insertLast(3);
        Assert.assertEquals(3, dllList.getTail().getElement());
        dllList.removeLast();
        Assert.assertEquals(2, dllList.getTail().getElement());
        dllList.removeLast();
        Assert.assertEquals(1, dllList.getTail().getElement());
        Assert.assertEquals(1, dllList.getHead().getElement());
        dllList.removeLast();
        Assert.assertNull(dllList.getTail());
        Assert.assertNull(dllList.getHead());
    }

    @Test
    public void testRemoveFirst(){
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        dllList.insertFirst(2);
        dllList.insertFirst(1);
        dllList.insertLast(3);
        Assert.assertEquals(1, dllList.getHead().getElement());
        Assert.assertEquals(3, dllList.getTail().getElement());
        dllList.removeFirst();
        Assert.assertEquals(2, dllList.getHead().getElement());
        Assert.assertEquals(3, dllList.getTail().getElement());
        dllList.removeFirst();
        Assert.assertEquals(3, dllList.getHead().getElement());
        Assert.assertEquals(3, dllList.getTail().getElement());
        dllList.removeFirst();
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstException(){
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        dllList.removeFirst();
    }

    @Test
    public void getIndexOfTest(){
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        dllList.insertFirst(2);
        dllList.insertFirst(1);
        dllList.insertLast(3);
        Assert.assertEquals(0, dllList.getIndexOf(1));
        Assert.assertEquals(1, dllList.getIndexOf(2));
        Assert.assertEquals(2, dllList.getIndexOf(3));
        dllList.removeLast();
        Assert.assertEquals(0, dllList.getIndexOf(1));
        Assert.assertEquals(1, dllList.getIndexOf(2));
        dllList.removeFirst();
        Assert.assertEquals(0, dllList.getIndexOf(2));
    }

    @Test
    public void testRemoveLast(){
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        dllList.insertFirst(2);
        dllList.insertFirst(1);
        dllList.insertLast(3);
        Assert.assertEquals(1, dllList.getHead().getElement());
        Assert.assertEquals(3, dllList.getTail().getElement());
        dllList.removeLast();
        Assert.assertEquals(1, dllList.getHead().getElement());
        Assert.assertEquals(2, dllList.getTail().getElement());
        dllList.removeLast();
        Assert.assertEquals(1, dllList.getHead().getElement());
        Assert.assertEquals(1, dllList.getTail().getElement());
        dllList.removeLast();
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastException(){
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        dllList.removeLast();
    }

    @Test
    public void testSearch(){
        Assert.assertNull(dllList.search(1));
        dllList.insertLast(1);
        dllList.insertLast(2);
        dllList.insertLast(3);
        dllList.insertLast(4);
        dllList.insertLast(5);
        Assert.assertNotNull(dllList.search(1));
        DLL.Node node2 = dllList.search(2);
        Assert.assertEquals(2, node2.getElement());
        Assert.assertNotNull(dllList.search(3));
        Assert.assertNotNull(dllList.search(4));
        Assert.assertNotNull(dllList.search(5));
        Assert.assertTrue(dllList.isEmpty());
    }

    @Test
    public void testSearchWithoutRemove(){
        Assert.assertNull(dllList.searchWithoutRemove(1));
        dllList.insertLast(1);
        dllList.insertLast(2);
        dllList.insertLast(3);
        dllList.insertLast(4);
        dllList.insertLast(5);
        Assert.assertNotNull(dllList.searchWithoutRemove(1));
        Assert.assertNotNull(dllList.searchWithoutRemove(2));
        Assert.assertNotNull(dllList.searchWithoutRemove(3));
        Assert.assertNotNull(dllList.searchWithoutRemove(4));
        Assert.assertNotNull(dllList.searchWithoutRemove(5));
        Assert.assertEquals(1, dllList.searchWithoutRemove(1).getElement());
        Assert.assertEquals(2, dllList.searchWithoutRemove(2).getElement());
        Assert.assertEquals(3, dllList.searchWithoutRemove(3).getElement());
        Assert.assertEquals(4, dllList.searchWithoutRemove(4).getElement());
        Assert.assertEquals(5, dllList.searchWithoutRemove(5).getElement());
        Assert.assertNull(dllList.searchWithoutRemove(0));
        Assert.assertNull(dllList.searchWithoutRemove(6));
        dllList.removeFirst();
        dllList.removeFirst();
        dllList.removeFirst();
        dllList.removeFirst();
        dllList.removeFirst();
        Assert.assertNull(dllList.searchWithoutRemove(1));
        Assert.assertNull(dllList.searchWithoutRemove(2));
        Assert.assertNull(dllList.searchWithoutRemove(3));
        Assert.assertNull(dllList.searchWithoutRemove(4));
        Assert.assertNull(dllList.searchWithoutRemove(5));
    }

    @Test
    public void testGetElements(){
        Assert.assertTrue(dllList.getElements().isEmpty());
        dllList.insertFirst(1);
        dllList.insertLast(2);
        dllList.insertLast(3);
        for (int i=1; i<=3; i++){
            Assert.assertEquals(i, (int) dllList.getElements().get(i-1));
        }
        Assert.assertEquals(3, dllList.getElements().size());
        dllList.removeLast();
        for (int i=1; i<=2; i++){
            Assert.assertEquals(i, (int) dllList.getElements().get(i-1));
        }
        Assert.assertEquals(2, dllList.getElements().size());
        dllList.removeLast();
        for (int i=1; i<=1; i++){
            Assert.assertEquals(i, (int) dllList.getElements().get(0));
        }
        Assert.assertEquals(1, dllList.getElements().size());
        dllList.removeLast();
        Assert.assertTrue(dllList.getElements().isEmpty());
    }

    @Test
    public void testGetNodeAtIndex(){
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        dllList.insertFirst(2);
        Assert.assertEquals(2, dllList.getNodeAtIndex(0).getElement());
        dllList.insertFirst(1);
        dllList.insertLast(3);
        dllList.insertLast(4);
        for (int i=1; i<= 4; i++){
            Assert.assertEquals(i, dllList.getNodeAtIndex(i-1).getElement());
        }
        dllList.removeFirst();
        for (int i=1; i<= 3; i++){
            Assert.assertEquals(i+1, dllList.getNodeAtIndex(i-1).getElement());
        }
        dllList.removeFirst();
        dllList.removeFirst();
        Assert.assertEquals(4, dllList.getNodeAtIndex(0).getElement());
        dllList.removeFirst();
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetNodeAtIndexException1(){
        dllList.getNodeAtIndex(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetNodeAtIndexException2(){
        dllList.insertLast(1);
        dllList.insertLast(2);
        dllList.insertLast(3);
        dllList.getNodeAtIndex(3);
    }

    @Test
    public void testRemoveAll(){
        dllList.removeAll();
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        Assert.assertEquals(0, dllList.getSize());
        dllList.insertLast(1);
        dllList.insertLast(2);
        dllList.insertLast(3);
        Assert.assertNotNull(dllList.getHead());
        Assert.assertNotNull(dllList.getTail());
        Assert.assertNotEquals(0, dllList.getSize());
        dllList.removeAll();
        Assert.assertNull(dllList.getHead());
        Assert.assertNull(dllList.getTail());
        Assert.assertEquals(0, dllList.getSize());
    }
}
