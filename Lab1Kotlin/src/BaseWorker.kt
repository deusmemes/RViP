import java.math.BigInteger

open class BaseWorker(private val matrix: Matrix) {
    fun printMatrix() {
        println(matrix.toString())
    }

    fun printTime(time: Long) {
        println("Time: $time ms")
    }

    fun printResult(result: BigInteger) {
        println("Result: $result")
    }
}