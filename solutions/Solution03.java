import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution03 {

    public static void main(String[] args) throws Exception {
        String a="java";
        a = a.concat("21"); // a now refers to "java21"
        StringBuilder sb = new StringBuilder("java");
        sb.append(21);
        System.out.println(sb.toString());
    }
}
