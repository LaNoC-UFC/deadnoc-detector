import java.util.ArrayList;
import java.util.List;

public class Graph {

    private int numberOfVertices = 0;
    private List<List<Integer>> neighbors = new ArrayList<>();

    public Graph() {
        new Graph(0);
    }

    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            neighbors.add(new ArrayList<>());
        }
    }

    public void connectVertices(int tic, int tac) {
        addEdge(tic, tac);
        addEdge(tac, tic);
    }

    public void addEdge(int from, int to) {
        neighbors.get(from).add(to);
    }

    public boolean isCyclic() {
        boolean[] visited = new boolean[numberOfVertices];
        boolean[] stack = new boolean[numberOfVertices];
        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            visited[vertex] = false;
            stack[vertex] = false;
        }
        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            if (isCyclic(vertex, visited, stack))
                return true;
        }
        return false;
    }

    private boolean isCyclic(int from, boolean[] visited, boolean[] stack) {
        if (!visited[from])
        {
            // Mark the current node as visited and part of recursion stack
            visited[from] = true;
            stack[from] = true;
            // Recur for all the vertices adjacent to this vertex
            for (Integer adjacent : neighbors.get(from)) {
                if (!visited[adjacent] && isCyclic(adjacent, visited, stack))
                    return true;
                else if (stack[adjacent])
                    return true;
            }
        }
        stack[from] = false;  // remove the vertex from recursion stack
        return false;
    }
}
