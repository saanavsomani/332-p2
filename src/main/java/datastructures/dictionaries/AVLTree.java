
package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {

    public AVLTree() { super(); }

    public class AVLNode extends BSTNode{
        @SuppressWarnings("unchecked")
        private int height;

        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;
        }

        private AVLNode getLeftChild() {
            return (AVLNode)this.children[0];
        }

        private AVLNode getRightChild() {
            return (AVLNode)this.children[1];
        }
    }

    private int getHeight(AVLNode node) {
        if (node == null) return -1;
        return node.height;
    }

    private void setHeight(AVLNode node) {
        if (node == null) throw new IllegalArgumentException();
        int leftHeight = getHeight(node.getLeftChild());
        int rightHeight = getHeight(node.getRightChild());
        node.height = Math.max(leftHeight, rightHeight) + 1;
    }

    private int getBalanceFactor(AVLNode node) {
        if (node == null) return -1;
        return getHeight(node.getRightChild()) - getHeight(node.getLeftChild());
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();
        V returnVal = find(key);
        this.root = insert((AVLNode)this.root, key, value);
        return returnVal;
    }

    private AVLNode insert(AVLNode node, K key, V value) {
        if (node == null) {
            this.size++;
            return new AVLNode(key, value);
        }
        //if node's key is less than given key --> move to left subtree
        if (key.compareTo(node.key) < 0) {
            node.children[0] = insert(node.getLeftChild(), key, value);
        } else if(key.compareTo(node.key) > 0) { //if node's key is more than given key --> move to right subtree
            node.children[1] = insert(node.getRightChild(), key, value);
        } else{
            node.value = value;
            return node;
        }
        node = rebalance(node);
        setHeight(node);
        return node;
    }

    private AVLNode rotateRight(AVLNode node) {
        AVLNode leftChild = node.getLeftChild();

        node.children[0] = leftChild.getRightChild();
        leftChild.children[1] = node;

        setHeight(node);
        setHeight(leftChild);

        return leftChild;
    }

    private AVLNode rotateLeft(AVLNode node) {
        AVLNode rightChild = node.getRightChild();

        node.children[1] = rightChild.getLeftChild();
        rightChild.children[0] = node;

        setHeight(node);
        setHeight(rightChild);

        return rightChild;
    }

    private AVLNode rebalance(AVLNode node) {
        int balance = getBalanceFactor(node);

        //Left-heavy
        if (balance < -1) {
            int leftBalance = getBalanceFactor(node.getLeftChild());
            if (leftBalance <= 0) {
                //Case 1: right rotation around problem node
                node = rotateRight(node);
            } else {
                //Case 2: left rotation at left child of problem + right rotation at problem node
                node.children[0] = rotateLeft(node.getLeftChild());
                node = rotateRight(node);
            }
        }

        //Right-heavy
        if (balance > 1) {
            int rightBalance = getBalanceFactor(node.getRightChild());
            if (rightBalance >= 0) {
                //Case 3: left rotation around problem node
                node = rotateLeft(node);
            } else {
                //Case 4: right rotation at right child of problem + left rotation at problem node
                node.children[1] = rotateRight(node.getRightChild());
                node = rotateLeft(node);
            }
        }
        return node;
    }



}

