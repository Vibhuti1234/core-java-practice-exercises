import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class InventoryCapstone {
    record Product(String id, List<String> tags) {
        Product { Objects.requireNonNull(id); tags = List.copyOf(tags); }
    }
    static final class InventoryService {
        private final Map<String, Product> products = new ConcurrentHashMap<>();
        private final Map<String, AtomicInteger> stocks = new ConcurrentHashMap<>();
        // Setup only: this exercise does not allow catalog changes during reservations.
        void add(Product product, int quantity) {
            if (quantity < 0) throw new IllegalArgumentException("negative stock");
            products.put(product.id(), product);
            stocks.put(product.id(), new AtomicInteger(quantity));
        }
        Optional<Product> find(String id) { return Optional.ofNullable(products.get(id)); }
        int remaining(String id) { return stocks.get(id).get(); }
        boolean reserve(String id, int quantity) {
            if (quantity <= 0) throw new IllegalArgumentException("quantity must be positive");
            AtomicInteger stock = stocks.get(id);
            if (stock == null) return false;
            while (true) {
                int current = stock.get();
                if (current < quantity) return false;
                if (stock.compareAndSet(current, current - quantity)) return true;
            }
        }
    }
    static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
    public static void main(String[] args) throws Exception {
        InventoryService service = new InventoryService();
        List<String> input = new ArrayList<>(List.of("book"));
        Product product = new Product("JAVA", input);
        service.add(product, 25);
        input.add("changed");
        check(product.tags().equals(List.of("book")), "defensive copy");
        check(service.find("missing").isEmpty(), "unknown product");
        try {
            service.reserve("JAVA", -1);
            throw new AssertionError("negative quantity accepted");
        } catch (IllegalArgumentException expected) { }
        ExecutorService pool = Executors.newFixedThreadPool(8);
        try {
            List<Future<Boolean>> results = new ArrayList<>();
            for (int i = 0; i < 100; i++) results.add(pool.submit(() -> service.reserve("JAVA", 1)));
            int successes = 0;
            for (Future<Boolean> result : results) if (result.get()) successes++;
            check(successes == 25, "wrong success count");
            check(service.remaining("JAVA") == 0, "stock invariant");
            Future<?> failure = pool.submit(() -> { throw new IllegalStateException("worker failed"); });
            try {
                failure.get();
                throw new AssertionError("failure not surfaced");
            } catch (ExecutionException expected) {
                check(expected.getCause() instanceof IllegalStateException, "wrong cause");
            }
            System.out.println("25 reservations succeeded; stock=0; all checks passed");
        } finally {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(2, TimeUnit.SECONDS)) pool.shutdownNow();
            } catch (InterruptedException e) {
                pool.shutdownNow(); Thread.currentThread().interrupt();
            }
        }
    }
}
