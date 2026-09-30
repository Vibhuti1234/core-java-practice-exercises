import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab01OOP {
    interface Notifier { void send(String message); }
    static class EmailNotifier implements Notifier {
        public void send(String message) { System.out.println("EMAIL: " + message); }
    }
    static class SMSNotifier implements Notifier{
    	@Override
    	public void send(String message) {
    		System.out.println("SMS: "+message);
    	}
    }
    static class AlertService {
        private final Notifier notifier;
        AlertService(Notifier notifier) { this.notifier = Objects.requireNonNull(notifier); }
        void alert(String message) { notifier.send(message); }
    }
    public static void main(String[] args) throws Exception {
        Notifier email = new EmailNotifier();
        AlertService service = new AlertService(email);
        service.alert("payment failed");
        Notifier email2=new SMSNotifier();
        AlertService service2=new AlertService(email2);
        service2.alert("sms sent successfully");
    }
}
