import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab13StackHeap {
    static class Box { int value; }
    static void change(Box box) {
        box.value = 2;
        box = new Box();
        box.value = 3;
    }
    public static void main(String[] args) throws Exception {
        Box box = new Box();
        box.value = 1;
        change(box);
        System.out.println(box.value);
    }
}
