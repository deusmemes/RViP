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

    val rows = matrix

    fun getRowByIndex(index: Int): IntArray {
        return matrix[index]
    }

    override fun toString(): String {
        return matrix.joinToString(separator = "\n") { row -> row.joinToString(separator = " ") }
    }

    companion object {
        var instance: Matrix? = null
    }
}