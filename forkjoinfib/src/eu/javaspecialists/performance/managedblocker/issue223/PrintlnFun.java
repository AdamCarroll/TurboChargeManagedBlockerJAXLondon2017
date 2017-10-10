package eu.javaspecialists.performance.managedblocker.issue223;

import java.util.stream.*;

// TODO: Sign up to Heinz's Newsletter: www.javaspecialists.eu
public class PrintlnFun {
    public static void main(String... args) {
        synchronized (System.out) {
            System.out.println("Hello World");
            IntStream.range(0, 4).parallel().
                forEach(System.out::println);
        }
    }
}
