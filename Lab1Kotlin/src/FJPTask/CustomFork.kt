package FJPTask

import Matrix
import java.math.BigInteger
import java.util.concurrent.RecursiveTask

class CustomFork(private val from: Int, private val to: Int): RecursiveTask<BigInteger>() {
    override fun compute(): BigInteger {
        return if ((to - from) <= (Matrix.instance!!.rowsCount / Runtime.getRuntime().availableProcessors())) {
            var sum = BigInteger.valueOf(0)
            for (i in from until to) {
                sum = sum.add(Matrix.instance!!.getRowMultiply(i))
            }
            sum
        } else {
            val middle = (to + from) / 2
            val firstHalf = CustomFork(from, middle)
            firstHalf.fork()
            val secondHalf = CustomFork(middle + 1, to)
            val secondValue = secondHalf.compute()
            firstHalf.join() + secondValue
        }
    }
}