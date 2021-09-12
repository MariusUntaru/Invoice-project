package com.invoice.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.invoice.rest.controller.InvoiceRestController;

@SpringBootApplication
@ComponentScan(basePackageClasses = InvoiceRestController.class)
public class InvoiceProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceProjectApplication.class, args);
	}

}
