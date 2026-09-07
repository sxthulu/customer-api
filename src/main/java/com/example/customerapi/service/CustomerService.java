package com.example.customerapi.service;


import com.example.customerapi.exception.CustomerNotFoundException;
import com.example.customerapi.model.Customer;
import com.example.customerapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }
    public List<Customer> saveCustomers(List<Customer> customers){
        return customerRepository.saveAll(customers);
    }
    public Customer getCustomerById(Long id){
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
    public Customer updateCustomer(Long id, Customer customer){
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null){
            return null;
        }
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        return customerRepository.save(existingCustomer);
    }
}
