package eu.javaspecialists.performance.managedblocker;

import java.math.*;
import java.util.*;
import java.util.concurrent.*;

// step 1: test100_000_000() time = 43617
// step 2: test100_000_000() time = 22541
// step 3: test100_000_000() time = 15004
// step 4: test100_000_000() time = 9963
// step 5: test100_000_000() time = 7211





// TODO: Sign up to Heinz's Newsletter: www.javaspecialists.eu
public class Fibonacci {
    private final BigInteger RESERVED = BigInteger.valueOf(-1000);

    public BigInteger f(int n) {
        if (n < 0) throw new IllegalArgumentException("n < 0");
        Map<Integer, BigInteger> cache = new ConcurrentHashMap<>();
        cache.put(0, BigInteger.ZERO);
        cache.put(1, BigInteger.ONE);
        return f(n, cache);
    }
    private BigInteger f(int n, Map<Integer, BigInteger> cache) {
        BigInteger result = cache.putIfAbsent(n, RESERVED);
        if (result == null) {
            int half = (n + 1) / 2;
            RecursiveTask<BigInteger> f0_task = new RecursiveTask<BigInteger>() {
                protected BigInteger compute() {
                    return f(half - 1, cache);
                }
            };
            f0_task.fork();
            BigInteger f1 = f(half, cache);
            BigInteger f0 = f0_task.join();

            if (n % 2 == 1) {
                result = f0.multiply(f0).add(f1.multiply(f1));
            } else {
                result = f0.shiftLeft(1).add(f1).multiply(f1);
            }
            synchronized (RESERVED) {
                cache.put(n, result);
                RESERVED.notifyAll();
            }
        } else if (result == RESERVED) {
            try {
                synchronized (RESERVED) {
                    while((result = cache.get(n)) == RESERVED) {
                        RESERVED.wait();
                    }
                }
            } catch (InterruptedException e) {
                throw new CancellationException("interrupted");
            }
        }
        return result;
    }
}
