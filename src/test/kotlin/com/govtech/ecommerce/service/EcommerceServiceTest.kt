package com.govtech.ecommerce.service

import com.govtech.ecommerce.repository.InvoiceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.util.ResourceUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.charset.StandardCharsets
import java.nio.file.Files

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EcommerceServiceTest {

    @Autowired
    lateinit var service : EcommerceService
    @Autowired
    lateinit var invoiceRepo : InvoiceRepository

    @BeforeAll
    fun init() {
        val csvFile = ResourceUtils.getFile("classpath:dataset/data.csv")
        val csvReader = Files.newBufferedReader(csvFile.toPath(), StandardCharsets.ISO_8859_1)
        service.saveCSV(csvReader)
    }

    @Test
    fun `check uploadCSV is able to save to directory`() {
        val csvByte = ByteArray(1)
        val csv = MockMultipartFile("mockCSV",csvByte)
        service.uploadCSV(csv)
    }

    @Test
    fun `check loadCSV is able to parse and save CSV into database`() {

        val result = invoiceRepo.findById(1)
        assertThat(result.isPresent).isEqualTo(true)
    }

    @Test
    fun `check able to return a page after uploading`() {
        val page = service.listInvoiceByPage(20,20)
        assertThat(page.size).isEqualTo(20)
    }

    @Test
    fun `check that searchByAny can return results`() {
        val result = service.searchByAny("France")
        assertThat(result.size).isGreaterThan(0)
    }

}