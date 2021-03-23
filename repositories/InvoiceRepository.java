package com.Bootcamp.Project.Application.repositories;

import com.Bootcamp.Project.Application.entities.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice,Integer> {
}
