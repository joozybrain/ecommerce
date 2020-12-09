package com.govtech.ecommerce.service

import com.govtech.ecommerce.model.Invoice
import com.govtech.ecommerce.repository.InvoiceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class EcommerceServiceTest {

    @Autowired
    lateinit var service : EcommerceService
    @Autowired
    lateinit var invoiceRepo : InvoiceRepository

    @Test
    fun `check loadCSV is able to parse and save CSV into database`() {
        val invoiceList = service.loadCSV()

        val result = invoiceRepo.findById(1)
        assertThat(result.isPresent).isEqualTo(true)
    }

}