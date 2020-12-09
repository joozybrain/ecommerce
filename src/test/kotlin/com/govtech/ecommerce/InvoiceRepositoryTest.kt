package com.govtech.ecommerce

import com.govtech.ecommerce.model.Invoice
import com.govtech.ecommerce.repository.InvoiceRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate

@DataJpaTest
class InvoiceRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val invoiceRepository: InvoiceRepository){

    @Test
    fun `check findByInvoiceNo`() {
        val invoiceToSave = Invoice(0,"IN01","S01","Sample",
        10, LocalDate.now(),1.10,"C01","USA")

        invoiceRepository.save(invoiceToSave)

        val result = invoiceRepository.findByInvoiceNo("IN01")

        assertThat(result?.country).isEqualTo("USA")
        assertThat(result?.description).isEqualTo("Sample")



    }

}