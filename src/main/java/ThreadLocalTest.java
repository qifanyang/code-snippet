import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangqifan on 2017/8/1.
 */
public class ThreadLocalTest {

    private final int threadLocalHashCode = nextHashCode();

    /**
     * The next hash code to be given out. Updated atomically. Starts at
     * zero.
     */
    private static AtomicInteger nextHashCode =
            new AtomicInteger();

    /**
     * The difference between successively generated hash codes - turns
     * implicit sequential thread-local IDs into near-optimally spread
     * multiplicative hash values for power-of-two-sized tables.
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    public static void main(String[] args) {
//        System.out.println(HASH_INCREMENT&15);
        System.out.println(Integer.toBinaryString(HASH_INCREMENT));

        for(int i = 0; i < 33; i++){
            System.out.println((nextHashCode()&15));
        }

    }
}
