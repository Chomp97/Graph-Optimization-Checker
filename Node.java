package com.company;

public class Node implements Comparable<Node> {
    public int identifier;
    public int rank;
    public Node(int identifier){
        this.identifier = identifier;
    }

    public int compareTo(Node n) {
        return this.rank - n.rank;
    }
}
