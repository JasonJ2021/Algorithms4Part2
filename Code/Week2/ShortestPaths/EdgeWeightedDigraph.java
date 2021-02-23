package Week2.ShortestPaths;

import Week2.MST.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Bag;

public class EdgeWeightedDigraph {
    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;
    public EdgeWeightedDigraph(int V){
        this.V = V;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        this.E = 0;
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<DirectedEdge>();
        }
    }
    public int V(){
        return V;
    }
    public int E(){
        return E;
    }
    public void addEdge(DirectedEdge e){
        E++;
        adj[e.from()].add(e);
    }
    public Iterable<DirectedEdge> adj(int v){
        return adj[v];
    }
    public Iterable<DirectedEdge> edges(){
        Bag<DirectedEdge> bag = new Bag<>();
        for (int i = 0; i < V; i++) {
            for (DirectedEdge e : adj[i]) {
                bag.add(e);
            }
        }
        return bag;
    }
}
