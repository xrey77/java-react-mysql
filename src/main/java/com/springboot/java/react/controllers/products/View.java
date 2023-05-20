package com.springboot.java.react.controllers.products;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.java.react.models.dto.ProductDto;
import com.springboot.java.react.services.ProductService;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "List Producs", description = "Products list with Pagination")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/products")
public class View {

	@Autowired
	private ProductService productService;
	
	@GetMapping(path="/getallproducts/{page}")
    public Map<String, Object> listproducts(@PathVariable Integer page) {
		try {
			
//			LIST PRODUCTS
			int perpage = 10;
			int totalrecs = productService.TotalRecords();
			int totalpage = (int) Math.ceil(Math.floor(totalrecs) / perpage);
			int offset = (page - 1) * perpage;
			
			List<ProductDto> products = productService.listAllProducts(perpage, offset);			
		    return Map.of("products", products, "page", page, "totpages", totalpage);
		    	        
		} catch(Exception ex) {
			
			    return Map.of("statuscode", 500, "message","Internal Server Error.\"");
		}	        
		
	}
	

	
}
