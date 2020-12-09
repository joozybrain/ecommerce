package com.govtech.ecommerce.service

import com.govtech.ecommerce.model.Invoice
import com.govtech.ecommerce.repository.InvoiceRepository
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.nio.charset.StandardCharsets
import java.nio.file.Files

@Service
class EcommerceService {

    @Autowired
    lateinit var invoiceRepo : InvoiceRepository

    fun loadCSV() : List<Invoice> {
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
            invoiceRepo.save(invoiceRecord)
        }

        return invoiceList
    }

}