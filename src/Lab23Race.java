import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab23Race {
    static class Counter { int value; }
    static void rendezvous(CyclicBarrier b) {
        try { b.await(); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); throw new RuntimeException(e); }
        catch (BrokenBarrierException e) { throw new RuntimeException(e); }
    }
    public static void main(String[] args) throws Exception {
        Counter c = new Counter();
        CyclicBarrier bothRead = new CyclicBarrier(2);
        Runnable task = () -> {
            int before = c.value;
            rendezvous(bothRead);
            c.value = before + 1;
        };
        Thread a = new Thread(task), b = new Thread(task);
        a.start(); b.start();
        a.join(); b.join();
        System.out.println(c.value);
    }
}
