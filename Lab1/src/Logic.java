import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Logic {
    Matrix matrix;

    public Logic() {
        this.matrix = new Matrix(1, 100000, 1, 10);
    }

    public void runSync() {
        var startTime = System.nanoTime();
        var sum = matrix.getSumRowsMultiply();
        var endTime = System.nanoTime();

        System.out.println("\nsum: " + sum);
        printTime(startTime, endTime);
    }

    public void runAsync() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(matrix.getRowsCount());
        List<Future<BigInteger>> futures = new ArrayList<>();

        var start = System.nanoTime();
        for (int i = 0; i < matrix.getRowsCount(); i++) {
            int finalI = i;
            var future = executor.submit((Callable) () -> {
                return matrix.getRowMultiply(matrix.getMatrix()[finalI]);
            });
            futures.add(future);
        }

        var sum = BigInteger.valueOf(0L);
        for (var f : futures) {
            BigInteger res = f.get();
            sum = sum.add(res);
        }

        var end = System.nanoTime();
        System.out.println(sum);
        printTime(start, end);
    }

    public void runFork() {
    }

    public void print() {
        System.out.println(matrix.toString());
    }

    private void printTime(long start, long end) {
        System.out.println("time: " + (end - start)/1000000 + " ms");
    }
}

class MySingleton {
    private static MySingleton instance;
    private int size;

    private MySingleton(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public static MySingleton getInstance() {

        if (instance == null) {
            instance = new MySingleton();
        }

        return instance;
    }
}
