package com.springboot.java.react.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springboot.java.react.entities.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
	
    @Query(value="SELECT * FROM products limit :x1 offset :x2", nativeQuery = true)
    public List<Products> paginateProducts(@Param("x1") int limit,  @Param("x2") int offset);

    @Query(value="SELECT * FROM products WHERE descriptions LIKE %?1% ORDER BY id limit ?2 offset ?3", nativeQuery = true)
    public List<Products> paginateByDescription(String description, int limit, int offset);
	    
    @Query(value="SELECT count(*) FROM products WHERE descriptions LIKE %?1%", nativeQuery = true)
    public int getProductDesc(String description);
    
    
	@Query(value="DELETE FROM Products u WHERE u.id = :id")
    public int deleteByProduct(@Param("id") int id);

}
