package com.teste.api.gruposc.controller;

import com.teste.api.gruposc.model.Customer;
import com.teste.api.gruposc.service.CustomerServiceImpl;
import com.teste.api.gruposc.validator.RestEntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerServiceImpl service;


    @GetMapping
    public ResponseEntity<RestEntityResponse<List<Customer>>> getCustomers() {

        RestEntityResponse<List<Customer>> response = service.getCustomers();
        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/razao")
    public ResponseEntity<RestEntityResponse<List<Customer>>> getCustomerByRazaoSocial(
            @RequestParam(name = "razaoSocial") String razaoSocial) {

        RestEntityResponse<List<Customer>> response = service.getCustomersByRazaoSocial(razaoSocial);
        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("{cnpj}")
    public ResponseEntity<RestEntityResponse<Customer>> getCustomerByRazaoSocial(
            @PathVariable(name = "cnpj") Long cnpj) {

        RestEntityResponse<Customer> response = service.getCustomerByCnpj(cnpj);

        if(!response.isSuccess()){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/login")
    public ResponseEntity<RestEntityResponse<Customer>> login(
            @RequestBody Customer customer) {

        RestEntityResponse<Customer> response = service.loginCustomer(customer.getLogin(), customer.getPassword());

        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RestEntityResponse<Customer>> createCustomer(
            @RequestBody Customer customer
    ) {
        RestEntityResponse<Customer> response = service.createCustomer(customer);

        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RestEntityResponse<Customer>> updateCustomer(
            @RequestBody Customer customer
    ) {
        RestEntityResponse<Customer> response = service.createCustomer(customer);

        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RestEntityResponse<Customer>> deleteCustomer(
            @PathVariable(name = "id") Integer id
    ) {
        RestEntityResponse<Customer> response = service.deleteCustomer(id);
        if (!response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
