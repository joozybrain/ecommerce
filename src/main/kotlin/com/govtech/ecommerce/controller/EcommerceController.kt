package com.govtech.ecommerce.controller

import com.govtech.ecommerce.model.Invoice
import com.govtech.ecommerce.service.EcommerceService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ecommerce")
class EcommerceController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var service : EcommerceService

    @GetMapping("/dataset/upload")
    fun upload() : Int {
        val invoiceList = service.uploadCSV()
        return invoiceList.size
    }

    @GetMapping("/dataset/list")
    fun list() : List<Invoice>? {
        val recordCount = service.checkifDBEmpty()
        if (recordCount == 0) {
            return null
        } else {
            val pageResult = service.listInvoiceByPage()
            return pageResult.content
        }
    }

    @PostMapping("/dataset/search")
    fun search(@RequestParam("query") query: String) : List<Invoice>{
        return service.searchByAny(query)
    }
}