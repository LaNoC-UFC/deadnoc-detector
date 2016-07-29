import java.util.*;

public class UnifierGraph {

    public static Graph unify(Graph noc, MovingRestrictions movingRestrictions) {
        return unify(noc, movingRestrictions, null);
    }

    public static Graph unify(Graph noc, MovingRestrictions movingRestrictions,  Map<Integer, List<Integer>> verticesMatch) {
        if (0 == movingRestrictions.size() || 0 == numberOfVertices(noc))
            return noc;
        Graph result = new Graph();
        if(null == verticesMatch)
            verticesMatch = new HashMap<>();
        Map<VerticesPair, VerticesPair> pairsMatch = addOriginalEdgesTo(result, noc, verticesMatch);
        addInternalEdgesTo(result, movingRestrictions, noc, pairsMatch);
        return result;
    }

    private static void addInternalEdgesTo(Graph target, MovingRestrictions restrictions, Graph noc, Map<VerticesPair, VerticesPair> pairsMatch) {
        for(Integer src : noc.vertices()) {
            for (Integer through : noc.neighborsOf(src)) {
                for (Integer dst : noc.neighborsOf(through)) {
                    if(!restrictions.isRestricted(src, through, dst)) {
                        target.addEdge(pairsMatch.get(new VerticesPair(src, through)).dst, pairsMatch.get(new VerticesPair(through, dst)).src);
                    }
                }
            }
        }
    }

    private static int numberOfVertices(Graph noc) {
        int result = 0;
        for(Integer vertex : noc.vertices())
            result += 2 * noc.neighborsOf(vertex).size();
        return result;
    }

    private static Map<VerticesPair, VerticesPair> addOriginalEdgesTo(Graph target, Graph source, Map<Integer, List<Integer>> verticesMatch) {
        Map<VerticesPair, VerticesPair> pairsMatch = new HashMap<>();
        for (Integer src : source.vertices()) {
            for (Integer dst : source.neighborsOf(src)) {
                Integer newSrc = target.newVertex();
                addMatchToMap(verticesMatch, src, newSrc);
                Integer newDst = target.newVertex();
                addMatchToMap(verticesMatch, dst, newDst);
                target.addEdge(newSrc, newDst);
                pairsMatch.put(new VerticesPair(src, dst), new VerticesPair(newSrc, newDst));
            }
        }
        return pairsMatch;
    }

    private static void addMatchToMap(Map<Integer, List<Integer>> verticesMatch, Integer from, Integer to) {
        if(!verticesMatch.containsKey(from))
            verticesMatch.put(from, new ArrayList<>());
        verticesMatch.get(from).add(to);
    }

    private static class VerticesPair {
        final Integer src;
        final Integer dst;

        VerticesPair(Integer src, Integer dst) {
            this.src = src;
            this.dst = dst;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof VerticesPair)) return false;

            VerticesPair that = (VerticesPair) o;

            if (src != null ? !src.equals(that.src) : that.src != null) return false;
            return dst != null ? dst.equals(that.dst) : that.dst == null;

        }

        @Override
        public int hashCode() {
            int result = src != null ? src.hashCode() : 0;
            result = 31 * result + (dst != null ? dst.hashCode() : 0);
            return result;
        }
    }
}
