package WordNet;
;
import edu.princeton.cs.algs4.*;

public class SAP {
    private int length;
    private int ancestor;
    private int[] disTo1;
    private int[] disTo2;
    private boolean[] marked1;
    private boolean[] marked2;
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;
    private Digraph graph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if (G == null){
            throw new IllegalArgumentException("Digraph is null");
        }
        disTo1 = new int[G.V()];
        disTo2 = new int[G.V()];
        marked1 = new boolean[G.V()];
        marked2 = new boolean[G.V()];
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        graph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        validateVertex(v);
        validateVertex(w);
        compute(v,w);
        return length;
    }
    private void compute(int v , int w){
        length = -1;
        ancestor = -1;
        disTo1[v] = 0;
        disTo2[w] = 0;
        marked1[v] = true;
        marked2[w] = true;
        stack1.push(v);
        stack2.push(w);
        Queue<Integer> queue1 = new Queue<>();
        Queue<Integer> queue2 = new Queue<>();
        queue1.enqueue(v);
        queue2.enqueue(w);
        bfs(queue1,queue2);
    }
    private void bfs(Queue<Integer> q1,Queue<Integer> q2){
        while(!q1.isEmpty() || !q2.isEmpty()){
            if (!q1.isEmpty()){
                int v = q1.dequeue();
                if (marked2[v]){
                    if (disTo1[v] + disTo2[v] <length || length == -1){
                        ancestor = v;
                        length = disTo1[v] + disTo2[v];
                    }
                }
                if (length == -1 || disTo1[v] <length){
                    for (Integer w : graph.adj(v)) {
                        if (!marked1[w]){
                            marked1[w] = true;
                            q1.enqueue(w);
                            disTo1[w] = disTo1[v] + 1;
                            stack1.push(w);
                        }
                    }
                }
            }
            if (!q2.isEmpty()){
                int v = q2.dequeue();
                if (marked1[v]){
                    if (disTo1[v] + disTo2[v] <length || length == -1){
                        ancestor = v;
                        length = disTo1[v] + disTo2[v];
                    }
                }
                if (length == -1 || disTo2[v] <length){
                    for (Integer w : graph.adj(v)) {
                        if (!marked2[w]){
                            marked2[w] = true;
                            q2.enqueue(w);
                            disTo2[w] = disTo2[v] + 1;
                            stack2.push(w);
                        }
                    }
                }
            }
        }
        init();
    }
    private void compute(Iterable<Integer> v , Iterable<Integer> w){
        length = -1;
        ancestor = -1;
        Queue<Integer> queue1 = new Queue<>();
        Queue<Integer> queue2 = new Queue<>();
        for (Integer i : v) {
            marked1[i] = true;
            disTo1[i] = 0;
            stack1.push(i);
            queue1.enqueue(i);
        }
        for (Integer j : w) {
            marked2[j] = true;
            disTo2[j] = 0;
            stack2.push(j);
            queue2.enqueue(j);
        }
        bfs(queue1 , queue2);
    }
    private void init(){
        while(!stack1.isEmpty()){
            int temp = stack1.pop();
            marked1[temp] = false;
        }
        while(!stack2.isEmpty()){
            int temp = stack2.pop();
            marked2[temp] = false;
        }
    }
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        validateVertex(v);
        validateVertex(w);
        compute(v,w);
        return ancestor;
    }
    private void validateVertex(int v){
        int V = marked1.length;
        if (v<0 || v>=V){
            throw new IllegalArgumentException("v is illegal");
        }
    }
    private void validateVertex(Iterable<Integer> vertixs){
        if (vertixs == null){
            throw new IllegalArgumentException("vertixs is null");
        }
        int V = marked1.length;
        for (Integer v : vertixs) {
            if (v<0 || v>=V){
                throw new IllegalArgumentException(" v is not Illegal");
            }
        }
    }
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        validateVertex(v);
        validateVertex(w);
        compute(v,w);
        return length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        validateVertex(v);
        validateVertex(w);
        compute(v,w);
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
