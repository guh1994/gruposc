package com.teste.api.gruposc.service;

import com.teste.api.gruposc.model.Customer;
import com.teste.api.gruposc.repository.CustomerRepository;
import com.teste.api.gruposc.validator.RestEntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository repository;

    private static  final Integer ACTIVATED = 1;
    private static  final Integer  DISABLED = 2 ;


    public RestEntityResponse<List<Customer>> getCustomers() {

        List<Customer> customers = repository.findAll();

        if (customers.isEmpty()) {
            return RestEntityResponse.<List<Customer>>builder()
                    .success(false)
                    .messages(List.of("Customers doesn't exists"))
                    .build();
        }
        return RestEntityResponse.<List<Customer>>builder().success(true).entity(customers).build();
    }

    public RestEntityResponse<List<Customer>> getCustomersByRazaoSocial(String razaoSocial) {

        boolean existByRazaoSocial = repository.existsByRazaoSocial(razaoSocial);
        if (!existByRazaoSocial) {
            return RestEntityResponse.<List<Customer>>builder()
                    .success(false)
                    .messages(List.of("Customers not found"))
                    .build();
        }
        List<Customer> customers = repository.findCustomersByRazaoSocial(razaoSocial);

        return RestEntityResponse.<List<Customer>>builder().success(true).entity(customers).build();

    }

    public RestEntityResponse<Customer> getCustomerByCnpj(String cnpj) {

        boolean existByCnpj = repository.existsByCnpj(cnpj);
        if (!existByCnpj) {
            return RestEntityResponse.<Customer>builder()
                    .success(false)
                    .messages(List.of("Customer not found"))
                    .build();

        }
        Customer customer = repository.findCustomerByCnpj(cnpj);

        return RestEntityResponse.<Customer>builder().success(true).entity(customer).build();
    }

    public RestEntityResponse<Customer> loginCustomer(String user, String password) {

        Customer customer = repository.findCustomerByLoginAndPassword(user, password);
        if (customer == null) {
            return RestEntityResponse.<Customer>builder()
                    .success(false)
                    .messages(List.of("User not found"))
                    .build();
        }

        if(customer.getStatus().equals(DISABLED)){
            return RestEntityResponse.<Customer>builder()
                    .success(false)
                    .messages(List.of("User disabled"))
                    .build();
        }

        return RestEntityResponse.<Customer>builder().success(true).messages(List.of("You are logged in")).entity(customer).build();
    }


    public RestEntityResponse<Customer> createCustomer(Customer customer) {


        List<String> validate = validadeCommons(customer);
        if (!validate.isEmpty()) {
            return RestEntityResponse.<Customer>builder()
                    .success(false)
                    .messages(validate)
                    .build();
        }

        boolean existByCnpj = repository.existsByCnpj(customer.getCnpj());
        if (existByCnpj) {
            return RestEntityResponse.<Customer>builder()
                    .success(false)
                    .messages(List.of("Customer Already exist"))
                    .build();
        }

        customer.setStatus(ACTIVATED);
        Customer response = repository.save(customer);

        return RestEntityResponse.<Customer>builder().success(true).entity(response).build();
    }

    public RestEntityResponse<Customer> updateCustomer(Customer customer) {

        if (customer.getId() == null) {
            return RestEntityResponse.<Customer>builder()
                    .success(false)
                    .messages(List.of("Id is null"))
                    .build();
        }

        List<String> validate = validadeCommons(customer);
        if (!validate.isEmpty()) {
            return RestEntityResponse.<Customer>builder()
                    .success(false)
                    .messages(validate)
                    .build();
        }

        Customer customerByCnpj = repository.findCustomerByCnpj(customer.getCnpj());
        if (!customerByCnpj.getLogin().contains(customer.getLogin())) {
            return RestEntityResponse.<Customer>builder().success(false).messages(List.of("Login already exists, you can't change.")).build();
        }

        customerByCnpj.update(customer);
        repository.save(customerByCnpj);

        return RestEntityResponse.<Customer>builder().success(true).entity(customerByCnpj).build();

    }

    public RestEntityResponse<Customer> deleteCustomer(Integer id) {

        if (id == null) {
            return RestEntityResponse.<Customer>builder()
                    .success(false)
                    .messages(List.of("Id is null"))
                    .build();
        }
        boolean existCustomer = repository.existsById(id);
        if (!existCustomer) {
            return RestEntityResponse.<Customer>builder()
                    .success(false)
                    .messages(List.of("Customer not Found"))
                    .build();
        }

        repository.deleteById(id);

        return RestEntityResponse.<Customer>builder().success(true).messages(List.of("Customer Deleted")).build();
    }

    private List<String> validadeCommons(Customer customer) {

        List<String> messages = new ArrayList<>();


        if (customer.getLogin() == null) {

            messages.add("Login is null");
            return messages;
        }
        if (customer.getPassword() == null) {

            messages.add("Password is null");
            return messages;
        }
        if (customer.getCnpj() == null) {
            messages.add("CNPJ is null");
        }
        if (customer.getRazaoSocial() == null) {
            messages.add("Razao Social is null");
        }

        return messages;


    }
}
