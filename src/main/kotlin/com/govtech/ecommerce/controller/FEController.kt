package com.govtech.ecommerce.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FEController {

    @GetMapping("/")
    fun index(model: Model) : String {
        model.addAttribute("title", "eCommerce")
        return "index"
    }
}