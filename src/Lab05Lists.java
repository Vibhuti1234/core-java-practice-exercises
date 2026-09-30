import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab05Lists {

    public static void main(String[] args) throws Exception {
        List<Integer> a = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> b = new LinkedList<>(a);
        a.remove(1);
        b.remove(Integer.valueOf(1));
        System.out.println(a);
        System.out.println(b);
        ListIterator<Integer> it = b.listIterator();
        it.next();
        it.add(9);
        System.out.println(b);
    }
}
