package game.pathlogic;

import java.util.*;

/**
 * Created by Sahidul on 3/16/2016.
 */
public class Graph<T> {
    private Map<T, LinkedHashSet<T>> map = new HashMap();

    /**
     * Adds an edge between both nodes. This connection is unidirectional
     *
     * @param node1 Source node
     * @param node2 Destination node
     */
    public void addEdge(T node1, T node2) {
        LinkedHashSet<T> adjacent = map.get(node1);
        if(adjacent==null) {
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);
    }

    /**
     * Connects two nodes together. This connection is bidirectional
     * @param node1 Node to connect
     * @param node2 Second node to connect
     */
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

    /**
     * Returns the nodes that are connected to the node
     * @param last Node to check for
     * @return The nodes connected to last
     */
    public LinkedList<T> adjacentNodes(T last) {
        LinkedHashSet<T> adjacent = map.get(last);
        if(adjacent==null) {
            return new LinkedList();
        }
        return new LinkedList<T>(adjacent);
    }
}
