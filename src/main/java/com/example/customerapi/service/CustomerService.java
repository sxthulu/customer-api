package com.example.customerapi.service;


import com.example.customerapi.dto.CustomerRequest;
import com.example.customerapi.dto.CustomerResponse;
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
    public List<CustomerResponse> getCustomers(){
        return customerRepository.findAll()
                .stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail()
                ))
                .toList();
    }
    public CustomerResponse saveCustomer (CustomerRequest request){
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());

        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getFirstName(),
                savedCustomer.getLastName(),
                savedCustomer.getEmail()
        );
    }
    public CustomerResponse getCustomerById(Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail()
        );
    }
    public void deleteCustomer(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(customer);
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest request){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        Customer updateCustomer = customerRepository.save(customer);

        return new CustomerResponse(
                updateCustomer.getId(),
                updateCustomer.getFirstName(),
                updateCustomer.getLastName(),
                updateCustomer.getEmail()
        );
    }
}
