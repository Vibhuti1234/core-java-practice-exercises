import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution23 {

    public static void main(String[] args) throws Exception {
        AtomicInteger count = new AtomicInteger();
        Runnable task = () -> count.incrementAndGet();
        Thread a=new Thread(task), b=new Thread(task);
        a.start(); b.start(); a.join(); b.join();
        System.out.println(count.get());
    }
}
