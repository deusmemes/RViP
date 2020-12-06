package com.example.worker.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.math.BigInteger

@RestController
class MainController {
    @GetMapping(value = ["/test"])
    fun test(): String {
        return "hello"
    }

    @PostMapping(value= ["/multiply"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun multiply(@RequestBody row: IntArray) : BigInteger {
        return row
                .map { BigInteger.valueOf(it.toLong()) }
                .reduce { acc, bigInteger -> acc.multiply(bigInteger) }
    }
}