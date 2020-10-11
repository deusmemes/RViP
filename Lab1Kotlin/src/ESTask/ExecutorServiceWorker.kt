package ESTask

import BaseWorker
import IWorker
import Matrix
import java.math.BigInteger
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.system.measureTimeMillis

class ExecutorServiceWorker(private val matrix: Matrix) : IWorker, BaseWorker(matrix) {
    override fun run() {
        val time = measureTimeMillis {
            val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
            val futures = mutableListOf<Future<BigInteger>>()

            for (i in 0 until matrix.rowsCount) {
                val future = executor.submit(Callable { matrix.getRowMultiply(i) })
                futures.add(future)
            }

            var sum = BigInteger.valueOf(0)
            futures.forEach { sum = sum.add(it.get()) }

            printResult(sum)
        }

        printTime(time)
    }
}