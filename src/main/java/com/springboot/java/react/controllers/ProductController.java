package com.springboot.java.react.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.java.react.models.ProductModel;
import com.springboot.java.react.models.dto.ProductDto;
import com.springboot.java.react.services.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@CrossOrigin
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
	
//	SEARCH PRODUCT BY DESCRIPTION
	@CrossOrigin
	@GetMapping(path="/getproduct/{page}")
    public Map<String, Object> listproductDescription(@RequestBody ProductModel prodmodel, @PathVariable Integer page) {
		try {
			
//			LIST PRODUCT DESCRIPTIONS
			int perpage = 10;
			int totalrecs = productService.TotalDessRecords(prodmodel.getDescriptions());
			int totalpage = (int) Math.ceil(Math.floor(totalrecs) / perpage);
			int offset = (page - 1) * perpage;
			
			List<ProductDto> products = productService.listProductsDescription(prodmodel.getDescriptions(), perpage, offset);			
		    return Map.of("products", products, "page", page, "totpages", totalpage);
		    	        
		} catch(Exception ex) {
			
			    return Map.of("statuscode", 500, "message","Internal Server Error.\"");
		}	        
		
	}
	
}
