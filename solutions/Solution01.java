import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
public class Solution01 {
    interface Notifier { void send(String message); }
    static class EmailNotifier implements Notifier {
        public void send(String message) { System.out.println("EMAIL: " + message); }
    }
    static class AlertService {
        private final Notifier notifier;
        AlertService(Notifier notifier) { this.notifier = Objects.requireNonNull(notifier); }
        void alert(String message) { notifier.send(message); }
    }
    static class SmsNotifier implements Notifier {
        public void send(String message) {
            System.out.println("SMS: " + message);
        }
    }

    public static void main(String[] args) throws Exception {
        new AlertService(new SmsNotifier()).alert("payment failed");
    }
}
