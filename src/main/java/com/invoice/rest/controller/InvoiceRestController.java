package com.invoice.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.rest.entity.Invoice;
import com.invoice.rest.exception.InvoiceNotFoundException;
import com.invoice.rest.service.IInvoiceService;
import com.invoice.rest.util.InvoiceUtil;

@ComponentScan(basePackageClasses = IInvoiceService.class)
@RestController
@RequestMapping("/invoice/rest")
public class InvoiceRestController {
	
	@Autowired
	private IInvoiceService service;  
	
	@Autowired
	private InvoiceUtil util;
	
	/**
	 * Takes Invoice Object as input and returns save Status as ResponseEntity<String>
	 */
	@PostMapping("/saveInvoice")
	public ResponseEntity<String> saveInvoice(@RequestBody Invoice inv){
		ResponseEntity<String> resp = null;
		try{
			Long id = service.saveInvoice(inv);
			resp= new ResponseEntity<String>(
					"Invoice '"+id+"' created",HttpStatus.CREATED); //201-created
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to save Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR); //500-Internal Server Error
		}
		return resp;
	}
	
	/**
	 * To retrieve all Invoices, returns data retrieval Status as ResponseEntity<?>
	 */
	@GetMapping("/getAllInvoices")
	public ResponseEntity<?> getAllInvoices() {
		ResponseEntity<?> resp=null;
		try {
			List<Invoice> list= service.getAllInvoices();
			resp= new ResponseEntity<List<Invoice>>(list,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to get Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	/**
	 * To retrieve one Invoice by providing id, returns Invoice object & Status as ResponseEntity<?>
	 */
	@GetMapping("/find/{id}")
	public ResponseEntity<?> getOneInvoice(@PathVariable Long id){
		ResponseEntity<?> resp= null;
		try {
			Invoice inv= service.getOneInvoice(id);
			resp= new ResponseEntity<Invoice>(inv,HttpStatus.OK);
		}catch (InvoiceNotFoundException nfe) {
			throw nfe; 
		}catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to find Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	/**
	 * To delete one Invoice by providing id, returns Status as ResponseEntity<String>
	 */
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<String> deleteInvoice(@PathVariable Long id){
		
		ResponseEntity<String> resp= null;
		try {
			service.deleteInvoice(id);
			resp= new ResponseEntity<String> (
					"Invoice '"+id+"' deleted",HttpStatus.OK);
			
		} catch (InvoiceNotFoundException nfe) {
			throw nfe;
		} catch (Exception e) {
			e.printStackTrace();
			resp= new ResponseEntity<String>(
					"Unable to delete Invoice", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return resp;
	}
	
	/**
	 * To modify one Invoice by providing id, updates Invoice object & returns Status as ResponseEntity<String>
	 */
	@PutMapping("/modify/{id}")
	public ResponseEntity<String> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice){
		
		ResponseEntity<String> resp = null;
		try {
			//db Object
			Invoice inv= service.getOneInvoice(id);
			//copy non-null values from request to Database object
			util.copyNonNullValues(invoice, inv);
			//finally update this object
			service.updateInvoice(inv);
			resp = new ResponseEntity<String>(
					//"Invoice '"+id+"' Updated",
					HttpStatus.RESET_CONTENT); //205- Reset-Content(PUT)
			
		} catch (InvoiceNotFoundException nfe) {
			throw nfe; // re-throw exception to handler
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to Update Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR); //500-ISE
		}
		return resp;
	}
	
	/**
	 * To update one Invoice just like where clause condition, updates Invoice object & returns Status as ResponseEntity<String>
	 */
	@PatchMapping("/modify/{id}/{number}")
	public ResponseEntity<String> updateInvoiceNumberById(
			@PathVariable Long id,
			@PathVariable String number
			) 
	{
		ResponseEntity<String> resp = null;
		try {
			service.updateInvoiceNumberById(number, id);
			resp = new ResponseEntity<String>(
					"Invoice '"+number+"' Updated",
					HttpStatus.PARTIAL_CONTENT); //206- Reset-Content(PUT)
			
		} catch(InvoiceNotFoundException pne) {
			throw pne; // re-throw exception to handler
		} catch (Exception e) {
			e.printStackTrace();
			resp = new ResponseEntity<String>(
					"Unable to Update Invoice", 
					HttpStatus.INTERNAL_SERVER_ERROR); //500-ISE
		}
		return resp;
	}
}