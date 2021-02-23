package Week2.MST;


import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Edge;
public class LazyPrimMST {
    private boolean[] marked;
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;
    private void visit(EdgeWeightedGraph G , int v){
        marked[v] = true;
        for (Edge edge : G.adj(v)) {
            if (!marked[edge.other(v)]){
                pq.insert(edge);
            }
        }
    }
    public LazyPrimMST(EdgeWeightedGraph G){
        marked = new boolean[G.V()];
        mst = new Queue<>();
        pq = new MinPQ<>();
        visit(G,0);
        while(!pq.isEmpty()){
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]){
                continue;
            }
            mst.enqueue(e);
            if (!marked[v])visit(G,v);
            if (!marked[w])visit(G,w);
        }
    }
    public Iterable<Edge> edges(){
        return mst;
    }
}
