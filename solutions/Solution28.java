import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution28 {

    public static void main(String[] args) throws Exception {
        ExecutorService pool=Executors.newFixedThreadPool(2);
        try { System.out.println(pool.submit(() -> 42).get()); }
        finally {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(2, TimeUnit.SECONDS))
                pool.shutdownNow();
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        }
        System.out.println("terminated="+pool.isTerminated());
    }
}
