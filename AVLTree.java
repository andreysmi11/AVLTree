/**
 * @author Andrey Smiryagin
 * AVL Tree.
 */

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T>
{
    /**
     * Constructor.
     */
    public AVLTree()
    {
        super();
        super.root = (AVLNode<T>) super.getRootNode();
    }

    /**
     * AVL Constructor with root node parameter.
     * @param rootEntry will be the Root node.
     */
    public AVLTree(T rootEntry)
    {
        super.root = new AVLNode<T>(rootEntry);
    }

    /**
     * Adds a node with rotation to the AVL Tree.
     * @param data data of the Node.
     * @return returns the T data of the node.
     */
    public T add(T data)
    {
        super.add(data);
        super.root = changeToAVL(super.root);

        if(checkForBalance(root))
            return data;
        else
        {
            super.root = findUnbalancedNode(root);
            return data;
        }

    }

    /**
     * Removes a node with Rotation.
     * @param data is the Data of the node.
     * @return
     */
    public T remove(T data)
    {
        super.remove(data);

        if(checkForBalance(root))
            return data;
        else
        {
            super.root = findUnbalancedNode(root);
            return data;
        }
    }

    /**
     * Changes the tree in to AVL nodes.
     * @param node is the passing node.
     * @return returns an AVLNode.
     */
    private AVLNode<T> changeToAVL(BinaryNode<T> node)
    {
        AVLNode<T> newNode = new AVLNode<T>(node.getData());
        if(node.getLeftChild() != null)
        {
            AVLNode<T> newLNode = changeToAVL(node.getLeftChild());
            newNode.setLeftChild(newLNode);
        }
        if(node.getRightChild() != null)
        {
            AVLNode<T> newRNode = changeToAVL(node.getRightChild());
            newNode.setRightChild(newRNode);
        }
        return newNode;
    }

    /**
     * Checks if the tree is balanced.
     * @return a boolean.
     */
    private boolean checkForBalance(BinaryNode<T> node)
    {
        if(node == null)
            return true;
        if(Math.abs(getBalance(node)) >= 2 )
            return false;
        return checkForBalance(node.getLeftChild()) && checkForBalance(node.getRightChild());
    }
    /**
     * Finds the balance of a AVL sub tree.
     * @param tree is a node.
     * @return an int.
     */
    private int getBalance(BinaryNode<T> tree)
    {
        if(tree == null) {
            return -1;
        }
        else if(tree.isLeaf())
        {
            return 0;
        }
        else if(tree.getLeftChild() != null && tree.getRightChild() != null)
        {
            return tree.getLeftChild().getHeight() - tree.getRightChild().getHeight();
        }
        else if(tree.getLeftChild() != null && tree.getRightChild() == null)
        {
            return tree.getLeftChild().getHeight() - (-1);
        }
        else
        {
            return (-1) - tree.getRightChild().getHeight();
        }
    }
    //Rotations Methods

    /**
     * Rebalances the Tree.  node.getData().compareTo(node.getRightChild().getData()) < 0
     */
    private BinaryNode<T> reBalance(AVLNode<T> node)
    {
        if(getBalance(node) < -1)//right heavy
        {
            if(getBalance(node.getRightChild()) > 1) //node.getData().compareTo(node.getLeftChild().getData()) < 0
            {
                node = leftRightRotate((AVLNode<T>) node);
            }
            else
            {

                node = leftRotate((AVLNode<T>) node);
            }
        }
        else if(getBalance(node) > 1)//left Heavy
        {
            if(getBalance(node.getLeftChild()) < -1) //getBalance(node.getLeftChild()) < -1
            {
                node = rightLeftRotate((AVLNode<T>) node);
            }
            else
            {
                node = rightRotate((AVLNode<T>) node);
            }
        }
        return node;
    }

    /**
     * Finds the Unbalanced Node.
     * @param node is a node from the Tree.  (Math.abs(getBalance(node)) > 1)
     * @return returns AVLNode  && ()
     */
    private BinaryNode<T> findUnbalancedNode(BinaryNode<T> node)
    {
        if(Math.abs(getBalance(node)) > 1 && Math.abs(getBalance(node.getLeftChild())) < 2 && Math.abs(getBalance( node.getRightChild())) < 2)
        {
            node = reBalance((AVLNode<T>) node);
        }
        else
        {
            if(node.getLeftChild() != null)
                node.setLeftChild(findUnbalancedNode((AVLNode<T>) node.getLeftChild()));
            if(node.getRightChild() != null)
                node.setRightChild(findUnbalancedNode((AVLNode<T>) node.getRightChild()));
        }
        return node;
    }


    /**
     * Performs a right rotation.
     * @param node which could be an AVLNode.
     * @return AVLNode which is the rotated node.
     */
    private AVLNode<T> rightRotate(AVLNode<T> node)
    {
        AVLNode<T> helperNode = (AVLNode<T>) node.getLeftChild();
        AVLNode<T> helperNode2 = (AVLNode<T>) helperNode.getRightChild();
        node.setLeftChild(helperNode2);

        helperNode.setRightChild(node);

        node.height = node.getHeight();
        helperNode.height = helperNode.getHeight();
        return helperNode;
    }

    /**
     * Performs a left rotation.
     * @param node which could be an AVLNode.
     * @return AVLNode which is the rotated node.
     */
    private AVLNode<T> leftRotate(AVLNode<T> node)
    {
        AVLNode<T> helperNode = (AVLNode<T>) node.getRightChild();
        AVLNode<T> helperNode2 = (AVLNode<T>) helperNode.getLeftChild();
        node.setRightChild(helperNode2);

        helperNode.setLeftChild(node);

        node.height = node.getHeight();
        helperNode.height = helperNode.getHeight();
        return helperNode;
    }

    /**
     * Performs a Left Right Rotation.
     * @param node which could be an AVLNode.
     * @return AVLNode which is the rotated node.
     */
    private AVLNode<T> leftRightRotate(BinaryNode<T> node)
    {
        node.setLeftChild(leftRotate((AVLNode<T>)node.getLeftChild()));
        return rightRotate((AVLNode<T>) node);
    }

    /**
     * Performs a Right Left Rotation.
     * @param node which could be an AVLNode.
     * @return AVLNode which is the rotated node.
     */
    private AVLNode<T> rightLeftRotate(BinaryNode<T> node)
    {
        node.setRightChild(rightRotate((AVLNode<T>)node.getRightChild()));
        return leftRotate((AVLNode<T>) node);
    }
}