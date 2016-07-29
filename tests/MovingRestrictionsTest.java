import org.junit.*;

public class MovingRestrictionsTest {
    @Test
    public void emptyMovingRestrictions() throws Exception {
        MovingRestrictions empty = new MovingRestrictions();
        Assert.assertFalse(empty.isRestricted(0, 1, 2));
        Assert.assertFalse(empty.isRestricted(18, 3, 57));
    }

    @Test
    public void selfLoopAlwaysRestricted() throws Exception {
        MovingRestrictions empty = new MovingRestrictions();
        Assert.assertTrue(empty.isRestricted(0, 1, 0));
    }

    @Test
    public void restrictionIsBidirectional() throws Exception {
        MovingRestrictions restrictions = new MovingRestrictions();
        restrictions.add(0, 1, 2);
        Assert.assertTrue(restrictions.isRestricted(0, 1, 2));
        Assert.assertTrue(restrictions.isRestricted(2, 1, 0));
    }
}
