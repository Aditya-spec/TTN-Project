package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Invoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
    @Query(value = "select * from invoice i where i.customer_id=:customerId", nativeQuery = true)
    List<Invoice> fetchByCustomerId(@Param("customerId") Long customerId, Pageable pageable);
}
