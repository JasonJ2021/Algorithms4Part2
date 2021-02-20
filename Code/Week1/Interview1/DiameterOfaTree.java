package Week1.Interview1;

import Week1.UndirectedGraph.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;


public class DiameterOfaTree {
    public static int findMax(Graph graph){
        BreadthFirstPaths bfs = new BreadthFirstPaths(graph,0);
        int maxDis = 0;
        for (int i = 0; i < graph.V(); i++) {
            if (maxDis < bfs.distance(i)){
                maxDis = bfs.distance(i);
            }
        }
        return maxDis;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(7);
        graph.addEdge(0,5);
        graph.addEdge(2,4);
        graph.addEdge(2,3);
        graph.addEdge(1,2);
        graph.addEdge(0,1);
        graph.addEdge(3,4);
        graph.addEdge(3,5);
        graph.addEdge(0,2);
        graph.addEdge(3,6);
        BreadthFirstPaths bfs = new BreadthFirstPaths(graph,0);
        int max = findMax(graph);
        System.out.println(max);
    }
}
