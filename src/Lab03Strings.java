import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab03Strings {

    public static void main(String[] args) throws Exception {
        String a = "java";
        String b = "ja" + "va";
        String c = new String("java");
        a.concat("21");
        System.out.println(a == b);//true
        System.out.println(a == c);//false
        System.out.println(a.equals(c));//true
        System.out.println(a);//java
        System.out.println(a == c.intern());//false
    }
}
