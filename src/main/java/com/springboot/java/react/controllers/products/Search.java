package com.springboot.java.react.controllers.products;

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

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Search Product", description = "Products Search with pagination")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/search")
public class Search {

	@Autowired
	private ProductService productService;
		
//	SEARCH PRODUCT BY DESCRIPTION
	@GetMapping(path="/product/{page}")
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
