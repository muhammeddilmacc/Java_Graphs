package Project2;

import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Muhammed
 */
public class BreadthFirstSearch {

    boolean[] marked;
    int[] edgeTo;
    int[] distTo;
    int from;
    static int counter = 0;

    public BreadthFirstSearch(GraphMatrix g, int from) {
        edgeTo = new int[g.numV];
        marked = new boolean[g.numV];
        distTo = new int[g.numV];
        this.from = from;
        bfs(g, from, g.hashTable);
    }

    public boolean hasPathTo(int w) {
        return marked[w];
    }

    public int distTo(int w) {
        return distTo[w];
    }

    public Integer[] pathTo(int w) {
        int k = edgeTo[w];
        java.util.Stack<Integer> st = new java.util.Stack<>();
        st.push(k);
        while (k != this.from) {
            if (k == edgeTo[k]) {
                break;
            }

            k = edgeTo[k];
            st.push(k);

        }

        Integer[] path = new Integer[st.size()];
        for (int i = 0; i < path.length; i++) {
            path[i] = st.pop();
        }
        return path;
    }

    public void printPathTo(int w, LinearProbingHash h1) {

        if (!hasPathTo(w)) {
            System.out.println("No path.");
            return;
        }

        Integer[] path = pathTo(w);

        for (int i = 0; i < path.length; i++) {

            System.out.print(h1.table[path[i]] + " -> ");
        }
        System.out.println(h1.table[w]);

    }

    public void bfs(GraphMatrix g, int source, LinearProbingHash h1) {
        if (counter > 0) {
            System.out.print("" + h1.table[source]);
        }
        marked[source] = true;
        int[] a = (int[]) g.neighborsArray(source);
        if (a.length == 0) {
            return;
        }

        LinkedList<Integer> q = new LinkedList<>();
        q.addLast(source);
        while (!q.isEmpty()) {
            source = q.removeFirst();
            a = (int[]) g.neighborsArray(source);
            for (int i = 0; i < a.length; i++) {
                int w = a[i];
                if (!marked[w]) {

                    if (counter > 0) {
                        System.out.print(", " + h1.table[w]);
                    }
                    q.addLast(w);
                    marked[w] = true;
                    edgeTo[w] = source;
                    distTo[w] = distTo[source] + 1;
                }
            }
        }
        System.out.println("");
    }
}
