import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution18 {

    public static void main(String[] args) throws Exception {
        List<String> seen=new ArrayList<>();
        BiPredicate<String, Integer> enough =
            (text, threshold) -> text.length() >= threshold;
        System.out.println(enough.test("java", 5)); // false
        Consumer<String> collect = seen::add;
        // Replace the existing collect declaration to avoid a duplicate.
    }
}
