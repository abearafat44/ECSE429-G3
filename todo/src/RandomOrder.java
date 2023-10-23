import org.junit.runner.manipulation.Ordering;

import java.util.Random;

public class RandomOrder implements Ordering.Factory {
    @Override
    public Ordering create(Ordering.Context context) {
        long seed = new Random().nextLong();
        System.out.println("RandomOrder: seed = " + seed);
        return Ordering.shuffledBy(new Random(seed));
    }
}
