import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab09Generics {
    static <T> void copy(List<? extends T> src, List<? super T> dst) {
        for (T value : src) dst.add(value);
    }
    public static void main(String[] args) throws Exception {
        List<Integer> source = List.of(1, 2, 3);
        List<Number> target = new ArrayList<>();
        copy(source, target);
        System.out.println(target);
        // List<Number> wrong = source;
    }
}
