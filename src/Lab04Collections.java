import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab04Collections {

    public static void main(String[] args) throws Exception {
        List<String> events = List.of("A", "B", "A");
        Set<String> unique = new LinkedHashSet<>(events);
        Deque<String> queue = new ArrayDeque<>(events);
        Map<String, Integer> counts = new TreeMap<>();
        for (String e : events) counts.merge(e, 1, Integer::sum);
        System.out.println(events);
        System.out.println(unique);
        System.out.println(queue.removeFirst());
        System.out.println(counts);
    }
}
