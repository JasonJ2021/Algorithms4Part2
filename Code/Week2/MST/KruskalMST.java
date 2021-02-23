package Week2.MST;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class KruskalMST {
    private Queue<Edge> mst; //用于记录最小生成树的边
    public KruskalMST(EdgeWeightedGraph G){
        mst = new Queue<>();
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge edge : G.edges()) {
            pq.insert(edge);
        }
        UF uf = new UF(G.V());
        while(!pq.isEmpty() && mst.size() < G.V() -1){
            Edge edge = pq.delMin();
            int v = edge.either();
            int w = edge.other(v);
            if (uf.connected(v,w)){
                continue;
            }
            mst.enqueue(edge);
            uf.union(v,w);
        }
    }
    public Iterable<Edge> edges(){
        return mst;
    }
}
