import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution15 {
    static final class Profile {
        private final List<String> roles;
        Profile(List<String> roles) { this.roles = List.copyOf(roles); }
        List<String> roles() { return roles; }
    }
    public static void main(String[] args) throws Exception {
        List<String> input = new ArrayList<>(List.of("USER"));
        Profile p = new Profile(input); input.add("ADMIN");
        System.out.println(p.roles());
        try { p.roles().add("OPS"); }
        catch (UnsupportedOperationException e) { System.out.println("blocked"); }
        System.out.println(p.roles());
    }
}
