package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import systemImp.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PublicTests {
	
	
	@Test
	public void testPutContainsKeyToString() {
		StringBuilder output = new StringBuilder();

	    BinarySearchTree<Integer> bst = new BinarySearchTree<>();
	    bst.add(40);
	    bst.add(20);
	    bst.add(60);
	    bst.add(10);
	    bst.add(30);
	    bst.add(50);
	    bst.add(70);

	    output.append("In-order:    ").append(bst.inOrder()).append("\n");
	    output.append("In-order:    ").append(bst.inorderNonRecursive()).append("\n");
	    output.append("Pre-order:   ").append(bst.preOrder()).append("\n");
	    output.append("Pre-order:   ").append(bst.preorderNonRecursive()).append("\n");
	    output.append("Post-order:  ").append(bst.postOrder()).append("\n");
	    output.append("Post-order:  ").append(bst.postorderNonRecursive()).append("\n");
	    output.append("-----------------\n");
	    output.append(bst.toString()).append("\n");
	    output.append("Perfect:  ").append(bst.isPerfect()).append("\n");
	    output.append("Complete:  ").append(bst.isComplete()).append("\n");
	    output.append("Full:  ").append(bst.isFull()).append("\n");
	    output.append("-----------------\n");

	    bst.remove(20, false);
	    output.append(bst.toString()).append("\n");
	    output.append("Perfect:  ").append(bst.isPerfect()).append("\n");
	    output.append("Complete:  ").append(bst.isComplete()).append("\n");
	    output.append("Full:  ").append(bst.isFull()).append("\n");
	    output.append("-----------------\n");

	    bst.add(35);
	    bst.add(5);
	    output.append(bst.toString()).append("\n");
	    output.append("Perfect:  ").append(bst.isPerfect()).append("\n");
	    output.append("Complete:  ").append(bst.isComplete()).append("\n");
	    output.append("Full:  ").append(bst.isFull()).append("\n");
	    output.append("-----------------\n");

	    bst.remove(5, false);
	    bst.add(34);
	    bst.add(36);
	    output.append(bst.toString()).append("\n");
	    output.append("Perfect:  ").append(bst.isPerfect()).append("\n");
	    output.append("Complete:  ").append(bst.isComplete()).append("\n");
	    output.append("Full:  ").append(bst.isFull()).append("\n");
	
	    TestsSupport.assertCorrect("pubTest01.txt", output.toString());

	
	}
 
}

	
