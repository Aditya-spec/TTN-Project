package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long> {

    Product findByName(String productName);

    @Query(value = "select * from product p where p.brand=:brand and category_id=:categoryId and seller_id=:sellerId ", nativeQuery = true)
    List<Product> fetchProduct(@Param("brand") String brand, @Param("categoryId") Long id, @Param("sellerId") Long id1);

    @Query(value = "select * from product p where p.seller_id=:id and p.name=:name",nativeQuery = true)
    Product fetchProductForUpdate(@Param("id") Long id,@Param("name") String name);

    @Query(value = "from Product p where p.seller=:sellerId and p.name=:name")
    Optional<Product> fetchProductName(@Param("sellerId") Long id, @Param("name") String name);

    @Query(value = "select * from product p where p.seller_id=:sellerId",nativeQuery = true)
    List<Product> fetchBySellerId(@Param("sellerId") Long id, Pageable sortById);
}
