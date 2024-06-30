package com.teste.api.gruposc.customer;

import com.teste.api.gruposc.model.Customer;
import com.teste.api.gruposc.repository.CustomerRepository;
import com.teste.api.gruposc.service.CustomerServiceImpl;
import com.teste.api.gruposc.validator.RestEntityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    private Customer updatedCustomer;

    @Mock
    private CustomerRepository repository;

    @BeforeEach
    public void setup() {
        Mockito.when(customer.getId()).thenReturn(ID);
        Mockito.when(customer.getCnpj()).thenReturn(CNPJ);
        Mockito.when(customer.getRazaoSocial()).thenReturn(RAZAO_SOCIAL);
        Mockito.when(customer.getLogin()).thenReturn(LOGIN);
        Mockito.when(customer.getPassword()).thenReturn(PASSWORD);
        Mockito.when(customer.getStatus()).thenReturn(STATUS_ACTIVATED);
        Mockito.when(repository.findAll()).thenReturn(List.of(customer));
        Mockito.when(repository.findCustomersByRazaoSocial(RAZAO_SOCIAL)).thenReturn(List.of((customer)));
        Mockito.when(repository.findCustomerByCnpj(CNPJ)).thenReturn(customer);
        Mockito.when(repository.findCustomerByLoginAndPassword(LOGIN, PASSWORD)).thenReturn(customer);
        Mockito.when(repository.save(any())).thenReturn(customer);
        Mockito.when(repository.existsByRazaoSocial(customer.getRazaoSocial())).thenReturn(true);
        Mockito.when(repository.existsByCnpj(customer.getCnpj())).thenReturn(true);
        Mockito.when(repository.existsById(customer.getId())).thenReturn(true);
    }


    @Test
    public void shouldGetCustomer() {


        RestEntityResponse<List<Customer>> response = subject.getCustomers();

        assertEquals(1, response.getEntity().size());

        Customer customerTest = response.getEntity().get(0);
        assertEquals(ID, customerTest.getId());
        assertEquals(CNPJ, customerTest.getCnpj());
        assertEquals(RAZAO_SOCIAL, customerTest.getRazaoSocial());
        assertEquals(LOGIN, customerTest.getLogin());
        assertEquals(PASSWORD, customerTest.getPassword());
        assertEquals(STATUS_ACTIVATED, customerTest.getStatus());
        assertTrue(response.isSuccess());

    }


    @Test
    public void shouldReturnErrorMessageCustomersDoesntExist() {

        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        RestEntityResponse<List<Customer>> response = subject.getCustomers();

        assertEquals(List.of("Customers doesn't exists"), response.getMessages());
        assertFalse(response.isSuccess());
    }


    @Test
    public void shouldGetCustomersByRazaoSocial() {

        RestEntityResponse<List<Customer>> response = subject.getCustomersByRazaoSocial(customer.getRazaoSocial());

        Customer customerTest = response.getEntity().get(0);
        assertEquals(ID, customerTest.getId());
        assertEquals(CNPJ, customerTest.getCnpj());
        assertEquals(RAZAO_SOCIAL, customerTest.getRazaoSocial());
        assertEquals(LOGIN, customerTest.getLogin());
        assertEquals(PASSWORD, customerTest.getPassword());
        assertEquals(STATUS_ACTIVATED, customerTest.getStatus());
        assertTrue(response.isSuccess());
    }

    @Test
    void shouldReturnErrorMessageCustomersNotFoundWhenFindCustomerByRazaoSocial() {

        Mockito.when(repository.existsByRazaoSocial(customer.getRazaoSocial())).thenReturn(false);

        RestEntityResponse<List<Customer>> response = subject.getCustomersByRazaoSocial(customer.getRazaoSocial());

        assertEquals(List.of("Customers not found"), response.getMessages());
        assertFalse(response.isSuccess());

    }

    @Test
    public void shouldGetCustomerByCnpj() {

        RestEntityResponse<Customer> response = subject.getCustomerByCnpj(customer.getCnpj());

        assertTrue(response.isSuccess());
        assertEquals(ID, response.getEntity().getId());
        assertEquals(CNPJ, response.getEntity().getCnpj());
        assertEquals(RAZAO_SOCIAL, response.getEntity().getRazaoSocial());
        assertEquals(LOGIN, response.getEntity().getLogin());
        assertEquals(PASSWORD, response.getEntity().getPassword());
        assertEquals(STATUS_ACTIVATED, response.getEntity().getStatus());
    }

    @Test
    public void shouldReturnErrorMessageCustomerNotFoundWhenGetCustomerByCnpj() {
        Mockito.when(repository.existsByCnpj(customer.getCnpj())).thenReturn(false);

        RestEntityResponse<Customer> response = subject.getCustomerByCnpj(customer.getCnpj());

        assertEquals(List.of("Customer not found"), response.getMessages());
        assertFalse(response.isSuccess());

    }

    @Test
    public void shouldLoginCustomer() {

        RestEntityResponse<Customer> response = subject.loginCustomer(customer.getLogin(), customer.getPassword());

        assertTrue(response.isSuccess());
        assertEquals(ID, response.getEntity().getId());
        assertEquals(CNPJ, response.getEntity().getCnpj());
        assertEquals(RAZAO_SOCIAL, response.getEntity().getRazaoSocial());
        assertEquals(LOGIN, response.getEntity().getLogin());
        assertEquals(PASSWORD, response.getEntity().getPassword());
        assertEquals(STATUS_ACTIVATED, response.getEntity().getStatus());

    }

    @Test
    public void shouldReturnErrorMessageUserNotFoundWhenLoginCustomer() {

        RestEntityResponse<Customer> response = subject.loginCustomer(null, null);

        assertFalse(response.isSuccess());
        assertEquals(List.of("User not found"), response.getMessages());
    }

    @Test
    public void shouldReturnErrorMessageUserDisabledWhenLoginCustomer() {

        Mockito.when(customer.getStatus()).thenReturn(STATUS_DISABLED);

        RestEntityResponse<Customer> response = subject.loginCustomer(customer.getLogin(), customer.getPassword());

        assertEquals(List.of("User disabled"), response.getMessages());
        assertFalse(response.isSuccess());


    }

    @Test
    public void shouldCreateCustomer() {

        Mockito.when(repository.existsByCnpj(customer.getCnpj())).thenReturn(false);
        RestEntityResponse<Customer> response = subject.createCustomer(customer);

        assertTrue(response.isSuccess());
        assertEquals(ID, response.getEntity().getId());
        assertEquals(CNPJ, response.getEntity().getCnpj());
        assertEquals(RAZAO_SOCIAL, response.getEntity().getRazaoSocial());
        assertEquals(LOGIN, response.getEntity().getLogin());
        assertEquals(PASSWORD, response.getEntity().getPassword());
        assertEquals(STATUS_ACTIVATED, response.getEntity().getStatus());

    }

    @Test
    public void shouldReturnErrorMessageCustomerLoginIsNullWhenCreateCustomer() {
        Mockito.when(customer.getLogin()).thenReturn(null);

        RestEntityResponse<Customer> response = subject.createCustomer(customer);

        assertEquals(List.of("Login is null"), response.getMessages());
        assertFalse(response.isSuccess());

    }

    @Test
    public void shouldReturnErrorMessageCustomerPasswordIsNullWhenCreateCustomer() {
        Mockito.when(customer.getPassword()).thenReturn(null);

        RestEntityResponse<Customer> response = subject.createCustomer(customer);

        assertEquals(List.of("Password is null"), response.getMessages());
        assertFalse(response.isSuccess());
    }


    @Test
    public void shouldReturnmessageCustomerCnpjIsNullWhenCreateCustomer() {
        Mockito.when(customer.getCnpj()).thenReturn(null);
        RestEntityResponse<Customer> response = subject.createCustomer(customer);

        assertEquals(List.of("CNPJ is null"), response.getMessages());
        assertFalse(response.isSuccess());
    }

    @Test
    public void shouldReturnMessageCustomerRazaoSocialIsNullWhenCreateCustomer() {
        Mockito.when(customer.getRazaoSocial()).thenReturn(null);
        RestEntityResponse<Customer> response = subject.createCustomer(customer);

        assertEquals(List.of("Razao Social is null"), response.getMessages());
        assertFalse(response.isSuccess());
    }

    @Test
    public void shouldReturnErrorMessageWhenCustomerAlreadyExists() {

        RestEntityResponse<Customer> response = subject.createCustomer(customer);

        assertEquals(List.of("Customer Already exist"), response.getMessages());
        assertFalse(response.isSuccess());
    }

    @Test
    public void shouldUpdateCustomer() {
        Customer updatedCustomerTest = new Customer();
        updatedCustomerTest.setId(1);
        updatedCustomerTest.setCnpj("11222333000055");
        updatedCustomerTest.setRazaoSocial("Teste Grupo SC");
        updatedCustomerTest.setLogin("teste2");
        updatedCustomerTest.setPassword("teste2");
        updatedCustomerTest.setStatus(STATUS_ACTIVATED);

        Mockito.when( repository.save( customer ) ).thenReturn( updatedCustomer );
        Mockito.when( updatedCustomer.getId() ).thenReturn( updatedCustomerTest.getId() );
        Mockito.when( updatedCustomer.getCnpj() ).thenReturn( updatedCustomerTest.getCnpj() );
        Mockito.when( updatedCustomer.getRazaoSocial() ).thenReturn( updatedCustomerTest.getRazaoSocial() );
        Mockito.when( updatedCustomer.getLogin() ).thenReturn( updatedCustomerTest.getLogin());
        Mockito.when( updatedCustomer.getPassword() ).thenReturn( updatedCustomerTest.getPassword());
        Mockito.when( updatedCustomer.getStatus() ).thenReturn( updatedCustomerTest.getStatus());

        RestEntityResponse<Customer> response = subject.updateCustomer(updatedCustomerTest);

        Mockito.verify( customer ).update( updatedCustomerTest );
        Mockito.verify( repository ).save( customer );

        assertTrue( response.isSuccess() );
        assertEquals(updatedCustomerTest.getId(),response.getEntity().getId());
        assertEquals(updatedCustomerTest.getCnpj(),response.getEntity().getCnpj());
        assertEquals(updatedCustomerTest.getRazaoSocial(),response.getEntity().getRazaoSocial());
        assertEquals(updatedCustomerTest.getLogin(),response.getEntity().getLogin());
        assertEquals(updatedCustomerTest.getPassword(),response.getEntity().getPassword());
        assertEquals(updatedCustomerTest.getStatus(),response.getEntity().getStatus());

    }

    @Test
    public void shoulReturnErrorMessageWhenCustomerIdIsNullWhenUpdateCustomer() {

        Mockito.when(customer.getId()).thenReturn(null);

        RestEntityResponse<Customer> response = subject.updateCustomer(customer);

        assertEquals(List.of("Id is null"), response.getMessages());
        assertFalse(response.isSuccess());
    }

    @Test
    public void shouldReturnErrorMessageCustomerLoginIsNullWhenUpdateCustomer() {

        Mockito.when(customer.getLogin()).thenReturn(null);

        RestEntityResponse<Customer> response = subject.updateCustomer(customer);
        assertEquals(List.of("Login is null"), response.getMessages());
        assertFalse(response.isSuccess());
    }

    @Test
    public void shouldReturnErrorMessageCustomerPasswordIsNullWhenUpdateCustomer() {
        Mockito.when(customer.getPassword()).thenReturn(null);

        RestEntityResponse<Customer> response = subject.updateCustomer(customer);
        assertEquals(List.of("Password is null"), response.getMessages());
        assertFalse(response.isSuccess());
    }

    @Test
    public void shouldReturnErrorMessageCustomerCnpjIsNullWhenUpdateCustomer() {
        Mockito.when(customer.getCnpj()).thenReturn(null);

        RestEntityResponse<Customer> response = subject.updateCustomer(customer);
        assertEquals(List.of("CNPJ is null"), response.getMessages());
        assertFalse(response.isSuccess());
    }

    @Test
    public void shouldReturnErrorMessageCustomerRazaoSocialIsNullWhenUpdateCustomer() {
        Mockito.when(customer.getRazaoSocial()).thenReturn(null);

        RestEntityResponse<Customer> response = subject.updateCustomer(customer);
        assertEquals(List.of("Razao Social is null"), response.getMessages());
        assertFalse(response.isSuccess());
    }


    @Test
    public void shouldDeleteCustomer() {

        RestEntityResponse<Customer> response = subject.deleteCustomer(customer.getId());

        assertTrue(response.isSuccess());
        assertEquals(List.of("Customer Deleted"), response.getMessages());
    }

    @Test
    public void shouldReturnErrorMessageCustomerIdIsNullWhenDeleteCustomer() {

        RestEntityResponse<Customer> response = subject.deleteCustomer(null);

        assertFalse(response.isSuccess());
        assertEquals(List.of("Id is null"), response.getMessages());
    }

    @Test
    public void shouldReturnErrorMessageCustomerIsNotFoundWhenDeleteCustomer() {
        Mockito.when(repository.existsById(customer.getId())).thenReturn(false);

        RestEntityResponse<Customer> response = subject.deleteCustomer(customer.getId());
        assertFalse(response.isSuccess());
        assertEquals(List.of("Customer not Found"), response.getMessages());
    }
}
