package Week1.UndirectedGraph;

import edu.princeton.cs.algs4.Graph;

public class CC {
    private boolean[] marked;
    private int count;
    private int[] id;
    public CC(Graph G){
        marked = new boolean[G.V()];
        count = 0;
        int[] id = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (!marked[i]){
                dfs(G,i);
                count++;
            }
        }
    }
    private void dfs(Graph G , int v){
        marked[v] = true;
        id[v] = count;
        for (Integer w : G.adj(v)) {
            if (!marked[w]){
                dfs(G,w);
            }
        }
    }
}
