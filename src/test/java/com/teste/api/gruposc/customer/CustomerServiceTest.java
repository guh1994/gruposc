package com.teste.api.gruposc.customer;

import com.teste.api.gruposc.model.Customer;
import com.teste.api.gruposc.repository.CustomerRepository;
import com.teste.api.gruposc.service.CustomerServiceImpl;
import com.teste.api.gruposc.validator.RestEntityResponse;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Rule;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerServiceTest {

    public static final Integer ID = 1;
    public static final String CNPJ = "11222333000055";
    public static final String RAZAO_SOCIAL = "Teste Grupo SC";
    public static final String LOGIN = "teste";
    public static final String PASSWORD = "teste";
    public static final Integer STATUS_ACTIVATED = 1;
    public static final Integer STATUS_DISABLED = 2;

    @InjectMocks
    private CustomerServiceImpl subject;

    @Mock
    private Customer customer;

    @Mock
    private CustomerRepository repository;

    @BeforeEach
    public void setup() {
        Mockito.when(customer.getId()).thenReturn(ID);
        Mockito.when(customer.getCnpj()).thenReturn(CNPJ);
        Mockito.when(customer.getRazaoSocial()).thenReturn(RAZAO_SOCIAL);
        Mockito.when(customer.getLogin()).thenReturn(LOGIN);
        Mockito.when(customer.getPassword()).thenReturn(PASSWORD);
        Mockito.when(repository.findAll()).thenReturn(List.of(customer));
        Mockito.when(repository.findCustomersByRazaoSocial(RAZAO_SOCIAL)).thenReturn(List.of((customer)));
        Mockito.when(repository.findCustomerByCnpj(CNPJ)).thenReturn(customer);
        Mockito.when(repository.findCustomerByLoginAndPassword(LOGIN, PASSWORD)).thenReturn(customer);
        Mockito.when(repository.save(any())).thenReturn(customer);
    }


    @Test
    public void shouldGetCustomer() {

        Mockito.when(customer.getStatus()).thenReturn(STATUS_ACTIVATED);

        RestEntityResponse<List<Customer>> customers = subject.getCustomers();

        assertEquals(1, customers.getEntity().size());

        Customer customerTest = customers.getEntity().get(0);
        assertEquals(ID, customerTest.getId());
        assertEquals(CNPJ, customerTest.getCnpj());
        assertEquals(RAZAO_SOCIAL, customerTest.getRazaoSocial());
        assertEquals(LOGIN, customerTest.getLogin());
        assertEquals(PASSWORD, customerTest.getPassword());
        assertEquals(STATUS_ACTIVATED, customerTest.getStatus());
    }

    @Test
    public void shouldReturnMessageCustomersDoesntExist() {

    }


    @Test
    public void shouldGetCustomersByRazaoSocial() {

    }

    @Test
    void shouldReturnMessageCustomersNotFound() {

    }

    @Test
    public void shouldGetCustomerByCnpj() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerNotFound() {

    }

    @Test
    public void shouldLoginCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageUserNotFound() {

    }

    @Test
    public void shouldReturnErrorMessageUserDisabled() {

    }

    @Test
    public void shouldCreateCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerLoginIsNullWhenCreateCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerPasswordIsNullWhenCreateCustomer() {

    }


    @Test
    public void shouldReturnmessageCustomerCnpjIsNullWhenCreateCustomer() {

    }

    @Test
    public void shouldReturnMessageCustomerRazaoSocialIsNullWhenCreateCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageWhenCustomerAlreadyExists() {

    }

    @Test
    public void shouldUpdateCustomer() {

    }

    @Test
    public void shoulReturnErrorMessageWhenCustomerIdIsNullWhenUpdateCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerLoginIsNullWhenUpdateCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerPasswordIsNullWhenUpdateCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerCnpjIsNullWhenUpdateCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerRazaoSocialIsNullWhenUpdateCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerAlreadyExistWhenUpdateCustomer() {

    }

    @Test
    public void shouldDeleteCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerIdIsNullWhenDeleteCustomer() {

    }

    @Test
    public void shouldReturnErrorMessageCustomerIsNotFoundWhenDeleteCustomer() {

    }
}
