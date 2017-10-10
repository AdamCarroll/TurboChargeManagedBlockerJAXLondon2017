package eu.javaspecialists.performance.managedblocker;

import java.math.*;

// step 1: test100_000_000() time = 43617

// TODO: Sign up to Heinz's Newsletter: www.javaspecialists.eu
public class Fibonacci {
    public BigInteger f(int n) {
        if (n < 0) throw new IllegalArgumentException("n < 0");
        if (n == 0) {
            return BigInteger.ZERO;
        } else if (n == 1) {
            return BigInteger.ONE;
        }

        int half = (n + 1) / 2;
        BigInteger f0 = f(half-1);
        BigInteger f1 = f(half);

        if (n % 2 == 1) {
            return f0.multiply(f0).add(f1.multiply(f1));
        } else {
            return f0.shiftLeft(1).add(f1).multiply(f1);
        }
    }
}
