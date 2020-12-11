package com.govtech.ecommerce

import com.govtech.ecommerce.service.EcommerceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class EcommerceApplication {

	/*@Autowired
	lateinit var service: EcommerceService

	@Bean
	fun loadDB() = CommandLineRunner {
		service.saveCSV()
	}*/
}

fun main(args: Array<String>) {
	runApplication<EcommerceApplication>(*args)


}
