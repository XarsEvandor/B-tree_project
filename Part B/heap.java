// PROJECT TITLE: Final Project PartB
// AUTHOR NAME: GIORGOS-PANAGIOTIS KATSONIS, Dr. GREGORY BAGLAVAS (displayHeap Method)
// PURPOSE OF PROJECT: To implement a heap data structure using arrays.
// VERSION or DATE: v 1.0
// AUTHORS: giorgos_katsonis@hotmail.com, grigoris@act.edu
// COPYRIGHT INFORMATION:  Content is copyright © Open Source Guides authors, released under CC-BY-4.0.
import java.util.ArrayList;
import java.util.List;

public class heap
{
	// Variables
    Node[] heapArray; //Array of Nodes
    int currentSize;
    int userSize;
    Node temp;
    List<Integer> keyList;

    /* Note
    left child = 2 * parent + 1
    right child = 2 * parent + 2 OR right child = left child + 1
    parent = (child -1) / 2 (integer division)
    */

	// Constructor
	public heap(int size, int hiddenSize)
    {
        heapArray = new Node[hiddenSize];
        currentSize = 0;
        this.userSize = size;
        keyList = new ArrayList<Integer>();
    }
    
    //I decided to work with the keys of the nodes instead of the nodes themselves due to the fact that, since we do not care
    //about node refferences, the nodes themselves do not matter.
    public void bubbleUp(int index) {
        //If the selected node reaches index zero it has become the root and the procedure stops.
        while (index != 0) {
            int parentIndex = (index - 1) / 2; //This variable is calculated every cycle based on the index.
            //If the key of the selected node is bigger than its parent, we swap their keys and change the index variable.
            if (heapArray[index].key > heapArray[parentIndex].key) {
                temp = new Node(heapArray[parentIndex].key);
                heapArray[parentIndex].key = heapArray[index].key;
                heapArray[index].key = temp.key;
                index = parentIndex;
            } else //If the key of the node at "index" is smaller than the key of the node at "parentIndex" the proccess stops.
                break;
        }
    }

    public void bubbleDown(int index) {
        //If the selected Node has no children we have reached the end of the path and we stop.
        while (heapArray[2 * index + 1] != null && heapArray[2 * index + 2] != null ) {
            int lChildIndex = 2 * index + 1;
            int rChildIndex = lChildIndex + 1;
            temp = new Node(-1);
            //If the node is smaller than both its childs, bubble down towards the largest one.
            if (heapArray[index].key < heapArray[lChildIndex].key
                    && heapArray[index].key < heapArray[rChildIndex].key) {
                if (heapArray[lChildIndex].key > heapArray[rChildIndex].key) {
                    temp.key = heapArray[lChildIndex].key;
                    heapArray[lChildIndex].key = heapArray[index].key;
                    heapArray[index].key = temp.key;
                    index = lChildIndex;
                }
                else {
                    temp.key = heapArray[rChildIndex].key;
                    heapArray[rChildIndex].key = heapArray[index].key;
                    heapArray[index].key = temp.key;
                    index = rChildIndex;
                }
            }
            else if (heapArray[index].key < heapArray[lChildIndex].key) {
                temp.key = heapArray[lChildIndex].key;
                heapArray[lChildIndex].key = heapArray[index].key;
                heapArray[index].key = temp.key;
                index = lChildIndex;
            }
            else if (heapArray[index].key < heapArray[rChildIndex].key) {
                temp.key = heapArray[rChildIndex].key;
                heapArray[rChildIndex].key = heapArray[index].key;
                heapArray[index].key = temp.key;
                index = rChildIndex;
            }
            else
                break;
        }
    }

	// Methods
   	public boolean isEmpty()
    {
        return currentSize == 0;
    }
    
    public boolean isFull()
    {
        return currentSize == userSize;
    }


   	public void insert(int key)
    {
        if (keyList.contains(key)) {
            System.out.println("No Duplicates are allowed in the heap. Aborting insertion...");
            return;
        }
        keyList.add(key);
        if (isFull()) {
            System.out.println("Heap is full. Aborting insertion....");
            keyList.remove(keyList.indexOf(key));
            return;
        }
        else if (isEmpty()) {
            System.out.println("Heap is empty. Adding node as root....");
            heapArray[0] = new Node(key);
        }
        else {
            heapArray[currentSize] = new Node(key);
            bubbleUp(currentSize);
        }

        currentSize++;
    } 

   	public Node remove()           
    {
        keyList.remove(keyList.indexOf(heapArray[0].key));             
        if (isEmpty()) {
            System.out.println("Heap is empty. Aborting Deletion....");
            return null;
        }

        Node deleted = heapArray[0];
        heapArray[0] = heapArray[currentSize-1];
        heapArray[currentSize-1] = null;
        currentSize--;
        bubbleDown(0);
    	return deleted; 	
    }

   	public void displayHeap()
    {
    	System.out.print("heapArray: ");    
      	for(int i=0; i<currentSize; i++)
      	{
         	if(heapArray[i] != null)
         	{
            	System.out.print( heapArray[i].key + " ");
            }
         	else
            {
            	System.out.print( "-- ");
            }
        }
      	System.out.println();
            
      	int nBlanks = 32;
      	int itemsPerRow = 1;
      	int column = 0;
      	int j = 0;                          
      	String dots = "...............................";
      	System.out.println(dots+dots);      

      	while(currentSize > 0)  
        {
         	if(column == 0)                  
         	{
            	for(int i=0; i<nBlanks; i++) 
            	{
               		System.out.print(' ');
               	}
            }
            System.out.print(heapArray[j].key);
			
			j++;
         	if(j == currentSize)          
         	{
            	break;
            }

			column++;
         	if(column==itemsPerRow)       
            {
            	nBlanks /= 2;             
            	itemsPerRow *= 2;         
            	column = 0;               
            	System.out.println();     
            }
         	else                          
         	{
            	for(int i=0; i<nBlanks*2-2; i++)
            	{
               		System.out.print(' ');     
            	}
         	}  
         }
      	 System.out.println("\n"+dots+dots); 
	}  
// -------------------------------------------------------------
}  