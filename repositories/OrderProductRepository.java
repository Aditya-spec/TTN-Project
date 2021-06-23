package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.OrderProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends CrudRepository<OrderProduct,Long> {
    @Query(value = "select * from order_product o where o.invoice_id=:orderId",nativeQuery = true)
    List<OrderProduct> fetchByOrderId(@Param("orderId") Long orderId, Pageable pageable);

    @Query("from OrderProduct ord where ord.productVariation.product.seller.id= :sellerId")
    List<OrderProduct> fetchAllSellerOrders(@Param("sellerId") long sellerId, Pageable pageable);

    @Query("from OrderProduct")
    List<OrderProduct> fetchAll(Pageable pageable);
}
