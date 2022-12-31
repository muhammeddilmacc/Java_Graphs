/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Muhammed
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        String filepath = "C:\\Users\\Muhammed\\OneDrive\\Masaüstü\\names.txt";

        LinearProbingHash<String> h1 = readfromFileIntoHastable(filepath);
       // System.out.println("\ntable: " + h1.toString() + "\n");

        GraphMatrix graph = readGraphMatrixfromFile(filepath, h1);
        //System.out.print("Matrix: \n" + graph.toString());

        System.out.println("\n____________________________________________________________\n");

        graph.IsThereAPath("aynaz", "ece");
        graph.IsThereAPath("ali", "sina");
        graph.AllPathsShorterThanEqualTo(4,3,"ali");
        graph.ShortestPathLengthFromTo("ali", "sara");
        graph.ShortestPathLengthFromTo("ali", "rahim");
        graph.NoOfPathsFromTo("ece", "taymaz");
        graph.NoOfPathsFromTo("rahim", "sevgi");
        graph.NoOfPathsFromTo("rahim", "ece");
        graph.DFSfromTo("ece", "aynaz");
        graph.DFSfromTo("ece", "ali");
        graph.BFSfromTo("ece", "aynaz");
        graph.NoOfVerticesInComponent("taymaz");


        System.out.println("____________________________________________________________\n");

    }

    public static LinearProbingHash<String> readfromFileIntoHastable(String f) {

        LinearProbingHash<String> temp = new LinearProbingHash<>(30);

        try {
            Scanner sc = new Scanner(new File(f));
            int v = sc.nextInt();
            int e = sc.nextInt();

            for (int i = 0; i < e; i++) {

                String name1 = sc.next();
                String name2 = sc.next();
                int weight = sc.nextInt();

                temp.insert(name1);
                temp.insert(name2);
            }
            return temp;

        } catch (FileNotFoundException e) {
            System.out.println("there is a mistake. ");
            return null;

        }

    }

    public static GraphMatrix readGraphMatrixfromFile(String f, LinearProbingHash hashTable) throws IOException {

        try {
            Scanner sc = new Scanner(new File(f));
            int v = sc.nextInt();
            int e = sc.nextInt();

            System.out.print("A graph of " + v + " vertices and " + e + " edges  is loaded");
            GraphMatrix graph = new GraphMatrix(hashTable.size);
            graph.hashTable = hashTable;
            graph.numE = e;
            for (int i = 0; i < e; i++) {
                int v1 = hashTable.search(sc.next());
                int v2 = hashTable.search(sc.next());
                int weight = sc.nextInt();
                graph.addEdge(v1, v2, weight);
            }
            System.out.println(" ");
            return graph;

        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            return null;
        }
    }

}
