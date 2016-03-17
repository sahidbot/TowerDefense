package game.pathlogic;

import java.util.*;

/**
 * Created by Sahidul on 3/16/2016.
 */
public class Graph<T> {
    private Map<T, LinkedHashSet<T>> map = new HashMap();

    public void addEdge(T node1, T node2) {
        LinkedHashSet<T> adjacent = map.get(node1);
        if(adjacent==null) {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }

    public void addTwoWayVertex(T node1, T node2) {
        addEdge(node1, node2);
        addEdge(node2, node1);
    }

    public boolean isConnected(T node1, T node2) {
        Set adjacent = map.get(node1);
        if(adjacent==null) {
            return false;
        }
        return adjacent.contains(node2);
    }

    public LinkedList<T> adjacentNodes(T last) {
        LinkedHashSet<T> adjacent = map.get(last);
        if(adjacent==null) {
            return new LinkedList();
        }
        return new LinkedList<T>(adjacent);
    }
}
