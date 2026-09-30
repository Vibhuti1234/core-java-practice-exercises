import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution21 {

    public static void main(String[] args) throws Exception {
        Object monitor = new Object();
        Thread blocked = new Thread(() -> { synchronized(monitor) { } });
        synchronized(monitor) {
            blocked.start();
            long deadline=System.nanoTime()+TimeUnit.SECONDS.toNanos(2);
            while (blocked.getState()!=Thread.State.BLOCKED && System.nanoTime()<deadline) Thread.yield();
            System.out.println("monitor contender: " + blocked.getState());
        }
        blocked.join();
        Thread sleeping = new Thread(() -> {
            try { Thread.sleep(10_000); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        sleeping.start();
        long deadline=System.nanoTime()+TimeUnit.SECONDS.toNanos(2);
        while (sleeping.getState()!=Thread.State.TIMED_WAITING && System.nanoTime()<deadline) Thread.yield();
        System.out.println("sleeper: " + sleeping.getState());
        sleeping.interrupt(); sleeping.join();
        System.out.println(Arrays.toString(Thread.State.values()));
    }
}
