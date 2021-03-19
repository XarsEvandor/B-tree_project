// PROJECT TITLE: Final Project PartC
// AUTHOR NAME: GIORGOS-PANAGIOTIS KATSONIS, Dr. GREGORY BAGLAVAS (class code basis).
// PURPOSE OF PROJECT: To implement an AVL tree data structure using in class code.
// VERSION or DATE: v 1.0
// AUTHORS: giorgos_katsonis@hotmail.com, grigoris@act.edu
// COPYRIGHT INFORMATION:  Content is copyright © Open Source Guides authors, released under CC-BY-4.0.

import java.util.*; // Needed for the Stack class in the displayTree method

public class AVL
{
    // Variables
    Node root;
    List<Integer> keyList;
    int comparisonCounter;
    
    // Constructor
    public AVL()
    {
        // Constructor code goes here
        root = null;
        comparisonCounter = 0;
        keyList = new ArrayList<Integer>();
    }

    //The ?: conditional operators are used as an efficient way to implement an if-else statement. In this line we are saying
    //if the no Node is null return -1, otherwise return its height.
    public int height(Node no) {
        return no == null ? -1 : no.height;
    }

    /*This method updates the height of the node based on its children. Math.max will return the longest of the two values.
    Notice that if Node no was a leaf, both children would be null and thus return a height of -1. Since we add 1 we reach
    height of 0 that is accurate for a leaf node. In any case, this will consider the longest path from our position to
    the end of the tree, and is thus applicable for all occassions.
    */
    public void updateHeight(Node no) {
        no.height = 1 + Math.max(height(no.leftChild), height(no.rightChild));
    }

    //If the Node no is null we return a balance of zero. Otherwise we use the formula Balance = height of the right leading path
    // minus height of the right leading path, to calculate the balance of any given node.
    public int getBalance(Node no) {
        return (no == null) ? 0 : height(no.rightChild) - height(no.leftChild);
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
        updateHeight(subRoot);
        updateHeight(child);
        return child;
    }
    
    //Since the left rotation is the mirror of the right rotation, we only change the relativepositions of the three nodes of 
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
        updateHeight(subRoot);
        updateHeight(child);
        return child;
    }

    //In this method, we take into consideration the balance of the node and we perform the correct rotation (R,L,RL,LR)
    public void rebalance(Node subRoot) {
        updateHeight(subRoot);
        int balance = getBalance(subRoot);
        if (balance > 1) {
            if (height(subRoot.rightChild.rightChild) > height(subRoot.rightChild.leftChild)) {
                comparisonCounter++;
                subRoot = rotateLeft(subRoot);
            } else {
                comparisonCounter++;
                subRoot.rightChild = rotateRight(subRoot.rightChild);
                subRoot = rotateLeft(subRoot);
            }
        } else if (balance < -1) {
            if (height(subRoot.leftChild.leftChild) > height(subRoot.leftChild.rightChild)) {
                comparisonCounter++;
                subRoot = rotateRight(subRoot);
            }
            else {
                comparisonCounter++;
                subRoot.leftChild = rotateLeft(subRoot.leftChild);
                subRoot = rotateRight(subRoot);
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
                        updateHeight(newNode);
                        updateHeight(newNode.parent);
                        if(newNode.parent.parent != null) rebalance(newNode.parent.parent);
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
                        updateHeight(newNode);
                        updateHeight(newNode.parent);
                        if(newNode.parent.parent != null) rebalance(newNode.parent.parent);
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

   public void displayTree()
      {
      Stack globalStack = new Stack();
      globalStack.push(root);
      int nBlanks = 32;
      boolean isRowEmpty = false;
      System.out.println(
      "......................................................");
      while(isRowEmpty==false)
         {
         Stack localStack = new Stack();
         isRowEmpty = true;

         for(int j=0; j<nBlanks; j++)
            System.out.print(' ');

         while(globalStack.isEmpty()==false)
            {
            Node temp = (Node)globalStack.pop();
            if(temp != null)
               {
               System.out.print(temp.key);
               localStack.push(temp.leftChild);
               localStack.push(temp.rightChild);

               if(temp.leftChild != null ||
                                   temp.rightChild != null)
                  isRowEmpty = false;
               }
            else
               {
               System.out.print("--");
               localStack.push(null);
               localStack.push(null);
               }
            for(int j=0; j<nBlanks*2-2; j++)
               System.out.print(' ');
            }  // end while globalStack not empty
         System.out.println();
         nBlanks /= 2;
         while(localStack.isEmpty()==false)
            globalStack.push( localStack.pop() );
         }  // end while isRowEmpty is false
      System.out.println(
      "......................................................");
      }  // end displayTree()
   }  