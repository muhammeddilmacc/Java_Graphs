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
public class ConnectedComponents {

    private boolean marked[];
    private int count; // holds number of vertex in a connected component
    int[] id; // holds all component and their group.

    // Constructure 
    public ConnectedComponents(GraphMatrix g) {
        marked = new boolean[g.numV];
        count = 0;
        id = new int[g.numV];
        for (int v = 0; v < g.numV; v++) {
            if (!marked[v]) {
                dfs(g, v);
                count++;
            }
        }
    }

    // it is a recursive method, seperates all component in their group 
    public void dfs(GraphMatrix g, int v) {
        marked[v] = true;
        id[v] = count;
        int[] a = (int[]) g.neighborsArray(v);
        for (int i = 0; i < a.length; i++) {
            if (!marked[a[i]]) {
                dfs(g, a[i]);
            }
        }
    }

    // takes a group no and returns number of an element belongs that group
    public int NoOfVerticesInComponent(int group) {
        int numberOfVertices = 0;
        for (int i = 0; i < id.length; i++) {
            if (id[i] == group) {
                numberOfVertices++;
            }
        }
        return numberOfVertices;
    }

}
