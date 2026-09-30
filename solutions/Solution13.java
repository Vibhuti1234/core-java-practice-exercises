import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution13 {
    static class Box { int value; }
    static Box replacement(Box box) {
        box.value = 2;
        Box next = new Box();
        next.value = 3;
        return next;
    }

    public static void main(String[] args) throws Exception {
        Box box=new Box(); box.value=1; box=replacement(box); System.out.println(box.value);
    }
}
