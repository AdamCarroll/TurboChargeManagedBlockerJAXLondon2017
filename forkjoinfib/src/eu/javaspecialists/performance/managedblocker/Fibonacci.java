package eu.javaspecialists.performance.managedblocker;

import java.math.*;

// TODO: Sign up to Heinz's Newsletter: www.javaspecialists.eu
public class Fibonacci {
    public BigInteger f(int n) {
        if (n < 0) throw new IllegalArgumentException("n < 0");
        switch (n) {
            case 0: return BigInteger.ZERO;
            case 1: return BigInteger.ONE;
            default: return f(n - 1).add(f(n - 2));
        }
    }
}
