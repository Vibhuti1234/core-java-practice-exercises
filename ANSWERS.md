# Answers and further reading

Try each lab before opening its solution. Slide code is an excerpt. Complete programs are in `src/` and `solutions/`.

## 01. OOP: replace a dependency

**File:** src/Lab01OOP.java

**Challenge:** Which implementation runs? Add an SMS notifier without editing AlertService. Where do you see the four OOP ideas?

**Expected observation:** EMAIL: payment failed

**Why:** The interface supplies abstraction and a subtype contract. EmailNotifier implements it, and runtime dispatch chooses send(). AlertService encapsulates a dependency and uses composition. Class inheritance would use extends.

**Change:** Implement SmsNotifier, then pass new SmsNotifier() to AlertService.

```java
static class SmsNotifier implements Notifier {
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}
// new AlertService(new SmsNotifier()).alert("payment failed");
```



Complete example: `solutions/Solution01.java`.

Sources:
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html

## 02. equals() and hashCode()

**File:** src/Lab02Equality.java

**Challenge:** Two users have the same ID. Why does the set keep both? Define identity by ID and predict the new size.

**Expected observation:** Before the change: false, then 2. After the change: true, then 1.

**Why:** Object equality defaults to identity. Equal objects must have equal hash codes. Equal hash codes alone do not make two objects equal.

**Change:** Add both methods to User. Keep id final. Explain symmetry, transitivity, consistency and null behavior.

```java
@Override public boolean equals(Object o) {
    return o instanceof User u && id == u.id;
}
@Override public int hashCode() {
    return Integer.hashCode(id);
}
```

Equality checks: reflexive means a.equals(a). Symmetry means a.equals(b) and b.equals(a) agree. Transitivity connects three equal objects. Consistency applies while relevant state stays unchanged. A non-null user is not equal to null. The class is final, so subclasses cannot introduce a conflicting equality rule.

Complete example: `solutions/Solution02.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html

## 03. String pool and immutability

**File:** src/Lab03Strings.java

**Challenge:** Predict all five lines. Why did concat not change a? What changes with a non-final variable in the concatenation?

**Expected observation:** true, false, true, java, true

**Why:** The constant expression shares the interned literal. new creates a distinct object. equals compares content. concat leaves the original unchanged. Concatenation with a non-final variable creates a distinct result.

**Change:** Assign the result of concat. Use StringBuilder for repeated appends to one growing string.

```java
a = a.concat("21"); // a now refers to "java21"
StringBuilder sb = new StringBuilder("java");
sb.append(21);
System.out.println(sb.toString());
```

Try String prefix = "ja"; String d = prefix + "va"; then compare "java" == d (false) and "java".equals(d) (true). A final String prefix initialized with a constant expression is a different case: it can participate in constant folding.

Complete example: `solutions/Solution03.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html

## 04. Collections: choose a contract

**File:** src/Lab04Collections.java

**Challenge:** Which structure preserves duplicates? Which preserves encounter order? Try events.add("C") and explain the failure.

**Expected observation:** [A, B, A] / [A, B] / A / {A=2, B=1}

**Why:** List permits duplicates. LinkedHashSet preserves insertion order and uniqueness. Deque supports a queue. Map associates keys with values. List.of creates an unmodifiable list, so add throws UnsupportedOperationException.

**Change:** Make a mutable copy, add C and remove every A safely.

```java
List<String> editable = new ArrayList<>(events);
editable.add("C");
editable.removeIf("A"::equals);
System.out.println(editable); // [B, C]
```



Complete example: `solutions/Solution04.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collection.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Map.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html

## 05. ArrayList vs LinkedList

**File:** src/Lab05Lists.java

**Challenge:** Predict the lists. For 100,000 items, compare get(i), appending, and insertion after an already-positioned iterator.

**Expected observation:** [1, 3] / [2, 3] / [2, 9, 3]

**Why:** remove(int) selects an index. remove(Object) selects a value. ArrayList get is O(1), append amortized O(1). LinkedList get traverses nodes, taking O(n) in general. Appending at its tail is O(1).

**Change:** Replace an indexed loop over LinkedList with a for-each loop. Explain why locating an insertion point still costs time.

```java
long sum = 0;
for (int n : b) sum += n;
System.out.println(sum); // 14
// LinkedList iterator insertion is O(1) after positioning.
// ArrayList middle insertion shifts elements: O(n).
```



Complete example: `solutions/Solution05.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/LinkedList.html

## 06. HashMap: collisions and lookup

**File:** src/Lab06Maps.java

**Challenge:** Key always returns hash code 1. Do different keys overwrite each other? Step into put() and get() using your JDK source.

**Expected observation:** 2 / updated / second

**Why:** Hashing selects a bin. Equality distinguishes keys within it. Capacity and load factor influence resizing. A collision does not imply equality.

**Change:** Replace the constant hash with Integer.hashCode(id). In OpenJDK 21 inspect table, Node, resize() and treeifyBin().

```java
@Override public int hashCode() {
    return Integer.hashCode(id);
}
// Bucket trees are implementation details.
// Treeification also depends on table capacity.
// Never rely on HashMap iteration order.
```

In OpenJDK 21 the backing table is an array of bins. Hash spreading and a capacity mask choose a bin. put compares hash and equality, replaces an existing key’s value, or adds an entry. Growth can trigger resize. A heavily populated bin can become a tree once capacity conditions also permit it. Use your IDE’s attached JDK sources to inspect these implementation details rather than treating their constants as a Map API promise.

Complete example: `solutions/Solution06.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashMap.html

## 07. HashSet: mutable keys

**File:** src/Lab07Sets.java

**Challenge:** Why can size be 1 while lookup fails? Which assumption made by the hash-based set no longer holds?

**Expected observation:** On the standard OpenJDK implementation: false, then 1. Behavior after mutating equality-relevant state is not a Set contract guarantee.

**Why:** The object remains stored using its original hash. A changed hash can direct lookup elsewhere. HashSet uses a HashMap internally.

**Change:** Use an immutable key. If an update is necessary, remove the old key before changing it, then insert again.

```java
record StableKey(int id) {}
Set<StableKey> stable = new HashSet<>();
stable.add(new StableKey(1));
System.out.println(stable.contains(new StableKey(1))); // true
```

Mutating the field does not by itself violate Object.equals or Object.hashCode: their consistency conditions allow relevant state changes. The problem is mutating equality-relevant state while the object is stored in a Set. The Set contract does not specify useful behavior for that mutation. Remove before modifying, or prefer immutable keys.

Complete example: `solutions/Solution07.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashSet.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Set.html

## 08. Comparable vs Comparator

**File:** src/Lab08Ordering.java

**Challenge:** Predict ID order twice. Why is subtracting two IDs a risky comparator? What if a TreeSet compares only priority?

**Expected observation:** Natural order IDs: 1, 2, 3. Custom order IDs: 3, 1, 2.

**Why:** Comparable supplies natural order. Comparator supplies an external order. Integer.compare avoids subtraction overflow. A TreeSet treats comparison result 0 as equality.

**Change:** Create a TreeSet with priority only, then add the ID tiebreaker and compare sizes.

```java
Comparator<Job> byPriority =
    Comparator.comparingInt(Job::priority);
Set<Job> s = new TreeSet<>(byPriority);
s.addAll(jobs); // size 2, same-priority jobs collapse
Set<Job> fixed = new TreeSet<>(
    byPriority.thenComparingInt(Job::id));
fixed.addAll(jobs); // size 3
```



Complete example: `solutions/Solution08.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Comparable.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Comparator.html

## 09. Generics: producer and consumer

**File:** src/Lab09Generics.java

**Challenge:** Uncomment the assignment. Why does it fail? Implement copy so it reads Integers and writes them into Numbers.

**Expected observation:** [1, 2, 3]. The commented assignment fails compilation when enabled.

**Why:** Generic types are invariant: List<Integer> is not List<Number>. An extends wildcard permits safe reading as T. A super wildcard permits adding T.

**Change:** Try a List<Object> destination and a List<Double> source. Both should compile with the same copy method.

```java
static <T> void copy(List<? extends T> src,
                     List<? super T> dst) {
    for (T value : src) dst.add(value);
}
// Reading from List<? super T> gives Object.
// Erasure means no new T() or new T[] here.
```



Complete example: `solutions/Solution09.java`.

Sources:
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html

## 10. Exceptions: preserve the cause

**File:** src/Lab10Exceptions.java

**Challenge:** Both the body and close() throw. Which exception escapes? Where does the second exception go?

**Expected observation:** read failed / close failed

**Why:** Try-with-resources keeps the body failure primary and records the close failure as suppressed. Wrapping should retain the original cause for diagnosis.

**Change:** Wrap the caught IOException in UncheckedIOException and inspect its cause and the cause’s suppressed exceptions.

```java
catch (IOException e) {
    throw new UncheckedIOException("import failed", e);
}
// The cause is "read failed".
// cause.getSuppressed()[0] is "close failed".
// A finally return can hide a failure: avoid it.
```



Complete example: `solutions/Solution10.java`.

Sources:
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.20.3

## 11. Checked vs unchecked exceptions

**File:** src/Lab11Checked.java

**Challenge:** Replace the first try/catch with checked(); and remove main’s throws declaration. Which call stops compiling? Does unchecked mean harmless?

**Expected observation:** checked caught / unchecked caught

**Why:** Checked exceptions must be caught or declared. RuntimeException and Error subclasses are unchecked. Both checked and unchecked exceptions happen at runtime.

**Change:** Write a validation method that rejects negative order quantities with IllegalArgumentException.

```java
static void validateQuantity(int quantity) {
    if (quantity < 0) {
        throw new IllegalArgumentException("negative quantity");
    }
}
// IOException: a caller may choose an I/O recovery policy.
// Validation choice depends on the API contract.
```

The edit means replacing the entire first try/catch statement with a bare checked() call, not leaving a try without catch or finally. With main no longer declaring throws, the compiler requires handling IOException. Removing the NumberFormatException catch still compiles, but the exception can terminate execution.

Complete example: `solutions/Solution11.java`.

Sources:
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-11.html

## 12. Java memory model: publication

**File:** src/Lab12MemoryModel.java

**Challenge:** The field is not volatile. Why is 42 guaranteed after join returns normally? What if you read before joining?

**Expected observation:** 42, after successful join. A read before join has no equivalent visibility guarantee and may see 0 or 42.

**Why:** Thread completion happens-before a successful join return. Happens-before relates memory actions across threads. It is separate from where objects are stored.

**Change:** Publish a value using a volatile ready flag. Which write must precede ready = true?

```java
// Shared fields: int value; volatile boolean ready;
// Writer:
value = 42;
ready = true;
// Reader:
if (ready) System.out.println(value); // 42
// This does not guarantee when the reader sees true.
```



Complete example: `solutions/Solution12.java`.

Sources:
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-17.html

## 13. Stack vs heap: reference copies

**File:** src/Lab13StackHeap.java

**Challenge:** Does assigning a new Box inside change() replace the caller’s variable? Inspect both frames in the debugger.

**Expected observation:** 2

**Why:** Java copies reference values into parameters. Reassignment changes only the callee’s local. Each thread has stack frames and locals. The JVM heap supplies shared object and array storage. Optimizations may eliminate physical allocations.

**Change:** Return the replacement Box, assign it in main, and predict the value. Discuss JVM optimizations separately from the logical model.

```java
static Box replacement(Box box) {
    box.value = 2;
    Box next = new Box();
    next.value = 3;
    return next;
}
// box = replacement(box); // value is now 3
```



Complete example: `solutions/Solution13.java`.

Sources:
- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-2.html

## 14. Garbage collection: reachability

**File:** src/Lab14GC.java

**Challenge:** Which references keep the arrays reachable? After clear(), must a collection happen immediately? Inspect GC logs.

**Expected observation:** 8, then 0. GC log timing and reclaimed byte counts vary.

**Why:** Clearing removes the list’s strong references to those arrays. Eligibility does not guarantee prompt reclamation. System.gc() is a request. Resource cleanup still needs close().

**Change:** Run with -Xlog:gc. Compare keeping the list populated with clearing it. Explain why a static collection can retain data indefinitely.

```java
// Run:
// java -Xlog:gc src/Lab14GC.java
// Optional JVM cap: -Xmx64m
// Do not assert that freeMemory() rises immediately.
// Strongly reachable objects cannot be reclaimed.
// Collection can reclaim unreachable cycles.
```

Examples of roots include live thread references and references held through loaded classes. Reachability is a graph property, so unreachable cycles can be collected. A weak reference alone does not keep its referent strongly reachable. An open file or socket should have explicit resource cleanup.

Complete example: `solutions/Solution14.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/System.html#gc()
- https://docs.oracle.com/javase/specs/jvms/se21/html/jvms-2.html

## 15. Immutable objects: defensive copies

**File:** src/Lab15Immutable.java

**Challenge:** The field is final. Why can callers still change the profile? Fix both constructor exposure and getter exposure.

**Expected observation:** [USER, ADMIN], then [USER, ADMIN, OPS] before the fix.

**Why:** final prevents assigning a different list to the field. It does not freeze that list. Copying the input and returning an unmodifiable list closes both paths.

**Change:** Use List.copyOf in the constructor. Catch the getter mutation failure and confirm the profile remains unchanged.

```java
Profile(List<String> roles) {
    this.roles = List.copyOf(roles);
}
// roles() can return this stored list.
// input.add("ADMIN") no longer affects p.
// p.roles().add("OPS") throws UnsupportedOperationException.
// Mutable elements would need their own copying policy.
```



Complete example: `solutions/Solution15.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html#copyOf(java.util.Collection)

## 16. Records: shallow immutability

**File:** src/Lab16Records.java

**Challenge:** Are record components deeply immutable? Which methods did the compiler supply? What happens to a’s hash code after mutation?

**Expected observation:** true / [java, sql] / false

**Why:** A record provides accessors, equals, hashCode and toString. Component fields are final. Referenced mutable objects can still change, so this record’s equality and hash code can change with its list.

**Change:** Add a compact constructor with a defensive copy. The last equality check should then remain true.

```java
record Course(String name, List<String> tags) {
    Course {
        Objects.requireNonNull(name);
        tags = List.copyOf(tags);
    }
}
// Record classes are final.
// Copying the list is shallow, sufficient for String elements.
```



Complete example: `solutions/Solution16.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Record.html

## 17. Functional interfaces: one contract

**File:** src/Lab17Functions.java

**Challenge:** Why can PriceRule have a default method and still be functional? Add a second unrelated abstract method and compile.

**Expected observation:** 900 / rule / 950. The extra abstract method makes the lambda target invalid.

**Why:** A functional interface has one abstract function contract. Default and static methods do not add abstract obligations. @FunctionalInterface asks the compiler to check this.

**Change:** Choose standard interfaces for testing a quantity, creating an ID and consuming a log message.

```java
Predicate<Integer> valid = n -> n > 0;
Supplier<String> id = () -> "ORDER-1";
Consumer<String> log = System.out::println;
log.accept(id.get());
System.out.println(valid.test(2)); // true
```



Complete example: `solutions/Solution17.java`.

Sources:
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html#jls-9.8

## 18. Lambdas: captured variables

**File:** src/Lab18Lambdas.java

**Challenge:** Uncomment minimum++. Why does that fail while seen.add works? Is seen now safe to share between threads?

**Expected observation:** true / [java]. Uncommenting minimum++ causes a compilation error.

**Why:** Captured local variables must be final or effectively final. The list reference stays unchanged while its contents mutate. Capture gives no thread safety to the object.

**Change:** Use an explicit threshold parameter instead of mutable captured configuration. Replace a suitable lambda with a method reference.

```java
BiPredicate<String, Integer> enough =
    (text, threshold) -> text.length() >= threshold;
System.out.println(enough.test("java", 5)); // false
Consumer<String> collect = seen::add;
// Replace the existing collect declaration to avoid a duplicate.
```



Complete example: `solutions/Solution18.java`.

Sources:
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-15.html#jls-15.27

## 19. Streams: lazy transformation

**File:** src/Lab19Streams.java

**Challenge:** When does processing happen? Why does the second terminal operation fail? Can result.add("ANN") succeed?

**Expected observation:** pipeline created / [AMY] / already consumed

**Why:** Intermediate operations build a lazy pipeline. A terminal operation consumes it. Stream.toList() returns an unmodifiable list. Avoid depending on side effects in pipeline stages.

**Change:** Produce a mutable result and count names by value using a collector. Explain map vs flatMap.

```java
List<String> mutable = List.of("amy", "bob").stream()
    .map(String::toUpperCase)
    .collect(Collectors.toCollection(ArrayList::new));
Map<String, Long> counts = List.of("amy", "bob", "amy")
    .stream().collect(Collectors.groupingBy(
        Function.identity(), TreeMap::new, Collectors.counting()));
// {amy=2, bob=1}; flatMap flattens each mapped stream.
```

map transforms each element into one result. flatMap turns each element into a stream and flattens those streams. Example: List.of(List.of(1,2), List.of(3)).stream().flatMap(List::stream).toList() gives [1,2,3].

Complete example: `solutions/Solution19.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Collectors.html

## 20. Optional: eager vs lazy fallback

**File:** src/Lab20Optionals.java

**Challenge:** How many times does fallback run? What changes when name is empty? When would you use orElseThrow?

**Expected observation:** fallback called / Mira / Mira / true

**Why:** Java evaluates arguments before calling orElse. orElseGet invokes its supplier only when empty. Optional represents possible absence, commonly in a return value.

**Change:** Normalize a nullable name using map and filter, then supply a default. Avoid unchecked get().

```java
String raw = "  ";
String value = Optional.ofNullable(raw)
    .map(String::trim)
    .filter(s -> !s.isEmpty())
    .orElse("anonymous");
System.out.println(value); // anonymous
// Optional.of(null) throws NullPointerException.
```

With Optional.empty(), the two fallback expressions each run once, and both print guest. Use orElseThrow(() -> new IllegalStateException("required value missing")) when absence should be a domain failure. Optional.empty().orElseThrow() without a supplier throws NoSuchElementException.

Complete example: `solutions/Solution20.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Optional.html

## 21. Thread lifecycle: observed states

**File:** src/Lab21Lifecycle.java

**Challenge:** Which states are guaranteed here? Break in the worker before await(). Why is run() different from start()?

**Expected observation:** NEW / a scheduling-dependent snapshot / TERMINATED. The middle snapshot may be RUNNABLE or WAITING.

**Why:** start schedules a new thread. Calling run directly runs code on the caller. getState is a snapshot, not a coordination primitive. A terminated thread cannot be restarted.

**Change:** Observe a thread waiting to enter synchronized and one in sleep. Name all six Thread.State values.

```java
// NEW: not started
// RUNNABLE: eligible to execute, including executing
// BLOCKED: waiting for a monitor
// WAITING: untimed wait, e.g. latch.await()
// TIMED_WAITING: e.g. sleep or timed wait
// TERMINATED: finished
// Re-starting t throws IllegalThreadStateException.
```



Complete example: `solutions/Solution21.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.State.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html

## 22. Synchronization: one shared monitor

**File:** src/Lab22Synchronization.java

**Challenge:** Predict the total. Remove synchronized and run repeatedly. Would synchronizing two different Counter instances protect one shared static value?

**Expected observation:** 200000 with synchronization. Removing it permits lost updates, even if some runs still print 200000.

**Why:** Every increment uses the same instance monitor, so only one thread executes it at a time. Synchronizing on different objects does not protect the same critical section.

**Change:** Replace the synchronized method with a block using one private final lock object.

```java
private final Object lock = new Object();
void increment() {
    synchronized (lock) { value++; }
}
// All competing accesses must follow the same lock policy.
// main reads after join, which also gives visibility.
```



Complete example: `solutions/Solution22.java`.

Sources:
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-17.html

## 23. Race conditions: forced lost update

**File:** src/Lab23Race.java

**Challenge:** Both threads read before either writes. What is the final value? Why would adding volatile to value fail to fix this?

**Expected observation:** 1 on normal completion. The barrier forces both reads to observe the initial 0 before either assignment.

**Why:** Increment consists of reading, calculating and writing. Visibility alone cannot make those actions indivisible. This controlled schedule exposes the lost update.

**Change:** Replace the complete read/barrier/write task with an atomic increment. Do not place the barrier inside a shared lock.

```java
AtomicInteger count = new AtomicInteger();
Runnable task = () -> count.incrementAndGet();
// Start two threads, join both, then count.get() is 2.
// A barrier inside a shared lock can deadlock:
// the first thread waits while blocking the second entrant.
```



Complete example: `solutions/Solution23.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/CyclicBarrier.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/atomic/AtomicInteger.html

## 24. volatile: publishing a stop signal

**File:** src/Lab24Volatile.java

**Challenge:** Why does this protocol need volatile? Remove it and retry. Does seeing successful runs prove that the plain-field version is correct?

**Expected observation:** Usually alive=false. The one-second timeout is not a scheduling guarantee. Plain-field success proves nothing about all executions.

**Why:** A volatile write happens-before subsequent reads of that field. Without synchronization, a reader may reuse a stale value. This visibility rule does not make count++ atomic or set a scheduling deadline.

**Change:** Use interruption for cancellation of a task that may block. Check the interrupt flag and handle InterruptedException.

```java
try {
    while (!Thread.currentThread().isInterrupted()) {
        Thread.sleep(100); // stand-in for interruptible work
    }
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
// Caller requests cancellation with t.interrupt().
```



Complete example: `solutions/Solution24.java`.

Sources:
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-17.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html

## 25. Locks: release after failure

**File:** src/Lab25Locks.java

**Challenge:** Why is unlock inside finally? Remove it and inspect isLocked(). When does tryLock help?

**Expected observation:** failed / false. Without unlock, this thread still owns the lock.

**Why:** Explicit locks require explicit release. ReentrantLock supports features such as timed acquisition and interruptible waiting. Reentrancy means the owner can acquire it again.

**Change:** Attempt a timed acquisition and release only when it succeeds. Explain how consistent lock ordering prevents a common deadlock pattern.

```java
if (lock.tryLock(200, TimeUnit.MILLISECONDS)) {
    try {
        System.out.println("acquired");
    } finally {
        lock.unlock();
    }
} else { System.out.println("busy"); }
```

If every task acquires lock A before lock B, two tasks cannot form the simple cycle where each holds one and waits for the other. All code paths need the same global ordering. Fair locking is a different property and does not automatically prevent deadlock.

Complete example: `solutions/Solution25.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/locks/ReentrantLock.html

## 26. Atomic classes: compare and set

**File:** src/Lab26Atomics.java

**Challenge:** Why is if (stock.get() > 0) stock.decrementAndGet() unsafe with two buyers? Which value does compareAndSet compare?

**Expected observation:** true / false / 0

**Why:** compareAndSet updates only if the current value matches the observed value. Retrying handles interference. A thread-safe read followed by a thread-safe write is not one atomic decision.

**Change:** Run reserve from two threads. Exactly one reservation should succeed and stock should remain zero.

```java
static boolean reserve(AtomicInteger stock) {
    while (true) {
        int current = stock.get();
        if (current <= 0) return false;
        if (stock.compareAndSet(current, current - 1))
            return true;
    }
}
```



Complete example: `solutions/Solution26.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/atomic/AtomicInteger.html

## 27. ConcurrentHashMap: compound updates

**File:** src/Lab27ConcurrentMap.java

**Challenge:** Predict the result. Replace merge with getOrDefault followed by put. Why is the new version unsafe?

**Expected observation:** 20000 with merge. The get-then-put version can lose updates.

**Why:** merge performs the update atomically for that key. Thread-safe methods do not make an arbitrary sequence atomic. The map rejects null keys and values.

**Change:** Use computeIfAbsent with LongAdder for a frequency counter. Read the final sum after joining all writers.

```java
ConcurrentHashMap<String, LongAdder> counts =
    new ConcurrentHashMap<>();
counts.computeIfAbsent("orders", key -> new LongAdder())
    .increment();
// LongAdder.sum() is not an atomic snapshot during updates.
// Iteration is weakly consistent, not a frozen snapshot.
```



Complete example: `solutions/Solution27.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ConcurrentHashMap.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/atomic/LongAdder.html

## 28. ExecutorService: results and shutdown

**File:** src/Lab28Executors.java

**Challenge:** Where does the task exception appear? Does shutdown wait for completion? Why must you inspect Future results?

**Expected observation:** 42 / boom

**Why:** submit captures failures in the Future. get surfaces them through ExecutionException. shutdown rejects new tasks while allowing accepted tasks to finish; it does not itself wait.

**Change:** Add awaitTermination with a timeout and request shutdownNow if needed. Preserve interruption and remember cancellation is cooperative.

```java
pool.shutdown();
try {
    if (!pool.awaitTermination(2, TimeUnit.SECONDS))
        pool.shutdownNow();
} catch (InterruptedException e) {
    pool.shutdownNow();
    Thread.currentThread().interrupt();
}
```



Complete example: `solutions/Solution28.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ExecutorService.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/Future.html

## 29. CompletableFuture: compose results

**File:** src/Lab29Futures.java

**Challenge:** Which stages are independent? Why use thenCompose when a function returns a future? What changes with thenApply?

**Expected observation:** 120 / 110 / -1

**Why:** thenCombine joins independent results. thenCompose flattens a future-producing step. thenApply transforms a value and would produce a nested future here. exceptionally replaces a failed result.

**Change:** Add orTimeout and explain why a timed-out future does not automatically stop its underlying task.

```java
CompletableFuture<Integer> pending = new CompletableFuture<>();
int result = pending.orTimeout(100, TimeUnit.MILLISECONDS)
    .exceptionally(error -> -1).join(); // -1
// Non-async continuations may run on a completing thread.
// Async methods without an executor usually use commonPool.
// join wraps failures in CompletionException.
```

price and shipping start as independent computations. price.thenApply(Lab29Futures::taxed) has type CompletableFuture<CompletableFuture<Integer>>. thenCompose removes that nesting. orTimeout completes the future exceptionally; it does not interrupt or cancel arbitrary underlying work. Cancellation requires a separate cooperative design.

Complete example: `solutions/Solution29.java`.

Sources:
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/CompletableFuture.html
