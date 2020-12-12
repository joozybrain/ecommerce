package com.govtech.ecommerce.controller

import com.govtech.ecommerce.service.EcommerceService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalTime

@Controller
class EcommerceController {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var service : EcommerceService

    @GetMapping(path = ["/flux"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamFlux() : Flux<String> {
        return Flux.interval(Duration.ofSeconds(1))
            .map { elements -> "In Progress at "+LocalTime.now().toString() }
    }



    @GetMapping("/dataset/upload")
    fun file() : String {
        return "upload"
    }

    @PostMapping("/dataset/upload")
    fun upload(model: Model,
                @RequestParam("uploadCSV") csv: MultipartFile) : String {

        try {
            val buffReader = service.uploadCSV(csv)

            val reactiveDataMessage = ReactiveDataDriverContextVariable(service.saveCSV(buffReader),1)

            model.addAttribute("messages", reactiveDataMessage)
            return "upload"
        } catch (e: Exception) {
            return "error"
        }

    }

    @GetMapping("/dataset/list")
    fun list(model: Model,
             @RequestParam("page", required = false, defaultValue = "0") page : Int,
             @RequestParam("size", required = false, defaultValue = "5") size : Int) : String? {

        try {
            val invoicePage = service.listInvoiceByPage(page, size)
            model.addAttribute("invoicePage", invoicePage)

            return "list"
        } catch (e: Exception) {
            return "error"
        }

    }

    @GetMapping("/dataset/search")
    fun search(model: Model,
               @RequestParam("query", required = false, defaultValue = "bob") query: String) : String{

        try {
            val queryResults = service.searchByAny(query)
            model.addAttribute("queryResults", queryResults)

            return "results"
        } catch (e: Exception) {
            return "error"
        }

    }

    @GetMapping("/")
    fun index(model: Model) : String {
        model.addAttribute("title", "eCommerce")
        service.checkifDBEmpty()
        return "index"
    }

}