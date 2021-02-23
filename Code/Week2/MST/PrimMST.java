package Week2.MST;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.IndexMinPQ;

public class PrimMST {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;
    public PrimMST(EdgeWeightedGraph G){
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<>(G.V());
        for (int i = 0; i < distTo.length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[0] = 0;
        pq.insert(0,0.0);
        while(!pq.isEmpty()){
            visit(G,pq.delMin());
        }
    }
    private void visit(EdgeWeightedGraph G , int v){
        marked[v] = true;
        for (Edge edge : G.adj(v)) {
            int w = edge.other(v);
            if (marked[w])continue;
            if (edge.weight() < distTo[w]){
                edgeTo[w] = edge;
                distTo[w] = edge.weight();
                if (pq.contains(w)){
                    pq.change(w,distTo[w]);
                }else{
                    pq.insert(w,distTo[w]);
                }
            }
        }
    }
    public Iterable<Edge> edges(){
        Bag<Edge> bag = new Bag<>();
        for (int i = 1; i < distTo.length; i++) {
            bag.add(edgeTo[i]);
        }
        return bag;
    }
}
