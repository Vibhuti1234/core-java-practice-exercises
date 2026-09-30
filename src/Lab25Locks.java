import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab25Locks {

    public static void main(String[] args) throws Exception {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
            try { throw new IllegalStateException("failed"); }
            finally { lock.unlock(); }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(lock.isLocked());
    }
}
