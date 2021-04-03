package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends CrudRepository<Seller,Long> {
    @Query("from Seller")
    List<Seller> fetchAllSeller();

    @Query("from Seller")
    List<Seller> fetchSellerByPage(Pageable sortById);


    Seller findByEmail(String email);


}
