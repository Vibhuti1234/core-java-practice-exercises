import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution20 {

    public static void main(String[] args) throws Exception {
        String raw = "  ";
        String value = Optional.ofNullable(raw)
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .orElse("anonymous");
        System.out.println(value); // anonymous
        // Optional.of(null) throws NullPointerException.
    }
}
