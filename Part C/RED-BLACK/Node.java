public class Node
{
    // instance variables - replace the example below with your own
    public int key;
    //We will set the black color to be false and the red color to be true.
    public boolean color;
    public Node leftChild;
    public Node rightChild;
    public Node parent;
    
    //we initialize all nodes as red. We will also assume that all null nodes are black in our implementation.
    public Node(int key)
    {
        this.key = key;
        color = true;
        leftChild = null;
        rightChild = null;
        parent = null;
    }

    public void display()
    {
        System.out.print(key);
    }
    
    public void updateColor(Boolean clr) {
        color = clr;
    }
}
