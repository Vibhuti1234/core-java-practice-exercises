import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab08Ordering {
    record Job(int id, int priority) implements Comparable<Job> {
        public int compareTo(Job other) {
            int c = Integer.compare(id, other.id);
            return c != 0 ? c : Integer.compare(priority, other.priority);
        }
    }
    public static void main(String[] args) throws Exception {
        List<Job> jobs = new ArrayList<>(List.of(
            new Job(2, 1), new Job(1, 1), new Job(3, 2)));
        Collections.sort(jobs);
        System.out.println(jobs);
        jobs.sort(Comparator.comparingInt(Job::priority)
            .reversed().thenComparingInt(Job::id));
        System.out.println(jobs);
    }
}
