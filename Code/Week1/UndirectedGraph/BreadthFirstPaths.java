package Week1.UndirectedGraph;


import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Graph;
public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;
    private int[] dis;
    public BreadthFirstPaths(Graph G , int s){
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dis = new int[G.V()];
        bfs(G , s);
    }
    private void bfs(Graph G , int s){
        Queue<Integer> queue = new Queue<Integer>();
        marked[s] = true;
        queue.enqueue(s);
        while(!queue.isEmpty()){
            int v = queue.dequeue();
            for (Integer w : G.adj(v)) {
                if (!marked[w]){
                    edgeTo[w] = v;
                    marked[w] = true;
                    dis[w] = dis[v] + 1;
                    queue.enqueue(w);
                }
            }
        }
    }
    public int distance(int w){
        return dis[w];
    }
    public boolean hasPathTo(int v){
        return marked[v];
    }
    public Iterable<Integer> pathTo(int v){
        if (!marked[v] )return null;
        Stack<Integer> path = new Stack<>();
        for (int w = v;w !=s;w = edgeTo[w]){
            path.push(w);
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
        BreadthFirstPaths bfs = new BreadthFirstPaths(graph,0);
        boolean b = bfs.hasPathTo(3);
        System.out.println(b);
        for (Integer integer : bfs.pathTo(3)) {
            System.out.println(integer);
        }
        System.out.println(bfs.distance(3));
    }
}
