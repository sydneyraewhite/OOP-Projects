package systemImp;


import java.util.Stack;
import java.util.LinkedList;  //only use this if you assign to a Queue variable
import java.util.Queue;

/**
 * A simple generic Binary Search Tree (BST) implementation
 * that supports basic insertion, traversal operations, etc.
 * This version models a Set: nodes store keys only.
 *
 * @param <K> the key type, which must be Comparable
 */
public class BinarySearchTree<K extends Comparable<K>> {
    
    /**
     * Inner class representing a node in the BST.
     */
    private class Node {
        private K key;
        private Node left, right;

        private Node(K key) {
            this.key = key;
        }
    }

    private Node root;
    
    public BinarySearchTree() {}

    /*
     * uses a private size() helper 
     * to determine whether insertion actually 
     * happened (duplicate vs. new)
     * An alternative would be a boolean wrapper array, 
     * but this keeps it clean
     */
    public boolean add(K key) {
    	if(key == null) {
    		throw new NullPointerException("Key must not be null.");
    	}
    	
    	int before = size(root);
    	root = addHelper(root, key);
    	return size(root) > before;
    }
    
    private Node addHelper(Node node, K key) {
    	if (node == null) {
    		return new Node(key);
    	}
    	int cmp = key.compareTo(node.key);
    	if (cmp < 0) {
    		node.left = addHelper(node.left, key);
    	} else if (cmp > 0){
    		node.right = addHelper(node.right, key);
    	}
    	return node;
    }
    
    private int size(Node node) {
    	if (node == null) return 0;
    	return 1 + size(node.left) + size(node.right);
    }
    
    /*
     * The key insight is using a secondary nextQueue 
     * to peek at the next level before committing to it 
     * This is what lets the method stop once an entire 
     * level is all nulls, rather than expanding infinitely
     */
    public String toString() {
    	if (root == null) return "Empty Tree";
    	
    	StringBuilder sb = new StringBuilder();
    	Queue<Node> queue = new LinkedList<>();
    	queue.add(root);
    	int level = 0;
    	
    	while(!queue.isEmpty()) {
    		int levelSize = queue.size();
    		StringBuilder line = new StringBuilder();
    		
    		// We need to detect whether the next level has any real nodes
            // so we use a secondary queue for the next level's nodes
    		Queue<Node> nextQueue = new LinkedList<>();
    		
    		for (int i = 0; i < levelSize; i++) {
    			Node curr = queue.poll();
    			if(i > 0) line.append(" ");
    			
    			if(curr == null) {
    				line.append("null");
    				nextQueue.add(null);
    				nextQueue.add(null);
    			} else {
    				line.append(curr.key);
    				nextQueue.add(curr.left);
    				nextQueue.add(curr.right);
    			}
    		}
    		if (sb.length() > 0) sb.append("\n");
    		sb.append("Level ").append(level).append(" ").append(line);
    		level++;
    		
    		// Check if next level has any real nodes before enqueuing
    		boolean nextHasReal = false;
    		for (Node n : nextQueue) {
    			if (n != null) { nextHasReal = true; break; }
    		}
    		if (nextHasReal) {
    			queue.addAll(nextQueue);
    		}
    	}
    	return sb.toString();
    }
    
    /*
     * left → root → right, which visits BST keys in sorted ascending order
     * The recursive version just recurses left, appends the key, recurses right 
     * The non-recursive version simulates the call stack manually
     */
    public String inOrder() {
    	StringBuilder sb = new StringBuilder();
    	inOrderHelper(root, sb);
    	return sb.toString();
    }
    
    private void inOrderHelper(Node node, StringBuilder sb) {
    	if ( node == null) return;
    	inOrderHelper(node.left, sb);
    	if (sb.length() > 0) sb.append(" ");
    	sb.append(node.key);
    	inOrderHelper(node.right, sb);
    }
    
    public String inorderNonRecursive() {
    	StringBuilder sb = new StringBuilder();
    	Stack<Node> stack = new Stack<>();
    	Node curr = root;
    	
    	while (curr != null || !stack.isEmpty()) {
    		while (curr != null) {
        		stack.push(curr);
        		curr = curr.left;
        	}
    		curr = stack.pop();
    		if (sb.length() > 0) sb.append(" ");
    		sb.append(curr.key);
    		curr = curr.right;
    	}
    	return sb.toString();
    }
    
    /*
     *  root → left → right. Recursive version visits the node first, then recurses. 
     *  Non-recursive uses a stack — push root, then each iteration pops a node, 
     *  visits it, and pushes its right child then left child
     */
    public String preOrder() {
    	StringBuilder sb = new StringBuilder();
    	preOrderHelper(root, sb);
    	return sb.toString();
    }
    
    private void preOrderHelper(Node node, StringBuilder sb) {
    	if (node == null) return;
    	if (sb.length() > 0) sb.append(" "); 
    	sb.append(node.key);
    	preOrderHelper(node.left, sb);
    	preOrderHelper(node.right, sb);
    }
    
    public String preorderNonRecursive() {
    	StringBuilder sb = new StringBuilder();
    	if (root == null) return sb.toString();
    	
    	Stack<Node> stack = new Stack<>();
    	stack.push(root);
    	
    	while (!stack.isEmpty()) {
    		Node curr = stack.pop();
    		if (sb.length() > 0) sb.append(" ");
    		sb.append(curr.key);
    		
    		if (curr.right != null) stack.push(curr.right);
    		if (curr.left != null) stack.push(curr.left);
    	}
    	return sb.toString();
    }
    
    /*
     *  left → right → root. Recursive version recurses both subtrees before visiting.
     *  Non-recursive uses the two-stack trick: stack1 does a modified pre-order (root → right → left) 
     *  pushing each node onto stack2. When stack1 is empty, stack2 holds everything 
     *  in reverse, which is exactly left → right → root. 
     */
    public String postOrder() {
    	StringBuilder sb = new StringBuilder();
    	postOrderHelper(root, sb);
    	return sb.toString();
    }
    
    private void postOrderHelper(Node node, StringBuilder sb) {
    	if (node == null) return;
    	postOrderHelper(node.left, sb);
    	postOrderHelper(node.right, sb);
    	if (sb.length() > 0) sb.append(" ");
    	sb.append(node.key);
    }
    
    /*
     * uses the classic two-stack approach: stack1 gives you 
     * a modified preorder (root→right→left), and stack2 
     * reverses that into postorder (left→right→root)
     */
    public String postorderNonRecursive() {
    	StringBuilder sb = new StringBuilder();
    	if (root == null) return sb.toString();
    	
    	Stack<Node> stack1 = new Stack<>();
    	Stack<Node> stack2 = new Stack<>();
    	stack1.push(root);
    	
    	while (!stack1.isEmpty()) {
    		Node curr = stack1.pop();
    		stack2.push(curr);
    		if (curr.left != null) stack1.push(curr.left);
    		if (curr.right != null) stack1.push(curr.right);
    	}
    	
    	while (!stack2.isEmpty()) {
    		if (sb.length() > 0) sb.append(" ");
    		sb.append(stack2.pop().key);
    	}
    	
    	return sb.toString();
    }
    
    /*
     * the minimum is always the leftmost node and 
     * the maximum is always the rightmost node. 
     * Both are simple iterative loops — just keep 
     * going left (or right) until there's nothing left to follow.
     */
    public K min() {
    	if (root == null) return null;
    	Node curr = root;
    	while(curr.left != null) curr = curr.left;
    	return curr.key;
    }
    
    public K max() {
    	if (root == null) return null;
    	Node curr = root;
    	while(curr.right != null) curr = curr.right;
    	return curr.key;
    }
    
    /*
     * fully recursive. When a node has two children, it copies 
     * the predecessor/successor key down and then recursively 
     * deletes that key from the appropriate subtree, which will 
     * always be a simpler case (0 or 1 child) at that point.
     */
    public boolean remove(K key, boolean preferLeft) {
    	if (key == null || root == null) return false;
    	int before = size(root);
    	root = removeHelper(root, key, preferLeft);
    	return size(root) < before;
    }
    
    private Node removeHelper(Node node, K key, boolean preferLeft) {
    	if (node == null) return null;
    	int cmp = key.compareTo(node.key);
    	
    	if(cmp < 0) {
    		node.left = removeHelper(node.left, key, preferLeft);
    	} else if (cmp > 0) {
    		node.right = removeHelper(node.right, key, preferLeft);
    	} else {
    		if (node.left == null && node.right == null) return null;
    		if (node.left == null) return node.right;
    		if (node.right == null) return node.left;
    		
    		if (preferLeft) {
    			K predKey = maxOf(node.left);
    			node.key = predKey;
    			node.left = removeHelper(node.left, predKey, preferLeft);
    		} else {
    			K succKey = minOf(node.right);
    			node.key = succKey;
    			node.right = removeHelper(node.right, succKey, preferLeft);
    		}
    	}
    	return node;
    }
    
    private K minOf(Node node) {
    	while (node.left != null) node = node.left;
    	return node.key;
    }
    
    private K maxOf(Node node) {
    	while (node.right != null) node = node.right;
    	return node.key;
    }
    
    /*
     * A perfect tree has all leaves at the same depth and 
     * every internal node has exactly two children. 
     * The helper checks: if it's a leaf, is it at the right depth? 
     * If it has only one child, immediately false. If it has two, 
     * recurse on both sides. The depth of the tree is calculated 
     * first with a height() helper.
     */
    public boolean isPerfect() {
    	int h = height(root);
    	return isPerfectHelper(root, h, 0);
    }
    
    private boolean isPerfectHelper(Node node, int height, int depth) {
    	if (node == null) return true;
    	if (node.left == null && node.right == null) return depth == height - 1;
    	if (node.left == null || node.right == null) return false;
    	return isPerfectHelper(node.left, height, depth + 1) 
    		&& isPerfectHelper(node.right, height, depth + 1);
    }
    
    private int height (Node node) {
    	if (node == null) return 0;
    	return 1 + Math.max(height(node.left), height(node.right));
    }
    
    /*
     * the BFS null-flag trick: once you see your first null child 
     * enqueued, any non-null after that means it's not complete.
     */
    public boolean isComplete() {
    	if (root == null) return true;
    	
    	Queue<Node> queue = new LinkedList<>();
    	queue.add(root);
    	boolean seenNull = false;
    	
    	while (!queue.isEmpty()) {
    		Node curr = queue.poll();
    		
    		if (curr == null) {
    			seenNull = true;
    		} else {
    			if (seenNull) return false;
    			queue.add(curr.left);
    			queue.add(curr.right);
    		}
    	}
    	return true;
    }
    
    /*
     * Every node must have either 0 or 2 children — no node can have exactly 1. 
     * Simple recursive check: if null, return true. If both children are null, 
     * return true (it's a leaf). If exactly one child is null, return false. 
     * Otherwise recurse on both children.
     */
    public boolean isFull() {
    	return isFullHelper(root);
    }
    
    private boolean isFullHelper(Node node) {
    	if (node == null) return true;
    	if (node.left == null && node.right == null) return true;
    	if (node.left == null || node.right == null) return false;
    	return isFullHelper(node.left) && isFullHelper(node.right);
    }
}

