// PROJECT TITLE: Final Project PartB
// AUTHOR NAME: GIORGOS-PANAGIOTIS KATSONIS, Dr. GREGORY BAGLAVAS (displayHeap Method)
// PURPOSE OF PROJECT: To implement a heap data structure using arrays.
// VERSION or DATE: v 1.0
// AUTHORS: giorgos_katsonis@hotmail.com, grigoris@act.edu
// COPYRIGHT INFORMATION:  Content is copyright © Open Source Guides authors, released under CC-BY-4.0.
import java.util.Scanner;

public class heapMain {
    public static void main(String[] args) {
        Scanner userIn = new Scanner(System.in);
        int size = 0;
        int hiddenSize = 0;
        int key = 0;
        int choice = 0;
        int height = 0;
        heap hp;
        Node deleted;

        System.out.println("Welcome! Please select the size of the heap. (Up to 32 nodes)");
        System.out.print("Input: ");

        while (true) {
            try {
                size = userIn.nextInt();
                while (true) {
                    if (size >= 1 && size <= 32) {
                        break;
                    } else {
                        System.out.println("Please input a valid value.");
                        System.out.print("Input: ");
                        size = userIn.nextInt();
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Please input a valid value.");
                System.out.print("Input: ");
                userIn.next();
            }
        }

        //To compensate for the case that the array will be full and we will have to check at the children of a leaf,
        //we will add a new height level of nodes hidden to the user.

        //First we calculate the height of the tree according to the number of nodes the user chose. The ceil command rounds
        //up our logarythmic result to an integer.
        height = (int) Math.ceil(Math.log(size + 1) / Math.log(2)) - 1;

        //Next we will add enough nodes to cover the current height plus one more. Finally we will use hiddenSize instead of
        //Array.length in the isFull method of the heap class.
        hiddenSize = (int) Math.pow(2, (height + 2)) - 1;

        hp = new heap(size, hiddenSize);

        while (true) {
            System.out.println("Please select an operation.");
            //Chose not to implement a separate display option as it is more intuitive for the user to see the differences on the 
            //heap at the time he makes them. 
            System.out.println("1. Insertion.\n2. Deletion.\n3. EXIT.");
            System.out.print("Input: ");

            while (true) {
                try {
                    choice = userIn.nextInt();
                    while (true) {
                        if (choice == 1 || choice == 2 || choice == 3) {
                            break;
                        } else {
                            System.out.println("Please input a valid value.");
                            System.out.print("Input: ");
                            choice = userIn.nextInt();
                        }
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Please input a valid value.");
                    System.out.print("Input: ");
                    userIn.next();
                }
            }

            switch (choice) {
                case 1:

                    System.out.println("Please enter the key of the node you wish to insert.");
                    System.out.print("Input: ");
                    while (true) {
                        try {
                            key = userIn.nextInt();
                            break;
                        } catch (Exception e) {
                            System.out.println("Please input a valid value.");
                            System.out.print("Input: ");
                            userIn.next();
                        }
                    }

                    hp.insert(key);
                    hp.displayHeap();
                    break;

                case 2:

                    deleted = hp.remove();

                    if (deleted != null) {
                        System.out.println("Successful deletion of the Node containing value: " + deleted.key);
                    }

                    hp.displayHeap();

                    break;

                case 3:

                    System.out.println("Thank you for using this program. Exiting.....");
                    System.exit(0);

            }
        }

        
    }
}
