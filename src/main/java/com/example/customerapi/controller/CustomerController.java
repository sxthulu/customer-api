package com.example.customerapi.controller;
import com.example.customerapi.model.Customer;
import com.example.customerapi.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @GetMapping("/customers")
    public List<Customer> getCustomer(){
        return customerService.getCustomers();
    }
    @PostMapping("/customers")
    public List<Customer> createCustomers(
            @RequestBody List<Customer> customers){
        return customerService.saveCustomers(customers);
    }
    @GetMapping("/customers/{id}")
    public Customer getCustomerById(
            @PathVariable Long id){
        return customerService.getCustomerById(id);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(
            @PathVariable Long id){
        customerService.deleteCustomer(id);
    }
    @PutMapping("/customers/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customer){
        return customerService.updateCustomer(id, customer);
    }
}
