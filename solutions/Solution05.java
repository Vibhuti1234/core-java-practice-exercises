import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution05 {

    public static void main(String[] args) throws Exception {
        List<Integer> b=new LinkedList<>(List.of(2,9,3));
        long sum = 0;
        for (int n : b) sum += n;
        System.out.println(sum); // 14
        // LinkedList iterator insertion is O(1) after positioning.
        // ArrayList middle insertion shifts elements: O(n).
    }
}
