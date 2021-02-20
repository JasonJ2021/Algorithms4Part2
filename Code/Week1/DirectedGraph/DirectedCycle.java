package Week1.DirectedGraph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

public class DirectedCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    private boolean[] onStack;
    public DirectedCycle(Digraph G){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        onStack = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (!marked[i]){
                dfs(G,i);
            }
        }
    }
    private void dfs(Digraph G , int v ){
        onStack[v] = true;
        marked[v] = true;
        for (Integer w : G.adj(v)) {
            if (hasCycle()){
                return;
            }else if (!marked[w]){
                edgeTo[w] = v;
                dfs(G,w);
            }else if (onStack[w]){
                cycle = new Stack<Integer>();
                for (int i = v; i !=v;i = edgeTo[i]){
                    cycle.push(i);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }
    public boolean hasCycle(){
        return cycle !=null;
    }
    public Iterable<Integer> cycle(){
        return cycle;
    }
}
