package com.govtech.ecommerce.controller

import com.govtech.ecommerce.service.EcommerceService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
class EcommerceController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var service : EcommerceService

    @GetMapping("/dataset/upload")
    fun file() : String {
        return "upload"
    }

    @PostMapping("/dataset/upload")
    fun upload(model: Model,
                @RequestParam("uploadCSV") csv: MultipartFile) : String {

        val buffReader = service.uploadCSV(csv)
        var message : String

        if (buffReader.ready()) {
            if (service.saveCSV(buffReader).size > 0) {
                message = "Success"
            } else {
                message = "Error"
            }
        } else {
            message = "Error"
        }

        model.addAttribute("message", message)
        return "upload"
    }

    @GetMapping("/dataset/list")
    fun list(model: Model,
             @RequestParam("page", required = false, defaultValue = "0") page : Int,
             @RequestParam("size", required = false, defaultValue = "5") size : Int) : String? {

        val invoicePage = service.listInvoiceByPage(page, size)
        model.addAttribute("invoicePage", invoicePage)

        return "list"
    }

    @GetMapping("/dataset/search")
    fun search(model: Model,
               @RequestParam("query", required = false, defaultValue = "bob") query: String) : String{
        val queryResults = service.searchByAny(query)
        model.addAttribute("queryResults", queryResults)

        return "results"
    }

    @GetMapping("/")
    fun index(model: Model) : String {
        model.addAttribute("title", "eCommerce")
        service.checkifDBEmpty()
        return "index"
    }

}