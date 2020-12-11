package com.govtech.ecommerce.controller

import com.govtech.ecommerce.model.Invoice
import com.govtech.ecommerce.repository.InvoiceRepository
import com.govtech.ecommerce.service.EcommerceService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageImpl
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.ResourceUtils
import reactor.core.publisher.Flux
import java.nio.charset.StandardCharsets
import java.nio.file.Files

@ExtendWith(SpringExtension::class)
@WebMvcTest(EcommerceController::class)
@AutoConfigureMockMvc
internal class EcommerceControllerTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun service() = mockk<EcommerceService>()
        @Bean
        fun repo() = mockk<InvoiceRepository>()
    }

    @Autowired
    lateinit var mockMvc : MockMvc

    @Autowired
    lateinit var mockService : EcommerceService


    @Test
    fun checkUploadEndPoint() {
        val expectedResult = Flux.just("result")


        val csvByte = ByteArray(1)
        val csv = MockMultipartFile("uploadCSV",csvByte)

        val csvFile = ResourceUtils.getFile("classpath:dataset/data.csv")
        val csvReader = Files.newBufferedReader(csvFile.toPath(), StandardCharsets.ISO_8859_1)

        every { mockService.saveCSV(csvReader) } returns expectedResult
        every { mockService.uploadCSV(csv) } returns csvReader

        mockMvc.perform(multipart("/dataset/upload")
            .file(csv))
            .andExpect(status().isOk)

    }

    @Test
    fun checkListEndPoint() {
        val invoiceList = mutableListOf<Invoice>()
        invoiceList.add(Invoice(1))
        invoiceList.add(Invoice(2))
        val expectedResult = PageImpl(invoiceList)

        every { mockService.listInvoiceByPage(0,5) } returns expectedResult
        every { mockService.checkifDBEmpty() } returns 1

        mockMvc.perform(get("/dataset/list"))
            .andExpect(status().isOk)
    }

    @Test
    fun checkSearchEndPoint() {
        val invoiceList = mutableListOf<Invoice>()
        invoiceList.add(Invoice(1,"INV01"))

        every { mockService.searchByAny("INV01") } returns invoiceList

        mockMvc.perform(get("/dataset/search")
            .param("query","INV01"))
            .andExpect(status().isOk)
    }
}

