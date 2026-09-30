import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution25 {

    public static void main(String[] args) throws Exception {
        ReentrantLock lock=new ReentrantLock();
        if (lock.tryLock(200, TimeUnit.MILLISECONDS)) {
            try {
                System.out.println("acquired");
            } finally {
                lock.unlock();
            }
        } else { System.out.println("busy"); }
    }
}
