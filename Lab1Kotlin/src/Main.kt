import kotlinx.coroutines.runBlocking

fun main() {
    val matrixSize = Pair(1_000, 10_000)
    val matrixRange = Pair(1, 7)

    val logic = Logic(matrixSize, matrixRange)
//    logic.runSync()
//    logic.runForkJoin()
//    logic.runExecutorService()
}