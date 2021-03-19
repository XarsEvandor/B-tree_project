public class Node
{
    // instance variables - replace the example below with your own
    public int key;
    public int height;
    public Node leftChild;
    public Node rightChild;
    public Node parent;
    
    /**
     * Constructor for objects of class Node
     */
    public Node(int key)
    {
        this.key = key;
        height = 0;
        leftChild = null;
        rightChild = null;
        parent = null;
    }

    public void display()
    {
        System.out.print(key);        
    }
}
