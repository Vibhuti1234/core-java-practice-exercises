import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab19Streams {

    public static void main(String[] args) throws Exception {
        Stream<String> names = List.of("amy", "bob", "amy")
            .stream().filter(s -> s.startsWith("a"))
            .map(String::toUpperCase).distinct();
        System.out.println("pipeline created");
        List<String> result = names.toList();
        System.out.println(result);
        try { names.count(); }
        catch (IllegalStateException e) {
            System.out.println("already consumed");
        }
    }
}
