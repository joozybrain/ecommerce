package com.govtech.ecommerce

import com.govtech.ecommerce.service.EcommerceService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class EcommerceApplication {
	@Bean
	fun loadDB(service : EcommerceService) = CommandLineRunner {
			args -> service.uploadCSV()
	}
}

fun main(args: Array<String>) {
	runApplication<EcommerceApplication>(*args)


}
