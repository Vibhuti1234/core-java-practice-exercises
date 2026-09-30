# Capstone: concurrent inventory reservation

## Challenge

Build a plain Java InventoryService. Use an immutable Product record with an ID and a copied list of tags. Index inventory by product ID. Return Optional<Product> for lookups. Reject zero or negative requested quantities. Implement `reserve(String id, int quantity)` so stock never becomes negative. Use an ExecutorService to submit 100 concurrent reservations against 25 items in stock. Each task reserves one item. Wait for every result and count successes. Shut down the executor.

## Acceptance checks

- Exactly 25 reservations succeed and the final stock is 0.
- Looking up an unknown ID returns Optional.empty().
- A negative quantity throws IllegalArgumentException.
- Mutating the original tag list does not alter Product.
- A failed worker task remains visible to the caller through Future.get().

## Worked design

Product data is immutable and stored in a ConcurrentHashMap. Inventory counts use AtomicInteger. `reserve` uses a compare-and-set retry loop: read stock, reject if too low, attempt CAS to stock minus quantity, retry on interference. For this exercise products are never removed or replaced while reservations run. That restriction keeps the Product and stock maps aligned. A real service would need a defined lifecycle and persistence model.

```java
boolean reserve(AtomicInteger stock, int quantity) {
    if (quantity <= 0) throw new IllegalArgumentException("quantity must be positive");
    while (true) {
        int current = stock.get();
        if (current < quantity) return false;
        if (stock.compareAndSet(current, current - quantity)) return true;
    }
}
```

See `solutions/InventoryCapstone.java` for a complete runnable implementation with acceptance checks. No external database or framework is needed.

## Interview explanation

An atomic operation preserves each product's stock invariant. ConcurrentHashMap safely publishes product entries but does not make a read-check-write sequence on stock atomic. Returning immutable product data prevents callers from changing service state. Futures carry task failures to the caller. The solution handles a single process only; distributed inventory requires an additional consistency design.
