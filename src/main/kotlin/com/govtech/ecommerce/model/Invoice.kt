package com.govtech.ecommerce.model

import java.time.LocalDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Invoice (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val invoiceNo: String,
    val stockCode: String,
    val description: String,
    val quantity: Int,
    val invoiceDate: LocalDate,
    val unitPrice: Double,
    val customerId: String,
    val country: String

)