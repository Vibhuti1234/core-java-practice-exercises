import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab27ConcurrentMap {

    public static void main(String[] args) throws Exception {
        ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<>();
        Runnable task = () -> {
            for (int i = 0; i < 10_000; i++) {
                counts.merge("orders", 1, Integer::sum);
            }
        };
        Thread a = new Thread(task), b = new Thread(task);
        a.start(); b.start();
        a.join(); b.join();
        System.out.println(counts.get("orders"));
    }
}
