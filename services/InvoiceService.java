package com.Bootcamp.Project.Application.services;

import com.Bootcamp.Project.Application.entities.Category;
import com.Bootcamp.Project.Application.entities.Invoice;
import com.Bootcamp.Project.Application.repositories.InvoiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository repository;

    public void createJson() {
        try {
            JSONParser parser = new JSONParser();
            JSONArray invoices = (JSONArray) parser.parse(new FileReader("/home/ttn/JsonData/InvoiceData.json"));
            for (Object cat : invoices) {
                ObjectMapper mapper = new ObjectMapper();
                Invoice invoice = mapper.readValue(cat.toString(), Invoice.class);
                repository.save(invoice);
            }
        } catch (Exception e) {
            System.out.println("Invoice is not added");
            e.printStackTrace();
        }

    }
}

