package com.company;

public class Edge implements Comparable<Edge>{
    public int source;
    public int destination;
    public long weight;

    public Edge(int source, int destination, int weight){
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge e) {
        return Long.compare(this.weight, e.weight);
    }
}
