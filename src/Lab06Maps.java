import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab06Maps {
    static final class Key {
        private final int id;
        Key(int id) { this.id = id; }
        public int hashCode() { return 1; }
        public boolean equals(Object o) { return o instanceof Key k && id == k.id; }
    }
    public static void main(String[] args) throws Exception {
        Map<Key, String> map = new HashMap<>(4);
        map.put(new Key(1), "first");
        map.put(new Key(2), "second");
        map.put(new Key(1), "updated");
        System.out.println(map.size());
        System.out.println(map.get(new Key(1)));
        System.out.println(map.get(new Key(2)));
    }
}
