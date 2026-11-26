
package com.example.customerservice.controller;

import com.example.customerservice.model.Customer;
import com.example.customerservice.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get one customer by ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));
    }

    // Create a customer
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    // Delete a customer
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerRepository.deleteById(id);
    }
}
