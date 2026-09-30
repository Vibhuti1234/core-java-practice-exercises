import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution29 {

    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> pending = new CompletableFuture<>();
        int result = pending.orTimeout(100, TimeUnit.MILLISECONDS)
            .exceptionally(error -> -1).join(); // -1
        // Non-async continuations may run on a completing thread.
        // Async methods without an executor usually use commonPool.
        // join wraps failures in CompletionException.
        System.out.println(result);
    }
}
