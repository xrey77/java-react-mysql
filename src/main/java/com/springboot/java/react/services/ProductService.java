package com.springboot.java.react.services;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.java.react.entities.Products;
import com.springboot.java.react.models.dto.ProductDto;
import com.springboot.java.react.repositories.ProductRepository;

@Service
@Transactional
public class ProductService {
	
	@Autowired
    private ProductRepository productRepository;
	
    @Autowired
    private ModelMapper modelMapper;
        	
    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
          .stream()
          .map(element -> modelMapper.map(element, targetClass))
          .collect(Collectors.toList());
    }    
    
    public int TotalRecords() {
    	return (int) productRepository.count();    
    }
    
    public int TotalDessRecords(String desc) {
    	return productRepository.getProductDesc(desc);
    }
    
    
    public List<ProductDto> listAllProducts(int limit, int offset) {
      List<Products> products = productRepository.paginateProducts(limit, offset);
      List<ProductDto> productDtoList = mapList(products, ProductDto.class);      
      return productDtoList;
    }
    
    public List<ProductDto> listProductsDescription(String desc,int limit, int offset) {
        List<Products> products = productRepository.paginateByDescription(desc, limit, offset);
        List<ProductDto> productDtoList = mapList(products, ProductDto.class);      
        return productDtoList;
      }
    
    
    public ProductDto saveProduct(ProductDto productDto) {
    	Products product = mapToEntity(productDto);
    	Products newProduct = productRepository.save(product);
    	ProductDto productResponse = mapToDto(newProduct);
    	return productResponse;
    }
    
    public ProductDto getUser(Integer id) {
    	Products products = productRepository.findById(id).get();
    	ProductDto productdto = mapToDto(products);
        return productdto;        
    }    
        
    public Boolean deleteProduct(Integer id) {
        productRepository.deleteByProduct(id);
        return true;
    }
    
    private ProductDto mapToDto(Products product) {
    	ProductDto productDto = modelMapper.map(product, ProductDto.class);
    	return productDto;
    }
    
    private Products mapToEntity(ProductDto productDto) {
    	Products products = modelMapper.map(productDto, Products.class);
    	return products;
    }
    

}
