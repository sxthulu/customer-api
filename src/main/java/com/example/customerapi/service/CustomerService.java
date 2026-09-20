package com.example.customerapi.service;


import com.example.customerapi.dto.CustomerPatchReq;
import com.example.customerapi.dto.CustomerRequest;
import com.example.customerapi.dto.CustomerResponse;
import com.example.customerapi.exception.CustomerNotFoundException;
import com.example.customerapi.exception.DuplicateEmailException;
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
                .map(this::toMapResponse)
                .toList();
    }

    public CustomerResponse saveCustomer (CustomerRequest request) {

        if (customerRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new DuplicateEmailException(request.getEmail());
        }
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());

        Customer savedCustomer = customerRepository.save(customer);

        return toMapResponse(savedCustomer);
    }
    public CustomerResponse getCustomerById(Long id){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        return toMapResponse(customer);
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

        Customer updatedCustomer = customerRepository.save(customer);

        return toMapResponse(updatedCustomer);
    }
    public CustomerResponse patchCustomer(
            Long id,
            CustomerPatchReq request
    ) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }
        Customer updatedCustomer = customerRepository.save(customer);

        return toMapResponse(updatedCustomer);
    }

    private String maskEmail(String email){
        if (email == null || !email.contains("@")){
            return email;
        }

        String[] parts = email.split("@",2);
        String username = parts[0];
        String domain = parts[1];

        if (username.length() <= 2){
            return "*".repeat(username.length())+ "@" + domain;
        }

        return username.charAt(0)
                + "*".repeat(username.length() - 2)
                + username.charAt(username.length() -1)
                + "@"
                + domain;
    }
    private CustomerResponse toMapResponse(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
    public List<CustomerResponse> searchCustomers(String name){
        return customerRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name)
                .stream()
                .map(this::toMapResponse)
                .toList();
    }
}
