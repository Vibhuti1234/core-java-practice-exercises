import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab24Volatile {
    static class Worker implements Runnable {
        volatile boolean stop;
        public void run() { while (!stop) Thread.onSpinWait(); }
    }
    public static void main(String[] args) throws Exception {
        Worker w = new Worker();
        Thread t = new Thread(w);
        t.setDaemon(true);
        t.start();
        w.stop = true;
        t.join(1000);
        System.out.println("alive=" + t.isAlive());
    }
}
