import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab22Synchronization {
    static class Counter {
        int value;
        synchronized void increment() { value++; }
    }
    static void repeat(Counter c) { for (int i = 0; i < 100_000; i++) c.increment(); }
    public static void main(String[] args) throws Exception {
        Counter c = new Counter();
        Thread a = new Thread(() -> repeat(c));
        Thread b = new Thread(() -> repeat(c));
        a.start(); b.start();
        a.join(); b.join();
        System.out.println(c.value);
    }
}
