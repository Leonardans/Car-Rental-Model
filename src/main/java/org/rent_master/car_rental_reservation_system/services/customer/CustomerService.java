package org.rent_master.car_rental_reservation_system.services.customer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.rent_master.car_rental_reservation_system.DTO.customer.CustomerCreationDTO;
import org.rent_master.car_rental_reservation_system.models.customer.Categories;
import org.rent_master.car_rental_reservation_system.models.customer.Customer;
import org.rent_master.car_rental_reservation_system.models.customer.Garage;
import org.rent_master.car_rental_reservation_system.models.customer.Licence;
import org.rent_master.car_rental_reservation_system.models.user.Role;
import org.rent_master.car_rental_reservation_system.models.user.Roles;
import org.rent_master.car_rental_reservation_system.repositories.customer.CustomerRepository;
import org.rent_master.car_rental_reservation_system.repositories.user.RoleRepository;
import org.rent_master.car_rental_reservation_system.repositories.user.UserRepository;
import org.rent_master.car_rental_reservation_system.utils.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    private final GarageService garageService;
    private final LicenceService licenceService;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public CustomerService(CustomerRepository customerRepository, GarageService garageService, LicenceService licenceService,
                           UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.licenceService = licenceService;
        this.passwordEncoder = passwordEncoder;
        this.garageService = garageService;
    }


    // CREATE : Create new customer
    @Transactional
    public void createCustomer(CustomerCreationDTO customerCreationDTO) {
        System.out.println(customerCreationDTO);
        // Check username existing
        if (userRepository.existsByUsername(customerCreationDTO.getUsername())) {
            throw new EntityNotFoundException("User with this username already exists");
        }

        // Check email existing
        if (userRepository.existsByEmail(customerCreationDTO.getEmail())) {
            throw new EntityNotFoundException("User with this email already exists");
        }

        // Check Licence
        Categories category;
        try {
            category = Categories.fromString(customerCreationDTO.getLicence());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid licence category!");
        }

        // Check Category
        Optional<Licence> optionalLicence = licenceService.readByCategory(category);
        if (optionalLicence.isEmpty()) {
            throw new IllegalArgumentException("Licence not found!!");
        }

        // All Necessary Validation
        if (Validations.isValidUsername(customerCreationDTO.getUsername())) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (Validations.isValidEmail(customerCreationDTO.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (Validations.isValidPassword(customerCreationDTO.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (Validations.isValidFirstName(customerCreationDTO.getFirstname())) {
            throw new IllegalArgumentException("Invalid first name");
        }
        if (Validations.isValidLastName(customerCreationDTO.getLastname())) {
            throw new IllegalArgumentException("Invalid last name");
        }
        if (Validations.isValidAddress(customerCreationDTO.getAddress1())) {
            throw new IllegalArgumentException("Invalid address");
        }
        if (customerCreationDTO.getAddress2() != null) {
            if (Validations.isValidAddress(customerCreationDTO.getAddress2())) {
                throw new IllegalArgumentException("Invalid address");
            }
        }
        if (Validations.isValidCity(customerCreationDTO.getCity())) {
            throw new IllegalArgumentException("Invalid city");
        }
        if (Validations.isValidCountry(customerCreationDTO.getCountry())) {
            throw new IllegalArgumentException("Invalid country");
        }

        // Create new Customer (which is also a User)
        Customer customer = new Customer();
        customer.setUsername(customerCreationDTO.getUsername());
        customer.setEmail(customerCreationDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(customerCreationDTO.getPassword()));
        customer.setFirstName(customerCreationDTO.getFirstname());
        customer.setLastName(customerCreationDTO.getLastname());
        customer.setAddress1(customerCreationDTO.getAddress1());
        if (customerCreationDTO.getAddress2() != null) {
            customer.setAddress2(customerCreationDTO.getAddress2());
        }
        customer.setCity(customerCreationDTO.getCity());
        customer.setCountry(customerCreationDTO.getCountry());

        System.out.println(customerCreationDTO.getLicence());
        // Set default role
        Role rentalRole = roleRepository.findByName(Roles.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_CUSTOMER is not found."));
        customer.setRoles(Collections.singleton(rentalRole));

        Licence licence = optionalLicence.get();
        customer.setCustomerLicences(Collections.singletonList(licence));

        // Save Customer (this will also save User data)
        System.out.println(customer);
        this.customerRepository.save(customer);

        // Create and save Garage for the new customer
        Garage garage = new Garage();
        garage.setCustomer(customer);
        garage.setGarageCars(new ArrayList<>());

        // Save Garage
        this.garageService.save(garage);
    }

    // READ : Get all Customers
    public List<Customer> readAllCustomers() {
        return this.customerRepository.findAll();
    }

}