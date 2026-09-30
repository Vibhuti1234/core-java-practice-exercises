import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab11Checked {
    static void checked() throws IOException { throw new IOException("disk failed"); }
    public static void main(String[] args) throws Exception {
        try {
            checked();
        } catch (IOException e) {
            System.out.println("checked caught");
        }
        try {
            Integer.parseInt("oops");
        } catch (NumberFormatException e) {
            System.out.println("unchecked caught");
        }
    }
}
