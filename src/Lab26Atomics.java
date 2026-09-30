import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab26Atomics {
    static boolean reserve(AtomicInteger stock) {
        while (true) {
            int current = stock.get();
            if (current <= 0) return false;
            if (stock.compareAndSet(current, current - 1)) return true;
        }
    }
    public static void main(String[] args) throws Exception {
        AtomicInteger stock = new AtomicInteger(1);
        System.out.println(reserve(stock));
        System.out.println(reserve(stock));
        System.out.println(stock.get());
    }
}
