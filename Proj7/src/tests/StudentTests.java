package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import systemImp.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentTests {

    // TEST 1: Empty tree behavior
    @Test
    public void test01_EmptyTree() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertEquals("Empty Tree", bst.toString());
        assertEquals("", bst.inOrder());
        assertEquals("", bst.inorderNonRecursive());
        assertEquals("", bst.preOrder());
        assertEquals("", bst.preorderNonRecursive());
        assertEquals("", bst.postOrder());
        assertEquals("", bst.postorderNonRecursive());
        assertNull(bst.min());
        assertNull(bst.max());
        assertTrue(bst.isPerfect());
        assertTrue(bst.isComplete());
        assertTrue(bst.isFull());
        assertFalse(bst.remove(5, true));
    }

    // TEST 2: Single node tree
    @Test
    public void test02_SingleNode() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertTrue(bst.add(50));
        assertEquals("Level 0 50", bst.toString());
        assertEquals("50", bst.inOrder());
        assertEquals("50", bst.inorderNonRecursive());
        assertEquals("50", bst.preOrder());
        assertEquals("50", bst.preorderNonRecursive());
        assertEquals("50", bst.postOrder());
        assertEquals("50", bst.postorderNonRecursive());
        assertEquals(Integer.valueOf(50), bst.min());
        assertEquals(Integer.valueOf(50), bst.max());
        assertTrue(bst.isPerfect());
        assertTrue(bst.isComplete());
        assertTrue(bst.isFull());
    }

    // TEST 3: Duplicate add returns false
    @Test
    public void test03_DuplicateAdd() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertTrue(bst.add(10));
        assertFalse(bst.add(10));
        assertTrue(bst.add(5));
        assertFalse(bst.add(5));
        assertTrue(bst.add(15));
        assertFalse(bst.add(15));
        // Only 3 unique nodes, inOrder should show each once
        assertEquals("5 10 15", bst.inOrder());
    }

    // TEST 4: NullPointerException on null add
    @Test(expected = NullPointerException.class)
    public void test04_AddNullThrows() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(null);
    }

    // TEST 5: Min and Max on skewed trees
    @Test
    public void test05_MinMaxSkewed() {
        // Right-skewed (ascending inserts)
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(10);
        bst.add(20);
        bst.add(30);
        bst.add(40);
        assertEquals(Integer.valueOf(10), bst.min());
        assertEquals(Integer.valueOf(40), bst.max());

        // Left-skewed (descending inserts)
        BinarySearchTree<Integer> bst2 = new BinarySearchTree<>();
        bst2.add(40);
        bst2.add(30);
        bst2.add(20);
        bst2.add(10);
        assertEquals(Integer.valueOf(10), bst2.min());
        assertEquals(Integer.valueOf(40), bst2.max());
    }

    // TEST 6: Remove leaf, preferLeft and preferRight 
    @Test
    public void test06_RemoveLeaf() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(40); bst.add(20); bst.add(60);
        bst.add(10); bst.add(30); bst.add(50); bst.add(70);

        // Remove a leaf
        assertTrue(bst.remove(10, true));
        assertEquals("20 30 40 50 60 70", bst.inOrder());
        assertFalse(bst.remove(10, false)); // already gone

        // Remove internal node preferring predecessor (left)
        // 20's left subtree is now empty, predecessor doesn't exist that way,
        // but right subtree has 30, so successor = 30
        assertTrue(bst.remove(20, false)); // successor of 20 is 30
        assertEquals("30 40 50 60 70", bst.inOrder());
    }

 // TEST 7: Remove root
    @Test
    public void test07_RemoveRoot() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(40); bst.add(20); bst.add(60);

        // preferLeft=false → successor of 40 is 60
        assertTrue(bst.remove(40, false));
        assertEquals("20 60", bst.inOrder());
        assertEquals("60 20", bst.preOrder()); // 60 is new root, 20 is its left child

        BinarySearchTree<Integer> bst2 = new BinarySearchTree<>();
        bst2.add(40); bst2.add(20); bst2.add(60);

        // preferLeft=true → predecessor of 40 is 20
        assertTrue(bst2.remove(40, true));
        assertEquals("20 60", bst2.inOrder());
        assertEquals("20 60", bst2.preOrder()); // 20 is new root, 60 is its right child
    }

    // TEST 8: isPerfect / isComplete / isFull variations
    @Test
    public void test08_TreeShapeChecks() {
        // Full AND complete but not perfect
        //      40
        //    /    \
        //   20    60
        //  /  \
        // 10  30
        // isComplete: BFS order 40,20,60,10,30 — fills left to right → TRUE
        // isFull: every node has 0 or 2 children → TRUE
        // isPerfect: leaves 60,10,30 not all at same depth → FALSE
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(40); bst.add(20); bst.add(60);
        bst.add(10); bst.add(30);

        assertFalse(bst.isPerfect());
        assertTrue(bst.isComplete());
        assertTrue(bst.isFull());

        // Complete but not full and not perfect
        //      40
        //    /    \
        //   20    60
        //  /
        // 10
        // isComplete: BFS 40,20,60,10 — fills left to right → TRUE
        // isFull: 20 has only one child → FALSE
        // isPerfect: FALSE
        BinarySearchTree<Integer> bst2 = new BinarySearchTree<>();
        bst2.add(40); bst2.add(20); bst2.add(60); bst2.add(10);

        assertFalse(bst2.isPerfect());
        assertTrue(bst2.isComplete());
        assertFalse(bst2.isFull());

        // Full but NOT complete and NOT perfect
        //        40
        //       /  \
        //      20   60
        //     /  \
        //    10  30
        //   /  \
        //  5   15
        // isComplete: FALSE (right side of level 2 is empty while level 3 has nodes)
        // isFull: every node 0 or 2 children → TRUE
        // isPerfect: FALSE
        BinarySearchTree<Integer> bst3 = new BinarySearchTree<>();
        bst3.add(40); bst3.add(20); bst3.add(60);
        bst3.add(10); bst3.add(30);
        bst3.add(5);  bst3.add(15);

        assertFalse(bst3.isPerfect());
        assertFalse(bst3.isComplete());
        assertTrue(bst3.isFull());
    }

    // TEST 9: All traversals on a known tree 
    @Test
    public void test09_AllTraversals() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(50); bst.add(25); bst.add(75);
        bst.add(12); bst.add(37); bst.add(62); bst.add(87);

        String expectedIn   = "12 25 37 50 62 75 87";
        String expectedPre  = "50 25 12 37 75 62 87";
        String expectedPost = "12 37 25 62 87 75 50";

        assertEquals(expectedIn,   bst.inOrder());
        assertEquals(expectedIn,   bst.inorderNonRecursive());
        assertEquals(expectedPre,  bst.preOrder());
        assertEquals(expectedPre,  bst.preorderNonRecursive());
        assertEquals(expectedPost, bst.postOrder());
        assertEquals(expectedPost, bst.postorderNonRecursive());
    }

    // TEST 10: toString level-order with null placeholders 
    @Test
    public void test10_ToStringNullPlaceholders() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(40); bst.add(20); bst.add(60); bst.add(10);
        // Shape:
        //      40
        //    /    \
        //   20    60
        //  /
        // 10
        String expected =
            "Level 0 40\n" +
            "Level 1 20 60\n" +
            "Level 2 10 null null null";
        assertEquals(expected, bst.toString());

        // After removing 10, level 2 disappears entirely
        bst.remove(10, false);
        String expected2 =
            "Level 0 40\n" +
            "Level 1 20 60";
        assertEquals(expected2, bst.toString());
    }
}