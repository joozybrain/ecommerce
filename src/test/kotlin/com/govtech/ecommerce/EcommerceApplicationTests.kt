package com.govtech.ecommerce

import com.govtech.ecommerce.controller.EcommerceController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EcommerceApplicationTests {

	@Autowired
	lateinit var controller : EcommerceController

	@Test
	fun contextLoads() {
		assertThat(controller).isNotNull()
	}

}
