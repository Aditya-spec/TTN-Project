package com.Bootcamp.Project.Application.entities;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Component
@PrimaryKeyJoinColumn(name = "id")
public class Seller extends User {

    @Column(unique = true)
    private String gstNumber;
    @Column(unique = true)
    private String companyContact;
    @Column(unique = true)
    private String companyName;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    private Set<Product> products;



    //Constructors

    public Seller() {
    }

    public Seller(long id) {
        this.setId(id);
    }

    //Getters

    public Address getAddress() {
        return address;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    //Setters


    public void setAddress(Address address) {
        address.setUser(this);
        this.address = address;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setProduct(Product product) {
        if (product != null) {
            if (products == null) {
                products = new HashSet<>();
            }
            product.setSeller(this);
            this.getProducts().add(product);
        }
    }
}
