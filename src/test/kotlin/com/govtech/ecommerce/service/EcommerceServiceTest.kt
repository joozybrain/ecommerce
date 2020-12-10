package com.govtech.ecommerce.service

import com.govtech.ecommerce.repository.InvoiceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EcommerceServiceTest {

    @Autowired
    lateinit var service : EcommerceService
    @Autowired
    lateinit var invoiceRepo : InvoiceRepository

    @BeforeAll
    fun init() {
        service.uploadCSV()
    }

    @Test
    fun `check loadCSV is able to parse and save CSV into database`() {

        val result = invoiceRepo.findById(1)
        assertThat(result.isPresent).isEqualTo(true)
    }

    @Test
    fun `check able to return a page after uploading`() {
        val page = service.listInvoiceByPage()
        assertThat(page.size).isEqualTo(20)
    }

}