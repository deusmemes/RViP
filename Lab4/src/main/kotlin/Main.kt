import org.apache.ignite.Ignition
import org.apache.ignite.configuration.IgniteConfiguration
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder
import java.math.BigInteger
import kotlin.system.measureTimeMillis


fun main() {
    val matrix = Matrix(100, 100, 1, 5)
    val cfg = IgniteConfiguration()
    cfg.isClientMode = true
    cfg.isPeerClassLoadingEnabled = true
    val ipFinder = TcpDiscoveryMulticastIpFinder()
    ipFinder.setAddresses(listOf("127.0.0.1:47500..47509"))
    cfg.discoverySpi = TcpDiscoverySpi().setIpFinder(ipFinder)
    val ignite = Ignition.start(cfg)
    ignite.use {
       val time = measureTimeMillis {
           val cache = ignite.getOrCreateCache<Int, IntArray>("rowsCache")
           matrix.rows.forEachIndexed { index, ints -> cache.put(index, ints) }
           matrix.rows.forEachIndexed { index, _ -> ignite.compute(ignite.cluster().forServers()).run(RemoteTask(index))}

           val outCache = ignite.getOrCreateCache<Int, BigInteger>("outCache")
           var sum = BigInteger.valueOf(0)
           outCache.forEach { sum = sum.add(it.value) }

           println("Sum = $sum")
       }

       println("Time: $time ms")
   }
}