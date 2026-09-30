# Core Java IDE practice

29 labs covering the requested Core Java and concurrency topics. No Maven, Gradle, Spring or external libraries are required.

## Setup

Use a JDK 21 project in IntelliJ IDEA, Eclipse or VS Code with Java support. These samples use Java 17-compatible language features and APIs. Enable assertions with `-ea` when adding your own assertions. Each file has a standalone main method and default package.

1. Open this folder as a Java project and set the project SDK.
2. Open `src/Lab01OOP.java` and run its main method.
3. Predict first, run second, then modify the code from the challenge.
4. Only after trying, open `solutions/Solution01.java` and the matching answer slide.
5. Re-create the solution from a blank file the next day.

Command line, from this folder:

```sh
java src/Lab01OOP.java
java solutions/Solution01.java
java -Xlog:gc src/Lab14GC.java
```

To compile everything into a single output folder (Bash):

```sh
mkdir -p out
javac -d out src/*.java solutions/*.java
java -cp out Lab01OOP
java -cp out Solution01
```

`ANSWERS.md` includes every challenge, expected observation, explanation, solution snippet and source. `PRACTICE_LOG.md` is your editable progress sheet. `CAPSTONE.md` combines several skills in one exercise.

## How to experiment

Allow 25–45 minutes per lab. Read the complete Java file: slides show main-method excerpts, with helper classes in the file. Compiler-failure experiments are commented out so the original pack compiles. Uncomment only the line you are currently investigating. Undo it before compiling the whole folder.

Use breakpoints to inspect object identity, locals, the call stack and shared fields. In concurrent labs, stopping threads can change timing. Never use breakpoint behavior as proof of thread safety. Output order, scheduling, collection timing and thread-state snapshots may vary where explicitly noted.

The barrier in Lab23 deliberately forces one failing interleaving. Do not put that barrier inside a shared lock. Lab24 uses a daemon worker and bounded join so removing volatile cannot leave the demo process alive forever. A one-second timeout does not promise a scheduling deadline. GC exercises make bounded allocations; do not turn them into an unbounded allocator.

## Suggested pace

Week 1: Labs 1–7. Week 2: Labs 8–14. Week 3: Labs 15–20. Week 4: Labs 21–25. Week 5: Labs 26–29 and capstone. Spread a week across more days when work is hectic.

One session: 5 minutes prediction, 15–25 minutes coding, 5 minutes explanation, 5 minutes recalling yesterday's lab. On a difficult workday, one prediction plus one small edit is enough.

## Verification scope

All original lab programs and worked solution programs were compiled and run with the available OpenJDK 17.0.20 runtime. The material targets JDK 21, and the samples deliberately stay within the Java 17-compatible subset. No JDK 21 execution was available in the authoring environment. Observed outputs are examples where scheduling or garbage collection is variable.
# core-java-practice-exercises
