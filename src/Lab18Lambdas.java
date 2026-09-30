import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab18Lambdas {

    public static void main(String[] args) throws Exception {
        int minimum = 3;
        Predicate<String> longEnough = s -> s.length() >= minimum;
        System.out.println(longEnough.test("java"));
        // minimum++;
        List<String> seen = new ArrayList<>();
        Consumer<String> collect = s -> seen.add(s);
        collect.accept("java");
        System.out.println(seen);
    }
}
