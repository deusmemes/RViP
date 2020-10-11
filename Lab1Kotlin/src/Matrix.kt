import java.math.BigInteger
import java.util.*

class Matrix(
        val rowsCount: Int,
        private val columnsCount: Int,
        private val min: Int,
        private val max: Int
) {
    private val rnd = Random()
    private val matrix: Array<IntArray> = Array(rowsCount) {
        IntArray(columnsCount) {
            rnd
                .ints(min, max + 1)
                .limit(1)
                .findFirst()
                .asInt
        }
    }

    fun getRowMultiply(rowIndex: Int): BigInteger {
        return matrix[rowIndex]
                .map { BigInteger.valueOf(it.toLong()) }
                .reduce { acc, bigInteger -> acc.multiply(bigInteger) }
    }

    fun sumRowsMultiply(): BigInteger {
        return matrix
                .mapIndexed { index, _ -> getRowMultiply(index) }
                .reduce { acc, bigInteger -> acc.add(bigInteger) }
    }

    override fun toString(): String {
        return matrix.joinToString(separator = "\n\n") { row -> row.joinToString(separator = "\t\t") }
    }

    companion object {
        var instance: Matrix? = null
    }
}