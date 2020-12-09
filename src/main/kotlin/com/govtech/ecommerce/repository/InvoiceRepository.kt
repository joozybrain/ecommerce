package com.govtech.ecommerce.repository

import com.govtech.ecommerce.model.Invoice
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : PagingAndSortingRepository<Invoice, Long> {
    fun findByInvoiceNo(invoiceNo: String): Invoice?
}