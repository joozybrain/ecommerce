package com.govtech.ecommerce.service

import com.govtech.ecommerce.model.Invoice
import com.govtech.ecommerce.repository.InvoiceRepository
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files

@Service
class EcommerceService {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var invoiceRepo : InvoiceRepository

    fun uploadCSV(csv : MultipartFile) : BufferedReader {
        val csvBytes = csv.bytes
        val inputStream = ByteArrayInputStream(csvBytes)
        //Use ISO_8859_1 to cover all character sets
        val buffReader = inputStream.bufferedReader(StandardCharsets.ISO_8859_1)

        return buffReader
    }

    fun saveCSV(buffReader : BufferedReader) : Flux<String> {
        //later add in exception handling for csv
        val csvReader = buffReader
        val csvParser = CSVParser(csvReader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase().withTrim())

        val invoiceList = mutableListOf<Invoice>()
        var id: Long = 1
        var counter: Long = 1
        for (invoice in csvParser) {
            var invoiceRecord = Invoice(id++,
                invoice.get("InvoiceNo"),
                invoice.get("StockCode"),
                invoice.get("Description"),
                invoice.get("Quantity"),
                invoice.get("InvoiceDate"),
                invoice.get("UnitPrice"),
                invoice.get("CustomerID"),
                invoice.get("Country"))
            invoiceList.add(invoiceRecord)
            counter++
            if (counter >= 50000 ) {
                invoiceRepo.saveAll(invoiceList)
                invoiceRepo.flush()
                counter = 0
                invoiceList.clear()
            }
        }
        //clear remaining where counter cannot exceed 20000
        if (counter > 0) {
            invoiceRepo.saveAll(invoiceList)
        }
        /*invoiceRepo.deleteAll()
        invoiceRepo.saveAll(invoiceList)*/
        logger.info("Completed DB loading..."+invoiceRepo.count())
        return Flux.just("Success! "+invoiceRepo.count()+" records inserted.")
    }

    fun checkifDBEmpty() : Int {
        val results = invoiceRepo.findAll()
        if (results.size == 0) {
            val csvFile = ResourceUtils.getFile("classpath:dataset/data.csv")
            val csvReader = Files.newBufferedReader(csvFile.toPath(), StandardCharsets.ISO_8859_1)
            saveCSV(csvReader)
        }

        return results.size
    }

    fun listInvoiceByPage(page: Int, size: Int) : Page<Invoice> {

        val pageSize = PageRequest.of(page, size)
        return invoiceRepo.findAll(pageSize)
    }

    fun searchByAny(param : String) : List<Invoice> {
        val invoiceProbe = Invoice(null,param,param,param,param,param,param,param,param)
        val matcher = ExampleMatcher
            .matchingAny()
            .withIgnoreCase()
            .withIgnorePaths("id")
            .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
            .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)

        return invoiceRepo.findAll(Example.of(invoiceProbe,matcher))
    }

}