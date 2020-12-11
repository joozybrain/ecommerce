package com.govtech.ecommerce.controller

import com.govtech.ecommerce.model.Invoice
import com.govtech.ecommerce.service.EcommerceService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(EcommerceController::class)
@AutoConfigureMockMvc
internal class EcommerceControllerTest {

    @Autowired
    lateinit var mockMvc : MockMvc

    @MockkBean
    lateinit var mockService : EcommerceService


    @Test
    fun checkUploadEndPoint() {
        val expectedResult = mutableListOf<Invoice>()
        expectedResult.add(Invoice(1))
        expectedResult.add(Invoice(2))


        every { mockService.uploadCSV() } returns expectedResult

        mockMvc.perform(get("/ecommerce/dataset/upload"))
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("2")))

    }

    @Test
    fun checkListEndPoint() {
        val invoiceList = mutableListOf<Invoice>()
        invoiceList.add(Invoice(1))
        invoiceList.add(Invoice(2))
        val expectedResult = PageImpl(invoiceList)

        every { mockService.listInvoiceByPage(10,10) } returns expectedResult
        every { mockService.checkifDBEmpty() } returns 1

        mockMvc.perform(get("/ecommerce/dataset/list"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].id").value(2))
    }

    @Test
    fun checkSearchEndPoint() {
        val invoiceList = mutableListOf<Invoice>()
        invoiceList.add(Invoice(1,"INV01"))

        every { mockService.searchByAny("INV01") } returns invoiceList

        mockMvc.perform(post("/ecommerce/dataset/search")
            .param("query","INV01"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].invoiceNo").value("INV01"))
    }
}

