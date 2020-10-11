package STTask

import BaseWorker
import IWorker
import Matrix
import kotlin.system.measureTimeMillis

class SingleThreadWorker(private val matrix: Matrix) : IWorker, BaseWorker(matrix) {
    override fun run() {
        val time = measureTimeMillis {
            val sum = matrix.sumRowsMultiply()
            printResult(sum)
        }

        printTime(time)
    }
}