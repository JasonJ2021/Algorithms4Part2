package WordNet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;
    public Outcast(WordNet wordnet){
        if (wordnet == null){
            throw new IllegalArgumentException();
        }
        this.wordNet = wordnet;
    }         // constructor takes a WordNet object
    public String outcast(String[] nouns){
        int id = -1;
        int maxdistance = -1;
        int[] distance = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                distance[i] += wordNet.distance(nouns[i],nouns[j]);
            }
            if (distance[i] > maxdistance){
                id = i;
                maxdistance = distance[i];
            }
        }
        if (id == -1){
            throw new IllegalArgumentException();
        }
        return nouns[id];
    }   // given an array of WordNet nouns, return an outcast
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }  // see test client below
}