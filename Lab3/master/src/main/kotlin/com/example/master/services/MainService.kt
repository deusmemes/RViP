package com.example.master.services

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.math.BigInteger
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.system.measureTimeMillis

class MainService {
    private val workers = arrayOf(
        "http://localhost:5001",
        "http://localhost:5002",
        "http://localhost:5003"
    )

    private val matrix = Matrix(1000, 10000, 1, 7)

    fun sumRowsMultiply() : BigInteger {
        var sum = BigInteger.valueOf(0)

        val time = measureTimeMillis {
            val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
            val futures = mutableListOf<Future<BigInteger>>()

            for (i in 0 until matrix.rowsCount) {
                val future = executor.submit(Callable {
                    val template = RestTemplate()
                    val headers = HttpHeaders()
                    headers.contentType = MediaType.APPLICATION_JSON
                    val url = workers[i % workers.size] + "/multiply"
                    val request = HttpEntity(matrix.matrix[i])
                    val response = template.postForEntity(url, request, BigInteger::class.java)
                    response.body!!
                })
                futures.add(future)
            }

            futures.forEach { sum = sum.add(it.get()) }
        }

        println("time: $time ms")
        return sum
    }
}