import java.util.*;

public class MovingRestrictions {
    private Set<Restriction> restrictions = new HashSet<>();

    public boolean isRestricted(int i, int j, int k) {
        if(i == k)
            return true;
        return restrictions.contains(new Restriction(i, j, k));
    }

    public void add(int i, int j, int k) {
        if (i == j)
            return;
        restrictions.add(new Restriction(i, j, k));
    }

    public int size() {
        return restrictions.size();
    }

    private class Restriction {
        private int tipA, tipB;
        private int middle;

        Restriction(int tic, int tac, int toe) {
            tipA = tic;
            middle = tac;
            tipB = toe;
        }

        @Override
        public int hashCode() {
            int result = Math.max(tipA, tipB);
            result = 31 * result + Math.min(tipA, tipB);
            result = 31 * result + middle;
            return result;
        }

        @Override
        public boolean equals(Object _that) {
            if (this == _that) return true;
            if (!(_that instanceof Restriction)) return false;

            Restriction that = (Restriction) _that;
            if (this.middle != that.middle) return false;
            if (this.tipA == that.tipA && this.tipB == that.tipB) return true;
            if (this.tipA == that.tipB && this.tipB == that.tipA) return true;
            return false;
        }

    }
}
