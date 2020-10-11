import ESTask.ExecutorServiceWorker
import FJPTask.ForkJoinPoolWorker
import STTask.SingleThreadWorker

class Logic(size: Pair<Int, Int>, range: Pair<Int, Int>) {
    private var matrix: Matrix = Matrix(size.first, size.second, range.first, range.second)

    init {
        Matrix.instance = matrix
        SingleThreadWorker(matrix).run()
        ExecutorServiceWorker(matrix).run()
        ForkJoinPoolWorker(matrix).run()
    }
}