package com.example.master.controllers

import com.example.master.services.MainService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigInteger

@RestController
class MainController {
    private val mainService = MainService()

    @GetMapping(value = ["/sum"])
    fun sum(): BigInteger {
        return mainService.sumRowsMultiply()
    }
}