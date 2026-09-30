import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab28Executors {

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            Future<Integer> ok = pool.submit(() -> 21 * 2);
            Future<Integer> bad = pool.submit(() -> {
                throw new IllegalStateException("boom");
            });
            System.out.println(ok.get());
            try { bad.get(); }
            catch (ExecutionException e) {
                System.out.println(e.getCause().getMessage());
            }
        } finally { pool.shutdown(); }
    }
}
