package com.example.customerapi.service;

import static org.mockito.ArgumentMatchers.any;
import com.example.customerapi.dto.CustomerRequest;
import com.example.customerapi.dto.CustomerResponse;
import com.example.customerapi.exception.CustomerNotFoundException;
import com.example.customerapi.model.Customer;
import com.example.customerapi.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void getCustomerById_shouldReturnCustomer(){

        Customer customer = new Customer(
                1L,
                "Shiva",
                "Rai",
                "emaieshv@gmail.com"
        );
        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));
        CustomerResponse response =
                customerService.getCustomerById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Shiva", response.getFirstName());
        assertEquals("Rai", response.getLastName());
        assertEquals("emaieshv@gmail.com", response.getEmail());

    }
    @Test
    void getCustomerById_shouldThrowExceptionWhenCustomerNotFound(){
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                CustomerNotFoundException.class, () -> customerService.getCustomerById(99L)
        );
    }
    @Test
    void saveCustomer_shouldCreateCustomer(){
        CustomerRequest request = new CustomerRequest();

        request.setFirstName("Adriel");
        request.setLastName("Rai");
        request.setEmail("maile2AD@mail.com");

        Customer customer = new Customer(
                1L,
                "Adriel",
                "Rai",
                "maile2AD@mail.com"
        );
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        CustomerResponse response = customerService.saveCustomer(request);

        assertEquals(1L, response.getId());
        assertEquals("Adriel", response.getFirstName());
        assertEquals("Rai", response.getLastName());
        assertEquals("maile2AD@mail.com", response.getEmail());
    }
}
