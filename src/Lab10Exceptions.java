import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab10Exceptions {
    static class Resource implements AutoCloseable {
        public void close() throws IOException { throw new IOException("close failed"); }
    }
    public static void main(String[] args) throws Exception {
        try (Resource r = new Resource()) {
            throw new IOException("read failed");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSuppressed()[0].getMessage());
        }
    }
}
