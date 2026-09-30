import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution26 {
    static boolean reserve(AtomicInteger stock) {
        while (true) {
            int current = stock.get();
            if (current <= 0) return false;
            if (stock.compareAndSet(current, current - 1)) return true;
        }
    }
    public static void main(String[] args) throws Exception {
        AtomicInteger stock = new AtomicInteger(1);
        AtomicInteger sold = new AtomicInteger();
        Runnable buyer=() -> { if (reserve(stock)) sold.incrementAndGet(); };
        Thread a=new Thread(buyer), b=new Thread(buyer);
        a.start(); b.start(); a.join(); b.join();
        System.out.println("sold="+sold.get()+", stock="+stock.get());
    }
}
