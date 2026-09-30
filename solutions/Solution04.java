import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution04 {

    public static void main(String[] args) throws Exception {
        List<String> events=List.of("A","B","A");
        List<String> editable = new ArrayList<>(events);
        editable.add("C");
        editable.removeIf("A"::equals);
        System.out.println(editable); // [B, C]
    }
}
