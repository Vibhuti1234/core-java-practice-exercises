import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab12MemoryModel {
    static class Box { int value; }
    public static void main(String[] args) throws Exception {
        Box box = new Box();
        Thread writer = new Thread(() -> box.value = 42);
        writer.start();
        writer.join();
        System.out.println(box.value);
    }
}
