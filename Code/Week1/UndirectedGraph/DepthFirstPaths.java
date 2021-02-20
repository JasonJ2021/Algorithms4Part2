package Week1.UndirectedGraph;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s; // 起点
    public DepthFirstPaths(Graph G ,int s){
        this.s = s;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        dfs(G,s);
    }
    private void dfs(Graph G , int v){
        marked[v] = true;
        for (Integer w : G.adj(v)) {
            if (!marked[w]){
                edgeTo[w] = v;
                dfs(G,w);
            }
        }
    }
    public boolean hasPathTo(int v){
        return marked[v];
    }
    public Iterable<Integer> pathTo(int v){
        if (marked[v] == false)return null;
        Stack<Integer> path = new Stack<>();
        for(int x = v;x !=s; x = edgeTo[x]){
            path.push(x);
        }
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge(0,5);
        graph.addEdge(2,4);
        graph.addEdge(2,3);
        graph.addEdge(1,2);
        graph.addEdge(0,1);
        graph.addEdge(3,4);
        graph.addEdge(3,5);
        graph.addEdge(0,2);
        DepthFirstPaths depthFirstPaths = new DepthFirstPaths(graph,0);
        boolean b = depthFirstPaths.hasPathTo(5);
        System.out.println(b);
        for (Integer integer : depthFirstPaths.pathTo(5)) {
            System.out.println(integer);
        }
    }
}
