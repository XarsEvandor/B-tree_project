// PROJECT TITLE: Final Project PartC
// AUTHOR NAME: GIORGOS-PANAGIOTIS KATSONIS
// PURPOSE OF PROJECT: To read and write data to a file.
// VERSION or DATE: v 1.0
// AUTHORS: giorgos_katsonis@hotmail.com
// COPYRIGHT INFORMATION:  Content is copyright © Open Source Guides authors, released under CC-BY-4.0.
import java.io.*;

public class RedBlackReader {
    public static void main(String[] args) throws IOException {
        String line;
        String insertInput = "InsertInput.txt";
        String findInput = "FindInput.txt";
        String fileOutput = "Output.txt";
        BufferedWriter out = new BufferedWriter(new FileWriter(fileOutput));
        BufferedReader insertIn = new BufferedReader(new FileReader(insertInput));
        BufferedReader findIn = new BufferedReader(new FileReader(findInput));
        RedBlack tree = new RedBlack();

        //Try to read the contents of the file one line at a time. Then use the data as a key for the insert method.
        //If you come across a null pointer exception catch it and write the number of comparisons that is called from AVL.
        try {
            do {
                line = insertIn.readLine();
                System.out.println(line);
                tree.insert(Integer.parseInt(line));
            }
            while (line != null);
        } catch (Exception e) {
            System.out.println(e);
            out.write(tree.comparisonCounter + " comparisons made to create the tree.");
            out.flush(); //Very important. It flushes the contents of the buffer onto the file.
        }

        //Same idea but with the find method.
        try {
            do {
                line = findIn.readLine();
                System.out.println(line);
                tree.find(Integer.parseInt(line));
            }
            while (line != null);
        } catch (Exception e) {
            System.out.println(e);
            out.newLine();
            out.write(tree.comparisonCounter + " comparisons made by the find method.");
            out.flush();
            out.close();
        }
        
    }
}
