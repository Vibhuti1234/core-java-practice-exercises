import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab14GC {

    public static void main(String[] args) throws Exception {
        List<byte[]> retained = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            retained.add(new byte[1024 * 1024]);
        }
        System.out.println(retained.size());
        retained.clear();
        System.gc();
        System.out.println(retained.size());
    }
}
