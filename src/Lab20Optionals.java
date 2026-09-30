import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab20Optionals {
    static String fallback() {
        System.out.println("fallback called");
        return "guest";
    }
    public static void main(String[] args) throws Exception {
        Optional<String> name = Optional.of("Mira");
        System.out.println(name.orElse(fallback()));
        System.out.println(name.orElseGet(() -> fallback()));
        System.out.println(Optional.ofNullable(null).isEmpty());
    }
}
