import mpi.MPI
import java.math.BigInteger
import kotlin.system.measureTimeMillis

val matrix = Matrix()

fun main(args: Array<String>) {
    MPI.Init(args)

    val currentProcessRank = MPI.COMM_WORLD.Rank()
    val countProcesses = MPI.COMM_WORLD.Size()
    val matrixRows = matrix.matrix.size

    val parts = IntArray(countProcesses - 1)
    for (i in parts.indices) {
        if (i == 0) {
            parts[i] = matrixRows / (countProcesses - 1)
        } else {
            parts[i] = (matrixRows - parts.filterIndexed { index, _ -> index < i }.sum()) / (countProcesses - 1 - i)
        }
    }

    if (isMainProcess(currentProcessRank)) {
        val mults = arrayOfNulls<BigInteger>(matrixRows)

        val time = measureTimeMillis {
            for (process in 1 until countProcesses) {
                val count = parts[process - 1]
                val offset = (process - 1) * count
                MPI.COMM_WORLD.Send(mults, offset, count, MPI.OBJECT, process, 1)
            }

            for (process in 1 until countProcesses) {
                val count = parts[process - 1]
                val offset = (process - 1) * count
                MPI.COMM_WORLD.Recv(mults, offset, count, MPI.OBJECT, process, 1)
            }
        }

        printTime(time)
        println(mults.reduce { acc, bigInteger -> acc!!.add(bigInteger) })
    } else {
        val count = parts[currentProcessRank - 1]
        val offset = (currentProcessRank - 1) * count
        val buffer = arrayOfNulls<BigInteger>(count)

        MPI.COMM_WORLD.Recv(buffer, 0, count, MPI.OBJECT, 0, 1)

        for ((j, i) in (offset until (offset + count)).withIndex()) {
            buffer[j] = matrix.getRowMultiply(i)
        }

        MPI.COMM_WORLD.Send(buffer, 0, count, MPI.OBJECT, 0, 1)
    }

    MPI.Finalize()
}

fun isMainProcess(processRank: Int): Boolean {
    return processRank == 0
}

fun printTime(time: Long) {
    println("Time: $time ms")
}

fun printResult(result: BigInteger) {
    println("Result: $result")
}