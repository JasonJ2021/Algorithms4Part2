package Week1.UndirectedGraph;

import edu.princeton.cs.algs4.Bag;

public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    public Graph(int V){
        this.V = V;
        E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<Integer>();
        }
    }
    public int V(){
        return V;
    }
    public int E(){
        return E;
    }
    public void addEdge(int v , int w){
        //将v w 连接起来
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }
    public Iterable<Integer> adj(int v){
        return adj[v];
    }
    //计算v 的度数
    public static int degree(Graph G , int v){
        int degree = 0;
        for (Integer integer : G.adj(v)) {
            degree++;
        }
        return degree;
    }
    public static int maxDegree(Graph G){
        int max = 0;
        for (int i = 0; i < G.V(); i++) {
            if (degree(G,i) > max)max = degree(G,i);
        }
        return max;
    }
    //计算所有顶点的平均度数
    public static double avgDegree(Graph G){
        return 2.0*G.E()/ G.V();
    }
    //计算所有自环的个数
    public static int numberOfSelfLoops(Graph G){
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (Integer item : G.adj[v]) {
                if (item == v)count++;
            }
        }
        return count/2;
    }
    public String toString(){
        String s = V + "vertices," + E + "edges\n";
        for (int v = 0; v < V; v++) {
            s += v + ":";
            for (Integer w : adj(v)) {
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }

}
