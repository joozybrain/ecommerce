package com.govtech.ecommerce.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Invoice (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val invoiceNo: String? = null,
    val stockCode: String? = null,
    val description: String? = null,
    val quantity: String? = null,
    val invoiceDate: String? = null,
    val unitPrice: String? = null,
    val customerId: String? = null,
    val country: String? = null

)