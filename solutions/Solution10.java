import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution10 {
    static class Resource implements AutoCloseable {
        public void close() throws IOException { throw new IOException("close failed"); }
    }
    public static void main(String[] args) throws Exception {
        try {
            try (Resource r = new Resource()) { throw new IOException("read failed"); }
            catch (IOException e) { throw new UncheckedIOException("import failed", e); }
        } catch (UncheckedIOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getMessage());
            System.out.println(e.getCause().getSuppressed()[0].getMessage());
        }
    }
}
