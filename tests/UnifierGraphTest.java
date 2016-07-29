import org.junit.*;
import java.util.*;

public class UnifierGraphTest {
    @Test
    public void noRestrictionReturnSame() throws Exception {
        Graph noc = simpleRingNoc();
        Graph unified = UnifierGraph.unify(noc, new MovingRestrictions());
        Assert.assertEquals(noc, unified);
    }

    @Test
    public void unlinkedVerticesReturnSame() throws Exception {
        Graph vertices = new Graph(10);
        MovingRestrictions restrictions = new MovingRestrictions();
        restrictions.add(0, 1, 2);
        Graph unified = UnifierGraph.unify(vertices, restrictions);
        Assert.assertEquals(vertices, unified);
    }

    @Test
    public void numberOfElementsOfSimpleCase() throws Exception {
        Graph noc = simpleRingNoc();
        MovingRestrictions restrictions = new MovingRestrictions();
        restrictions.add(0, 1, 2);
        Graph unified = UnifierGraph.unify(noc, restrictions);
        Assert.assertEquals(4 + 4 + 4, unified.vertices().size());
        Assert.assertEquals(10, numberOfEdges(unified));
    }

    @Test
    public void originalLinksOfSimpleCase() throws Exception {
        Graph noc = simpleRingNoc();
        MovingRestrictions restrictions = new MovingRestrictions();
        restrictions.add(0, 1, 2);
        Map<Integer, List<Integer>> verticesMatch = new HashMap<>();
        Graph unified = UnifierGraph.unify(noc, restrictions, verticesMatch);
        for (Integer src : noc.vertices()) {
            for (Integer dst : noc.neighborsOf(src)) {
                Assert.assertTrue(hasLink(verticesMatch.get(src), verticesMatch.get(dst), unified));
            }
        }
    }

    @Test
    public void restrictionsOfSimpleCase() throws Exception {
        Graph noc = simpleRingNoc();
        MovingRestrictions restrictions = new MovingRestrictions();
        restrictions.add(0, 1, 2);
        Map<Integer, List<Integer>> verticesMatch = new HashMap<>();
        Graph unified = UnifierGraph.unify(noc, restrictions, verticesMatch);
        Assert.assertFalse(hasConnection(0, 1, 2, verticesMatch, unified));
        Assert.assertFalse(hasConnection(2, 1, 0, verticesMatch, unified));
        Assert.assertTrue(hasConnection(1, 2, 0, verticesMatch, unified));
        Assert.assertTrue(hasConnection(0, 2, 1, verticesMatch, unified));
    }

    private boolean hasConnection(int src, int by, int dst, Map<Integer, List<Integer>> verticesMatch, Graph unified) {
        List<Integer> from = verticesGoingIn(verticesMatch.get(by), verticesMatch.get(src), unified);
        List<Integer> to = verticesGoingOut(verticesMatch.get(dst), verticesMatch.get(by), unified);
        return hasLink(from, to, unified);
    }

    private List<Integer> verticesGoingIn(List<Integer> at, List<Integer> from, Graph in) {
        List<Integer> result = new ArrayList<>();
        for (Integer src : from) {
            for (Integer dst : in.neighborsOf(src)) {
                if (at.contains(dst))
                    result.add(dst);
            }
        }
        return result;
    }

    private List<Integer> verticesGoingOut(List<Integer> to, List<Integer> from, Graph in) {
        List<Integer> result = new ArrayList<>();
        for (Integer src : from) {
            Collection<Integer> neighbors = in.neighborsOf(src);
            for (Integer dst : neighbors) {
                if (to.contains(dst)) {
                    result.add(src);
                    break;
                }
            }
        }
        return result;
    }

    private boolean hasLink(List<Integer> from, List<Integer> to, Graph in) {
        for (Integer src : from) {
            for (Integer dst : in.neighborsOf(src))
                if(to.contains(dst))
                    return true;
        }
        return false;
    }

    private int numberOfEdges(Graph g) {
        int result = 0;
        for (Integer vertex : g.vertices())
            result += g.neighborsOf(vertex).size();
        return result;
    }

    private Graph simpleRingNoc() {
        Graph noc = new Graph(3);
        noc.connectVertices(0, 1);
        noc.connectVertices(1, 2);
        noc.connectVertices(2, 0);
        return noc;
    }
}
