import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab17Functions {
    @FunctionalInterface interface PriceRule {
        int apply(int cents);
        default String label() { return "rule"; }
    }
    public static void main(String[] args) throws Exception {
        PriceRule discount = cents -> cents - 100;
        System.out.println(discount.apply(1000));
        System.out.println(discount.label());
        Function<Integer, Integer> plusTax = n -> n + 50;
        System.out.println(plusTax.apply(900));
    }
}
