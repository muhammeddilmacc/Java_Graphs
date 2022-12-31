package Project2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Muhammed
 */
public class DepthFirstPaths {

    boolean[] marked;
    int[] edgeTo;
    int from;

    public boolean hasPathTo(int w) {
        return marked[w];
    }

    public Integer[] pathTo(int w) {
        int k = edgeTo[w];
        java.util.Stack<Integer> st = new java.util.Stack<Integer>();
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

        // if there is no path, don't write any way. write no path
        if (!hasPathTo(w)) {
            System.out.println("No path.");
            return;
        }

        Integer[] path = pathTo(w);

        for (int i = 0; i < path.length; i++) {

            System.out.print(h1.table[path[i]] + ", ");
        }
        System.out.println(h1.table[w]);

    }

    public DepthFirstPaths(GraphMatrix g, int from) {
        edgeTo = new int[g.numV];
        marked = new boolean[g.numV];
        this.from = from;
        dfs(g, from);
    }

    public void dfs(GraphMatrix g, int source) {
        marked[source] = true;
        int[] a = (int[]) g.neighborsArray(source);
        for (int i = 0; i < a.length; i++) {
            int neighbor = a[i];
            if (!marked[neighbor]) {
                dfs(g, neighbor);
                edgeTo[neighbor] = source;
            }
        }
    }
}
