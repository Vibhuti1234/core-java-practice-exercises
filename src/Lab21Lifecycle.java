import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab21Lifecycle {
    static void await(CountDownLatch latch) {
        try { latch.await(); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    public static void main(String[] args) throws Exception {
        CountDownLatch release = new CountDownLatch(1);
        Thread t = new Thread(() -> await(release));
        System.out.println(t.getState());
        t.start();
        System.out.println(t.getState());
        release.countDown();
        t.join();
        System.out.println(t.getState());
    }
}
