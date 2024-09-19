package org.rent_master.car_rental_reservation_system.controllers;

import org.rent_master.car_rental_reservation_system.DTO.customer.CustomerCreationDTO;
import org.rent_master.car_rental_reservation_system.models.customer.Customer;
import org.rent_master.car_rental_reservation_system.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    private final CustomerService customerService;

    @Autowired
    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }


    // GET : Get All Customers
    @GetMapping("")
    public ResponseEntity<List<Customer>> readCustomers() {
        return ResponseEntity.ok(customerService.readAllCustomers());
    }


    // POST : Create new Customer
    @PostMapping("/sign-up")
    public ResponseEntity<String> createCustomer(@RequestBody CustomerCreationDTO customerCreationDTO) {
        try {
            System.out.println(customerCreationDTO);
            customerService.createCustomer(customerCreationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}