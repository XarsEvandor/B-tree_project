// PROJECT TITLE: Final Project PartC
// AUTHOR NAME: GIORGOS-PANAGIOTIS KATSONIS, Dr. GREGORY BAGLAVAS(class code basis).
// PURPOSE OF PROJECT: To implement a Red-Black tree data structure using in class code.
// VERSION or DATE: v 1.0
// AUTHORS: giorgos_katsonis@hotmail.com, grigoris@act.edu
// COPYRIGHT INFORMATION:  Content is copyright © Open Source Guides authors, released under CC-BY-4.0.

import java.util.*; // Needed for the Stack class in the displayTree method

public class RedBlack
{
    // Variables
    Node root;
    List<Integer> keyList;
    int comparisonCounter;

    //For ease of understanding we will create two constants for the colors that correspond to the definition we set in Node.
    final boolean red = true;
    final boolean black = false;
    
    // Constructor
    public RedBlack()
    {
        // Constructor code goes here
        root = null;
        comparisonCounter = 0;
        keyList = new ArrayList<Integer>();
    }
    
    //To perform a right rotation we store the right child of the left child of the subroot in temp. We then make the subroot 
    //into the right child of the child finally we make the left child of the subRoot the temp Node. 
    //I know this makes almost no sense but i cannot explain it better without a graphical representation

    //We also update the height of the subRoot and the child but there is no need to do the same for the grandchild, since
    //its height remains the same.
    public Node rotateRight(Node subRoot) {
        Node child = subRoot.leftChild;
        Node temp = child.rightChild;
        if (subRoot.parent != null)
            child.parent = subRoot.parent;
        else
            child.parent = null;
        subRoot.parent = child;
        child.rightChild = subRoot;
        subRoot.leftChild = temp;
        if (temp != null) temp.parent = subRoot;
        return child;
    }
    
    //Since the left rotation is the mirror of the right rotation, we only change the relative positions of the thrre nodes of 
    //interest
    public Node rotateLeft(Node subRoot) {
        Node child = subRoot.rightChild;
        Node temp = child.leftChild;
        if (subRoot.parent != null)
            child.parent = subRoot.parent;
        else
            child.parent = null;
        subRoot.parent = child;
        child.leftChild = subRoot;
        subRoot.rightChild = temp;
        if (temp != null) temp.parent = subRoot;
        return child;
    }

    public void rebalance(Node subRoot) {
        //if the parent of the subRoot is black it cannot violate any properties and thus we do not account for that case.
        if (subRoot.parent.color == red) {
            //We start rebalancing the tree after the first level since we need to evaluate the subRoot's grandparent.
            if (subRoot.parent.parent != null) {
                //We need to locate the sibling of the parent of the subRoot.
                if (subRoot.parent.parent.leftChild == subRoot.parent) {
                    //In this case the sibling of the parent of the root is the right child of the grandparent.
                    if (subRoot.parent.parent.rightChild.color == red) {
                        //We have to perform a recolor. This means making the grandparent red, the parent black and the uncle
                        //also black. However we need to acount for the case that the grandparent is the root. In this case
                        //changing the color to red would be a violation of a rule, so we don't.
                        if (subRoot.parent.parent.parent != null)
                            subRoot.parent.parent.updateColor(red);
                        subRoot.parent.updateColor(black);
                        subRoot.parent.parent.rightChild.updateColor(black);
                    } else {
                        //Here it gets a bit complicated. Depending on the relation of the subroot to the parent, and of the parent
                        //to the grandparent, we need to perform different rotations.

                        //If the parent is the left child of the grandparent..

                        //..and subroot is the left child of parent, we perform a right rotation, and then we color the new
                        //parent to black and the new sibling of the root to red.
                        if (subRoot == subRoot.parent.leftChild) {
                            rotateRight(subRoot.parent.parent);
                            subRoot.parent.updateColor(black);
                            subRoot.parent.leftChild.updateColor(red);
                        }
                        //..and subroot is the right child of parent, we perform a left rotation, and then we repeat the steps
                        //of the previous case.
                        else {
                            rotateLeft(subRoot.parent.parent);
                            rotateRight(subRoot.parent.parent);
                            subRoot.parent.updateColor(black);
                            subRoot.parent.leftChild.updateColor(red);
                        }
                        
                    }
                }
                //Here we repeat the same steps but knowing that the uncle of the subroot is the left child of the grandparent.
                else {
                    if (subRoot.parent.parent.leftChild.color == red) {
                        if (subRoot.parent.parent.parent != null)
                            subRoot.parent.parent.updateColor(red);
                        subRoot.parent.updateColor(black);
                        subRoot.parent.parent.leftChild.updateColor(black);
                    } else {
                        if (subRoot.parent == subRoot.parent.parent.rightChild) {
                            if (subRoot == subRoot.parent.rightChild) {
                                rotateLeft(subRoot.parent.parent);
                                subRoot.parent.updateColor(black);
                                subRoot.parent.leftChild.updateColor(red);
                            }
                            else {
                                rotateRight(subRoot.parent.parent);
                                rotateLeft(subRoot.parent.parent);
                                subRoot.parent.updateColor(black);
                                subRoot.parent.leftChild.updateColor(red);
                            }
                        }
                    }
                }
            }
        }
    }

    public void insert(int key) // insert node with given key
    {
        // STEP 1: Create a new Node object with the given key
        Node newNode = new Node(key);

        if (keyList.contains(key)) {
            System.out.println("No Duplicates are allowed in the tree. Aborting insertion...");
            return;
        }
        keyList.add(key);
        
        if ( root == null ) // If the tree is empty,
        {
            root = newNode; // The newNode becomes the root
            newNode.updateColor(black); //The root must always be black.
        }
        else // The tree has Nodes
        {
            Node current = root; // Starting from the root Node,
            
            while ( true ) // Forever loop, breaks from the inside
            {
                if (current.key > key) {
                    comparisonCounter++;
                    if (current.leftChild == null) // Check if current has a left child
                    {
                        current.leftChild = newNode; // Place newNode as the leftChild of current
                        newNode.parent = current;
                        rebalance(newNode);
                        break; // Insertion Completed
                    } else // current has a left child
                    {
                        current = current.leftChild; // current moves to the left
                    }
                    // STEP 2: Compare the newNode's key with the 'current' Node one
                    // STEP 2a: If the key of the current Node is larger, then
                    //      current moves to the left, and we continue from STEP 2
                } else {
                    comparisonCounter++;
                    if (current.rightChild == null)// Check if current has a right child
                    {
                        current.rightChild = newNode; // Place newNode as the rightChild of current
                        newNode.parent = current;
                        rebalance(newNode);
                        break; // Insertion Completed
                    } else // current has a right child
                    {
                        current = current.rightChild;
                    }
                    // STEP 2b: If the key of the current Node is smaller, then
                    //      current moves to the right, and we continue from STEP 2
                }
                // STEP 3: Continue running STEP 2 until you cannot go either 
                //      left or right (empty). If this case, place the newNode
                //      in that position.
            }
        }
    }  

   public Node find(int key)      // find node with given key
   {  
       // STEP 1: Create a 'current' Node, starting from the root
       Node current = root;
       
       while ( current.key != key ) // For as long as we don not find the Node,
       {
       // STEP 2: Compare the key of the 'current' Node, with the given key 
            if ( current.key > key )
            {
                comparisonCounter++;
                current = current.leftChild; 
            // STEP 2a: If the key of the 'current' Node is larger, then
            //           'current' moves to the left and we continue from STEP 2
            }
            else
            {
                comparisonCounter++;
                current = current.rightChild;
            }
            // STEP 2b: If the key of the 'current' Node is smaller, then
            //           'current' moves to the right and we continue from STEP 2
            if ( current == null ) // we end up in a 'null' Node (the Node was not found in the tree)
            {
                return null; // Stops the execution of the method
            }
       }
       
       // We found the Node
       return current;
       
       // STEP 3: Continue running STEP 2 until either:
       // STEP 3a: we find the Node
       // STEP 3b: we end up in a 'null' Node (the Node was not found in the tree)
   }  
}  