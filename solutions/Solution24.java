import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution24 {

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            try {
    while (!Thread.currentThread().isInterrupted()) {
        Thread.sleep(100); // stand-in for interruptible work
    }
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}

        });
        t.start(); t.interrupt(); t.join();
        System.out.println(t.getState());
    }
}
