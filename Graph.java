package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    public int size;
    public ArrayList<Edge> edges;
    public Node[] nodes;
    public LinkedList<Integer>[] adj;
    public long maxWeight = 0;

    public Graph(int size){
        this.size = size;
        edges = new ArrayList<>(size);
        nodes = new Node[size+1];
        for(int i = 1; i < size+1;i++){
            nodes[i] = new Node(i);
        }
        nodes[1].rank = 1;
        adj = new LinkedList[size+1];
        for(int i = 1; i < size+1; i++){
            adj[i] = new LinkedList<>();
        }
    }

    public void insertEdge(Edge e){
        adj[e.source].add(e.destination);
        adj[e.destination].add(e.source);
        edges.add(e);
        if(e.weight > maxWeight) this.maxWeight = e.weight;
    }

    public void utilityRankTree(int v, boolean[] ranked){
        ranked[v] = true;
        for (int n : adj[v]) {
            if (!ranked[n]) {
                nodes[n].rank = nodes[v].rank + 1;
                utilityRankTree(n, ranked);
            }
        }
    }

    public void rankTree(){
        boolean[] ranked = new boolean[size+1];
        utilityRankTree(1,ranked);
    }

    public Node climbTreeUntilRank(Node a, int rank){
        if(a.rank <= rank) return a;
        for (Integer integer : adj[a.identifier]) {
            Node b = nodes[integer];
            if (b.rank < a.rank) return climbTreeUntilRank(b, rank);
        }
        return a;
    }

    public boolean isConnectedAfterRemoval(Edge q, Edge rem){
        Node sourceRem = nodes[rem.source];
        Node destinationRem = nodes[rem.destination];
        Node cutOffRem = sourceRem.rank < destinationRem.rank ? destinationRem : sourceRem;
        Node querySourceGoTo = climbTreeUntilRank(nodes[q.source],cutOffRem.rank);
        Node queryDestGoTo = climbTreeUntilRank(nodes[q.destination],cutOffRem.rank);
        return(querySourceGoTo.identifier == cutOffRem.identifier) ^ (queryDestGoTo.identifier == cutOffRem.identifier);
    }

    public void remove(Edge remove){
        adj[remove.source].remove((Integer) remove.destination);
        adj[remove.destination].remove((Integer) remove.source);
    }

    public void undoRemove(Edge remove){
        adj[remove.source].add(remove.destination);
        adj[remove.destination].add(remove.source);
    }

    public boolean queryMeetsConditions(Edge q){
        if (q.weight >= maxWeight){
            return false;
        }
        for(int i = 0; i < size-1; i++){
            if(q.weight >= edges.get(i).weight){
                continue;
            }
            Edge dummy = edges.get(i);
            remove(dummy);
            if(isConnectedAfterRemoval(q,dummy)){
                undoRemove(dummy);
                return true;
            }
            undoRemove(dummy);
        }
        return false;
    }
}
