import org.junit.*;

public class GraphTest {
    @Test
    public void emptyGraphIsAcyclic() throws Exception {
        Graph emptyGraph = new Graph();
        Assert.assertFalse(emptyGraph.isCyclic());
    }

    @Test
    public void unlinkedGraphIsAcyclic() throws Exception {
        Graph unlinkedGraph = new Graph(10);
        Assert.assertFalse(unlinkedGraph.isCyclic());
    }

    @Test
    public void linearGraphIsAcyclic() throws Exception {
        Graph linearGraph = new Graph(3);
        linearGraph.addEdge(0, 1);
        linearGraph.addEdge(1, 2);
        Assert.assertFalse(linearGraph.isCyclic());
    }

    @Test
    public void connectedPairIsCyclic() throws Exception {
        Graph connectedPair = new Graph(2);
        connectedPair.connectVertices(0, 1);
        Assert.assertTrue(connectedPair.isCyclic());
    }

    @Test
    public void ringIsCyclic() throws Exception {
        Graph ring = new Graph(4);
        ring.addEdge(0, 1);
        ring.addEdge(1, 2);
        ring.addEdge(2, 3);
        ring.addEdge(3, 0);
        Assert.assertTrue(ring.isCyclic());
    }
}
