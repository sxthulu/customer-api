package com.example.customerapi.controller;
import com.example.customerapi.dto.CustomerPatchReq;
import com.example.customerapi.dto.CustomerRequest;
import com.example.customerapi.dto.CustomerResponse;
import com.example.customerapi.model.Customer;
import com.example.customerapi.service.CustomerService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @GetMapping("/customers")
    public List<CustomerResponse> getCustomers(){
        return customerService.getCustomers();
    }
    @PostMapping("/customers")
    public CustomerResponse createCustomers(
            @Valid @RequestBody CustomerRequest request){
        return customerService.saveCustomer(request);
    }
    @GetMapping("/customers/{id}")
    public CustomerResponse getCustomerById(
            @PathVariable Long id){
        return customerService.getCustomerById(id);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(
            @PathVariable Long id){
        customerService.deleteCustomer(id);
    }
    @PutMapping("/customers/{id}")
    public CustomerResponse updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request){
        return customerService.updateCustomer(id, request);
    }
    @PatchMapping("/customers/{id}")
    public CustomerResponse patchCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerPatchReq request
            ){
        return customerService.patchCustomer(id, request);
    }
    @GetMapping("/customers/search")
    public List<CustomerResponse> searchCustomers(
            @RequestParam String name){
        return customerService.searchCustomers(name);
    }
}
