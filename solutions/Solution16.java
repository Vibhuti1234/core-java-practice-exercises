import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution16 {
    record Course(String name, List<String> tags) {
        Course {
            Objects.requireNonNull(name);
            tags = List.copyOf(tags);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> tags = new ArrayList<>(List.of("java"));
        Course a = new Course("backend", tags);
        Course b = new Course("backend", List.of("java"));
        System.out.println(a.equals(b));
        tags.add("sql");
        System.out.println(a.tags());
        System.out.println(a.equals(b));
    }
}
