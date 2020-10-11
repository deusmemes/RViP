package FJPTask

import BaseWorker
import IWorker
import Matrix
import java.util.concurrent.ForkJoinPool
import kotlin.system.measureTimeMillis

class ForkJoinPoolWorker(private val matrix: Matrix) : IWorker, BaseWorker(matrix) {
    override fun run() {
        val time = measureTimeMillis {
            val pool = ForkJoinPool(Runtime.getRuntime().availableProcessors())
            val sum = pool.invoke(CustomFork(0, matrix.rowsCount))
            printResult(sum)
        }

        printTime(time)
    }
}