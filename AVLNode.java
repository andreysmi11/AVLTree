/**
 * AVL Tree Node
 * @author Andrey Smiryagin
 * @param <T>
 */

public class AVLNode<T> extends BinaryNode<T>
{
    public int height;
    /**
     * AVLNode Constructor.
     */
    public AVLNode()
    {
        super();
        height = -1;
    }

    /**
     * AVL Constructor with Data parameter.
     * @param data is Data of the Node.
     */
    public AVLNode(T data)
    {
        super(data);
        height = 0;
    }

    /**
     * AVL Node Constructor with Data and Left, Right Nodes.
     * @param data Data of the node.
     * @param leftChild Left Node.
     * @param rightChild Right Node.
     */
    public AVLNode(T data, AVLNode<T> leftChild, AVLNode<T> rightChild)
    {
        super(data, leftChild, rightChild);
        this.height = 1;
    }

    /**
     * Sets the left Child.
     * @param leftChild new Left Child.
     */
    public void setLeftChild(BinaryNode<T> leftChild) {
        super.setLeftChild(leftChild);
        this.height = getHeight();
    }

    /**
     * Sets the right child.
     * @param rightChild new Right Child.
     */
    public void setRightChild(BinaryNode<T> rightChild) {
        super.setRightChild(rightChild);
        this.height = getHeight();
    }

    /**
     * Gets the Heights.
     * @return int Height.
     */
    public int getHeight() {

        if(this.isLeaf())
            this.height = 0;
        else if(this.hasLeftChild() && this.hasRightChild())
            this.height = Math.max(super.getLeftChild().getHeight(), super.getRightChild().getHeight()) + 1;
        else if(!this.hasLeftChild() && this.hasRightChild())
            this.height = super.getRightChild().getHeight() + 1;
        else if(this.hasLeftChild() && !this.hasRightChild())
            this.height = super.getLeftChild().getHeight() + 1;
        return this.height;
    }


}