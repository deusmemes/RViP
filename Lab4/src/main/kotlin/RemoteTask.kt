import org.apache.ignite.Ignite
import org.apache.ignite.lang.IgniteRunnable
import org.apache.ignite.resources.IgniteInstanceResource
import java.math.BigInteger


class RemoteTask(private val index: Int) : IgniteRunnable {
    @IgniteInstanceResource
    var ignite: Ignite? = null
    override fun run() {
        val cache = ignite!!.cache<Int, IntArray>("rowsCache")
        val row = cache.get(index)

        val outCache = ignite!!.getOrCreateCache<Int, BigInteger>("outCache")
        outCache.put(index, getRowMultiply(row))
    }

    private fun getRowMultiply(row: IntArray): BigInteger {
        return row
            .map { BigInteger.valueOf(it.toLong()) }
            .reduce { acc, bigInteger -> acc.multiply(bigInteger) }
    }
}