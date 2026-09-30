import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution02 {
    static final class User { private final int id; User(int id) { this.id=id; }
    @Override public boolean equals(Object o) {
        return o instanceof User u && id == u.id;
    }
    @Override public int hashCode() {
        return Integer.hashCode(id);
    }
    }
    public static void main(String[] args) throws Exception {
        User a = new User(7);
        User b = new User(7);
        Set<User> users = new HashSet<>();
        users.add(a);
        users.add(b);
        System.out.println(a.equals(b));
        System.out.println(users.size());
    }
}
