package game.pathlogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the depth first search for path finding.
 */
public class Search<T> {
    List<LinkedList<T>> paths;

    public List<LinkedList<T>> depthFirst(Graph graph, T start, T end) {
        if (paths == null) {
            paths = new ArrayList<>();
        }
        else {
            paths.clear();
        }

        LinkedList<T> visited = new LinkedList();
        visited.add(start);
        depthFirst(graph, visited, end);

        return paths;
    }

    private void depthFirst(Graph graph, LinkedList<T> visited, T end) {
        LinkedList<T> nodes = graph.adjacentNodes(visited.getLast());
        // examine adjacent nodes
        for (T node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(end)) {
                visited.add(node);
                // add path
                addPath(visited);
                visited.removeLast();
                break;
            }
        }
        for (T node : nodes) {
            if (visited.contains(node) || node.equals(end)) {
                continue;
            }
            visited.addLast(node);
            depthFirst(graph, visited, end);
            visited.removeLast();
        }
    }

    private void addPath(LinkedList<T> visited) {
        LinkedList<T> newPath = new LinkedList();
        for (T node: visited) {
            newPath.add(node);
        }

        paths.add(newPath);
    }
}
