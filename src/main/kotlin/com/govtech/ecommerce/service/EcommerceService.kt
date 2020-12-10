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
import java.nio.charset.StandardCharsets
import java.nio.file.Files

@Service
class EcommerceService {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var invoiceRepo : InvoiceRepository

    fun uploadCSV() : List<Invoice> {
        //later add in exception handling for csv
        val csvFile = ResourceUtils.getFile("classpath:dataset/data.csv")

        //Use ISO_8859_1 to cover all character sets
        val csvReader = Files.newBufferedReader(csvFile.toPath(),StandardCharsets.ISO_8859_1)
        val csvParser = CSVParser(csvReader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase().withTrim())

        val invoiceList = mutableListOf<Invoice>()
        var id: Long = 1
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
        }
        invoiceRepo.saveAll(invoiceList)

        return invoiceList
    }

    fun checkifDBEmpty() : Int {
        val results = invoiceRepo.findAll()
        return results.count()
    }

    fun listInvoiceByPage() : Page<Invoice> {
        val twentyPerPage = PageRequest.of(0,20)
        return invoiceRepo.findAll(twentyPerPage)
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