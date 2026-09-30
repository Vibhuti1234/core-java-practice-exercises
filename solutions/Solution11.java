import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution11 {
    static void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("negative quantity");
        }
    }

    public static void main(String[] args) throws Exception {
        try { validateQuantity(-1); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }
}
