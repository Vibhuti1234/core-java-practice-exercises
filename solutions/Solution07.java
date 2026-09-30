import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution07 {

    public static void main(String[] args) throws Exception {
        record StableKey(int id) {}
        Set<StableKey> stable = new HashSet<>();
        stable.add(new StableKey(1));
        System.out.println(stable.contains(new StableKey(1))); // true
    }
}
