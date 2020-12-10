package com.govtech.ecommerce.repository

import com.govtech.ecommerce.model.Invoice
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<Invoice, Long> {
    fun findByInvoiceNo(invoiceNo: String): Invoice?

}