import java.io.File
import java.math.BigInteger

class Matrix(

) {

    var matrix: Array<IntArray> = this.readFromFile()

    fun getRowMultiply(rowIndex: Int): BigInteger {
        return matrix[rowIndex]
                .map { BigInteger.valueOf(it.toLong()) }
                .reduce { acc, i -> acc.multiply(i) }
    }

    override fun toString(): String {
        return matrix.joinToString(separator = "\n\n") { row -> row.joinToString(separator = "\t\t") }
    }

    fun readFromFile(): Array<IntArray> {
        val file = File("matrix.txt")

        return file
                .readLines()
                .map { it -> it.split(' ')
                        .map { it.toInt() }
                        .toIntArray() }
                .toTypedArray()
    }

    companion object {
        var instance: Matrix? = null
    }
}