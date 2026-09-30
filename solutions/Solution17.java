import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution17 {

    public static void main(String[] args) throws Exception {
        Predicate<Integer> valid = n -> n > 0;
        Supplier<String> id = () -> "ORDER-1";
        Consumer<String> log = System.out::println;
        log.accept(id.get());
        System.out.println(valid.test(2)); // true
    }
}
