import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution19 {

    public static void main(String[] args) throws Exception {
        List<String> mutable = List.of("amy", "bob").stream()
            .map(String::toUpperCase)
            .collect(Collectors.toCollection(ArrayList::new));
        Map<String, Long> counts = List.of("amy", "bob", "amy")
            .stream().collect(Collectors.groupingBy(
                Function.identity(), TreeMap::new, Collectors.counting()));
        // {amy=2, bob=1}; flatMap flattens each mapped stream.
        mutable.add("ANN"); System.out.println(mutable); System.out.println(counts);
    }
}
