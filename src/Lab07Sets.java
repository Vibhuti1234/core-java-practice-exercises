import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab07Sets {
    static final class Key {
        int id;
        Key(int id) { this.id = id; }
        public int hashCode() { return id; }
        public boolean equals(Object o) { return o instanceof Key k && id == k.id; }
    }
    public static void main(String[] args) throws Exception {
        Key key = new Key(1);
        Set<Key> set = new HashSet<>();
        set.add(key);
        key.id = 2;
        System.out.println(set.contains(key));
        System.out.println(set.size());
    }
}
