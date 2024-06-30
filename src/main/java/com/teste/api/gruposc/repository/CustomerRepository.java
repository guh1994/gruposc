package com.teste.api.gruposc.repository;

import com.teste.api.gruposc.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findCustomersByRazaoSocial(String razaoSocial);

    Customer findCustomerByCnpj(String cnpj);

    Customer findCustomerByLoginAndPassword(String login, String password);

    boolean existsByCnpj(String cnpj);

    boolean existsByRazaoSocial(String razaoSocial);
}
