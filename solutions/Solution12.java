import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution12 {
    static class Box { int value; volatile boolean ready; }
    public static void main(String[] args) throws Exception {
        Box box = new Box();
        Thread writer = new Thread(() -> { box.value=42; box.ready=true; });
        writer.start();
        while (!box.ready) Thread.onSpinWait();
        System.out.println(box.value);
        writer.join();
    }
}
