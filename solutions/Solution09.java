import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution09 {
    static <T> void copy(List<? extends T> src, List<? super T> dst) {
        for (T value : src) dst.add(value);
    }
    public static void main(String[] args) throws Exception {
        List<Double> src=List.of(1.5,2.5); List<Object> dst=new ArrayList<>();
        copy(src,dst); System.out.println(dst);
    }
}
