package Project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Muhammed
 */
public class GraphMatrix {

    int edges[][];
    int numV;
    int numE;
    LinearProbingHash<String> hashTable;
    int numberOfPaths = 0;
    int shortestPathLength = 100000; // in order to find (compare) the shorthest path initial value must be a large number

    public GraphMatrix(int V) {
        this.numV = V;
        edges = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                edges[i][j] = 0;
            }
        }
    }

    public void addEdge(int from, int to, int weight) {
        edges[from][to] = weight;
        edges[to][from] = weight;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < numV; i++) {
            for (int j = 0; j < numV; j++) {
                s.append(edges[i][j] + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    //takes the neighbors of given index and return as linkedList
    public LinkedList<Integer> neighborsList(int from) {
        LinkedList<Integer> neighborsList = new LinkedList<>();
        for (int i = 0; i < edges[from].length; i++) {
            if (edges[from][i] != 0) {
                neighborsList.add(i);
            }
        }
        return neighborsList;
    }

    //takes the neighbors of given index and return as int array
    public int[] neighborsArray(int from) {
        int[] ar = new int[edges[from].length];
        int size = 0;

        for (int i = 0; i < edges[from].length; i++) {
            if (edges[from][i] != 0) {
                ar[i] = i;
                size++;
            }
        }

        int[] ar2 = new int[size];
        int j = 0;

        for (int i = 0; i < ar.length; i++) {
            if (ar[i] != 0) {
                ar2[j] = ar[i];
                j++;
            }
        }

        return ar2;
    }

    //----------------------------------------------------------------------------------------------------
    // search is there a path, using DepthFirstPaths class
    public void IsThereAPath(String name1, String name2) {
        int n1 = hashTable.search(name1);
        int n2 = hashTable.search(name2);

        DepthFirstPaths p2 = new DepthFirstPaths(this, n1);
        System.out.println("IsThereAPath(" + name1 + "," + name2 + "): " + p2.hasPathTo(n2) + "\n");

    }

    //----------------------------------------------------------------------------------------------------
    public void AllPathsShorterThanEqualTo(int maxPathLength, int minVerticesNo, String name1) {
        boolean[] marked = new boolean[numV];
        Stack<Integer> path = new Stack<>();
        int source = hashTable.search(name1);

        path.push(source);

        System.out.println("AllPathsShorterThanEqualTo(" + maxPathLength + ", " + minVerticesNo + ", " + name1 + "): \n");

        // recursive method calls until all possible path are visited  
        allPathsFromGivenNameTo(maxPathLength, minVerticesNo, source, marked, path);

        numberOfPaths = 0;

    }

    // takes a source and keeps continue until reach the given values(works for AllPathsShorterThanEqualTo() method)
    public void allPathsFromGivenNameTo(int maxPathLength, int minVerticesNo, int source, boolean[] marked, Stack<Integer> path) {

        int newLength = calculatePathlength(path);

        if (path.size() >= minVerticesNo && newLength <= maxPathLength) {
            printPath(path);
            // System.out.println(path);

        }

        marked[source] = true;

        // stores neighbors of source.
        int[] allNeighbors = this.neighborsArray(source);
        int vertex = 0;

        for (int i = 0; i < allNeighbors.length; i++) {
            vertex = allNeighbors[i];

            if (!marked[vertex]) {
                path.push(vertex);
                allPathsFromGivenNameTo(maxPathLength, minVerticesNo, vertex, marked, path);
                path.pop();
                marked[vertex] = false;
            }
        }
    }

    // calculates given path length (works for AllPathsShorterThanEqualTo() method in allPathsFromGivenNameTo())
    public int calculatePathlength(Stack<Integer> path) {
        int v1 = 0;
        int v2 = 0;
        int newLength = 0;
        Stack<Integer> currPath = (Stack<Integer>) path.clone();

        v1 = currPath.pop();
        while (!currPath.empty()) {
            v2 = currPath.pop();
            newLength += edges[v1][v2];
            v1 = v2;
        }
        return newLength;
    }

    // takes a stack path, prints a that path(works for AllPathsShorterThanEqualTo() method in allPathsFromGivenNameTo())
    public void printPath(Stack<Integer> path) {

        Stack<Integer> currPath = (Stack<Integer>) path.clone();

        int[] patharray = new int[currPath.size()];
        int i = currPath.size() - 1;

        while (!currPath.empty()) {
            patharray[i] = currPath.pop();
            i--;
        }

        System.out.print(hashTable.getName(patharray[0]));
        for (int j = 1; j < patharray.length; j++) {
            System.out.print(", " + hashTable.getName(patharray[j]));
        }
        System.out.println("\n");
    }

    //----------------------------------------------------------------------------------------------------
    // takes two name and finds shortest path.
    public void ShortestPathLengthFromTo(String name1, String name2) {

        boolean[] marked = new boolean[numV];
        Stack<Integer> path = new Stack<>();
        int source = hashTable.search(name1);
        int destination = hashTable.search(name2);

        path.push(source);

        possiblePathsLength(source, destination, marked, path);

        if (shortestPathLength == 100000) {
            System.out.println("ShortestPathLengthFromTo(" + name1 + ", " + name2 + "): infinty \n");
        } else {
            System.out.println("ShortestPathLengthFromTo(" + name1 + ", " + name2 + "): " + shortestPathLength + "\n");
            shortestPathLength = 100000;
        }

    }

    // takes some parameter from ShortestPathLengthFromTo() method then try to find out shortest path
    // this method works for ShortestPathLengthFromTo()
    public void possiblePathsLength(int source, int destination, boolean[] marked, Stack<Integer> path) {

        if (source == destination) {

            int v1 = 0;
            int v2 = 0;
            int newLength = 0;
            Stack<Integer> currPath = (Stack<Integer>) path.clone();

            v1 = currPath.pop();
            while (!currPath.empty()) {
                v2 = currPath.pop();
                newLength += edges[v1][v2];
                v1 = v2;
            }

            if (newLength < shortestPathLength) {
                shortestPathLength = newLength;
            }

            return;
        }

        marked[source] = true;

        // stores neighbors of source.
        int[] allNeighbors = this.neighborsArray(source);
        int vertex = 0;

        for (int i = 0; i < allNeighbors.length; i++) {
            vertex = allNeighbors[i];

            if (!marked[vertex]) {
                path.push(vertex);
                possiblePathsLength(vertex, destination, marked, path);
                path.pop();
                marked[vertex] = false;
            }
        }
    }

    //----------------------------------------------------------------------------------------------------
    // takes two name and search all possible paths with CountAllPath() method
    // after CountAllPath() method done, writes number of numberOfPaths
    public void NoOfPathsFromTo(String name1, String name2) {

        boolean[] marked = new boolean[numV];
        Stack<Integer> path = new Stack<>();
        int source = hashTable.search(name1);
        int destination = hashTable.search(name2);

        path.push(source);

        // recursive method calls until all possible path are visited
        CountAllPath(source, destination, marked, path);
        System.out.println("NoOfPathsFromTo(" + name1 + ", " + name2 + "): " + numberOfPaths + "\n");
        numberOfPaths = 0;
    }

    // takes some parameter from NoOfPathsFromTo() method then counts all possible paths using stack
    // this method works for NoOfPathsFromTo()
    public void CountAllPath(int source, int destination, boolean[] marked, Stack<Integer> path) {

        if (source == destination) {
            numberOfPaths++;
            // System.out.println(path);  //if there is a path, it writes that path and returns
            // list = path;
            return;
        }

        marked[source] = true;

        // stores neighbors of source.
        int[] allNeighbors = this.neighborsArray(source);
        int vertex = 0;

        for (int i = 0; i < allNeighbors.length; i++) {
            vertex = allNeighbors[i];

            if (!marked[vertex]) {
                path.push(vertex);
                CountAllPath(vertex, destination, marked, path);
                path.pop();
                marked[vertex] = false;
            }
        }
    }

    //----------------------------------------------------------------------------------------------------
    // takes two name and go for DepthFirstPaths, then writes the path 
    public void DFSfromTo(String name1, String name2) {
        int n1 = hashTable.search(name1);
        int n2 = hashTable.search(name2);

        DepthFirstPaths p = new DepthFirstPaths(this, n1);
        System.out.print("DFSfromTo(" + name1 + "," + name2 + "): ");
        p.printPathTo(n2, hashTable);
        System.out.println("");
    }

    //----------------------------------------------------------------------------------------------------
    // takes two name and go for BreadthFirstSearch, then write the path that visited in search
    public void BFSfromTo(String name1, String name2) {
        int vertex1No = hashTable.search(name1);
        int vertex2No = hashTable.search(name2);

        BreadthFirstSearch.counter++;
        System.out.print("BFSfromTo(" + name1 + "," + name2 + "): ");
        BreadthFirstSearch p = new BreadthFirstSearch(this, vertex1No);
        BreadthFirstSearch.counter--;
        System.out.println("");
    }

    //----------------------------------------------------------------------------------------------------
    // takes name and search number of vertices in that component
    public void NoOfVerticesInComponent(String name) {

        ConnectedComponents conn = new ConnectedComponents(this);

        int vertexNo = this.hashTable.search(name);
        int grupNo = conn.id[vertexNo];

        int numberOfVertices = conn.NoOfVerticesInComponent(grupNo);
        System.out.println("NoOfVerticesInComponent(" + name + "): " + numberOfVertices + "\n");

    }

}
