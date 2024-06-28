package com.teste.api.gruposc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table( name = "customer" )
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cnpj")
    private Long cnpj;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;

    public void update(Customer customer){
        this.cnpj = customer.getCnpj();
        this.login = customer.getLogin();
        this.password = customer.getPassword();
        this.razaoSocial = customer.getRazaoSocial();
    }
}
