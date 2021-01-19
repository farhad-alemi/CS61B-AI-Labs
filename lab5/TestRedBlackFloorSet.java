import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
       RedBlackFloorSet rbs = new RedBlackFloorSet();
       AListFloorSet alf = new AListFloorSet();

       for (int i = 0; i < 1000000; ++i) {
           double r = StdRandom.uniform(-5000.0, 5000.0);
           rbs.add(r);
           alf.add(r);
       }

       for (int i = 0; i < 1000000; i++) {
           double r = StdRandom.uniform(-5000.0, 5000.0);
           double x = rbs.floor(r);
           double y = alf.floor(r);

//           assertEquals(x, y, 0.00001);
       }
    }
}
