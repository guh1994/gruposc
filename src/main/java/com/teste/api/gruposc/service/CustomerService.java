package com.teste.api.gruposc.service;

import com.teste.api.gruposc.model.Customer;
import com.teste.api.gruposc.validator.RestEntityResponse;

import java.util.List;

public interface CustomerService {

    RestEntityResponse<List<Customer>> getCustomers();

    RestEntityResponse<List<Customer>> getCustomersByRazaoSocial( String razaoSocial);

    RestEntityResponse<Customer> getCustomerByCnpj( Long CNPJ);

    RestEntityResponse<Customer> loginCustomer ( String user, String password);

    RestEntityResponse<Customer> createCustomer(Customer customer);

    RestEntityResponse<Customer> updateCustomer(Customer customer);

    RestEntityResponse<Customer> deleteCustomer( Integer id);


}
