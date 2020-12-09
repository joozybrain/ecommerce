package com.govtech.ecommerce.controller

import com.govtech.ecommerce.model.Invoice
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ecommerce")
class EcommerceController {

    @GetMapping("/dataset/upload")
    fun upload() = "Hello World"

    @GetMapping("/dataset/list")
    fun list() = "Listing"

    @GetMapping("/dataset/search")
    fun search() = "Searching World"
}