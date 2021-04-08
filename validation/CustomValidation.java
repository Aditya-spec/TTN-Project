package com.Bootcamp.Project.Application.validation;


import com.Bootcamp.Project.Application.dtos.CustomerRegistrationDTO;
import com.Bootcamp.Project.Application.dtos.SellerRegistrationDTO;
import com.Bootcamp.Project.Application.entities.Customer;
import com.Bootcamp.Project.Application.entities.Seller;
import com.Bootcamp.Project.Application.enums.ErrorCode;
import com.Bootcamp.Project.Application.enums.Label;
import com.Bootcamp.Project.Application.exceptionHandling.EcommerceException;
import com.Bootcamp.Project.Application.repositories.CustomerRepository;
import com.Bootcamp.Project.Application.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Stream;

@Component
public class CustomValidation {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    CustomerRepository customerRepository;

    public String checkSellerValidation(SellerRegistrationDTO sellerRegistrationDTO) {
        List<Seller> sellerList = sellerRepository.fetchAllSeller();

        if (!sellerRegistrationDTO.getPassword().equals(sellerRegistrationDTO.getConfirmPassword())) {

            return " error: password and confirm-password do not match";
        }

        for (Seller seller : sellerList) {
            if (sellerRegistrationDTO.getEmail().equals(seller.getEmail())) {

                return "error: email id already exist";
            }


            if (sellerRegistrationDTO.getCompanyContact().equals(seller.getCompanyContact())) {
                return "error: contactNumber already exist";
            }

            if (sellerRegistrationDTO.getGstNumber().equals(seller.getGstNumber())) {
                return "seller account with gstNumber" + sellerRegistrationDTO.getGstNumber() + "already exist";
            }
            if (sellerRegistrationDTO.getCompanyName().equals(seller.getCompanyName())) {
                return "seller account with company name" + sellerRegistrationDTO.getCompanyName() + "already exist";
            }
        }

        return null;
    }


    public String checkCustomerValidation(CustomerRegistrationDTO customerRegistrationDTO) {
        List<Customer> customerList = customerRepository.fetchAllCustomer();

        if (!customerRegistrationDTO.getPassword().equals(customerRegistrationDTO.getConfirmPassword())) {

            return "error: password and confirm-password did not match";
        }

        for (Customer customer : customerList) {
            if (customerRegistrationDTO.getEmail().equals(customer.getEmail())) {
                return " error: email id already exist";
            }

            if (customerRegistrationDTO.getContact().equals(customer.getContact())) {
                return " error: contactNumber already exist";
            }

        }
        return null;
    }

    public Label verifyLabel(String label) {

        return Stream.of(Label.values())
                .filter(c -> c.toString().equals(label))
                .findFirst()
                .orElseThrow(() -> {
                    throw new EcommerceException(ErrorCode.LABEL_NOT_CORRECT);
                });
    }
}
