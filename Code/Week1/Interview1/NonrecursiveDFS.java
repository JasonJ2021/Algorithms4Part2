package Week1.Interview1;


import edu.princeton.cs.algs4.Graph;

import java.util.Stack;

public class NonrecursiveDFS {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    public NonrecursiveDFS(Graph g , int s ){
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        this.s = s;
        dfs(g , s);
    }
    private void dfs(Graph g,int s){
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);
        while(!stack.isEmpty()){
            Integer pop = stack.pop();
            for (Integer w : g.adj(pop)) {
                if (!marked[w]){
                    stack.push(w);
                    marked[w] = true;
                    edgeTo[w] = pop;
                }
            }
        }
    }
    public Iterable<Integer> pathTo(int v){
        if (marked[v] == false)return null;
        edu.princeton.cs.algs4.Stack<Integer> path = new edu.princeton.cs.algs4.Stack<>();
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
        NonrecursiveDFS nonrecursiveDFS = new NonrecursiveDFS(graph,0);
        for (Integer integer : nonrecursiveDFS.pathTo(4)) {
            System.out.println(integer);
        }
    }
}
