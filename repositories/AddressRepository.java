package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AddressRepository extends JpaRepository<Address,Long> {

  /*  Set<Address> getByCustomerId(Long id);*/

    @Query("from Address a where a.user.id=:userId and a.deleted=FALSE")
    List<Address> fetchAddresses(@Param("userId") Long userId);

    @Query("from Address a where a.id=:addressId and a.deleted=FALSE")
    Address getAddressById(@Param("addressId") Long id);
}
