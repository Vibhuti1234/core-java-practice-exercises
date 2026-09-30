import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab29Futures {
    static CompletableFuture<Integer> taxed(int price) {
        return CompletableFuture.completedFuture(price + 10);
    }
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture<Integer> price =
                CompletableFuture.supplyAsync(() -> 100, pool);
            CompletableFuture<Integer> shipping =
                CompletableFuture.supplyAsync(() -> 20, pool);
            System.out.println(price.thenCombine(shipping, Integer::sum).join());
            System.out.println(price.thenCompose(Lab29Futures::taxed).join());
            System.out.println(CompletableFuture.<Integer>failedFuture(
                new IllegalStateException("down")).exceptionally(e -> -1).join());
        } finally { pool.shutdown(); }
    }
}
