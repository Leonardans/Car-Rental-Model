package org.rent_master.car_rental_reservation_system.services.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.rent_master.car_rental_reservation_system.DTO.business.RentalDTO;
import org.rent_master.car_rental_reservation_system.DTO.business.RentalCreationDTO;
import org.rent_master.car_rental_reservation_system.models.business.Rental;
import org.rent_master.car_rental_reservation_system.models.user.Role;
import org.rent_master.car_rental_reservation_system.models.user.Roles;
import org.rent_master.car_rental_reservation_system.repositories.business.RentalRepository;
import org.rent_master.car_rental_reservation_system.repositories.user.RoleRepository;
import org.rent_master.car_rental_reservation_system.repositories.user.UserRepository;
import org.rent_master.car_rental_reservation_system.utils.FileUtils;
import org.rent_master.car_rental_reservation_system.utils.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${logo.upload-dir}")
    private String logoUploadDir;

    @Autowired
    public RentalService(RentalRepository rentalRepository, RoleRepository roleRepository, UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // CREATE : Create new Rental Business
    @Transactional
    public void createRentalBusiness(RentalCreationDTO rentalCreationDTO) {
        System.out.println(rentalCreationDTO);
        // Check username existing
        if (userRepository.existsByUsername(rentalCreationDTO.getUsername())) {
            throw new UsernameNotFoundException("User with this username already exists");
        }

        // Check email existing
        if (userRepository.existsByEmail(rentalCreationDTO.getEmail())) {
            throw new EntityNotFoundException("User with this email already exists");
        }

        // Check Business name existing
        if (rentalRepository.existsByName(rentalCreationDTO.getName())) {
            throw new EntityNotFoundException("Business with this name already exists");
        }

        // All Necessary Validation
        if(Validations.isValidCompanyName(rentalCreationDTO.getName())) {
            throw new IllegalArgumentException("Invalid company name!");
        }
        if (Validations.isValidUsername(rentalCreationDTO.getUsername())) {
            throw new IllegalArgumentException("Invalid username!");
        }
        if (Validations.isValidEmail(rentalCreationDTO.getEmail())) {
            throw new IllegalArgumentException("Invalid email!");
        }
        if (Validations.isValidPassword(rentalCreationDTO.getPassword())) {
            throw new IllegalArgumentException("Invalid password!");
        }
        if (Validations.isValidFullName(rentalCreationDTO.getOwner())) {
            throw new IllegalArgumentException("Invalid full name!");
        }
        if (Validations.isValidAddress(rentalCreationDTO.getAddress1())) {
            throw new IllegalArgumentException("Invalid address!");
        }
        if (rentalCreationDTO.getAddress2() != null) {
            if (Validations.isValidAddress(rentalCreationDTO.getAddress2())) {
                throw new IllegalArgumentException("Invalid address!");
            }
        }
        if (Validations.isValidCity(rentalCreationDTO.getCity())) {
            throw new IllegalArgumentException("Invalid city!");
        }
        if (Validations.isValidCountry(rentalCreationDTO.getCountry())) {
            throw new IllegalArgumentException("Invalid country!");
        }

        String logoName = "";
        try {
            // Save Rental Logo
            logoName = FileUtils.addImageToDirectory(rentalCreationDTO.getLogo(), logoUploadDir);
            System.out.println("Logo saved as: " + logoName);

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

        // Create new Rental
        Rental rental = new Rental();
        rental.setUsername(rentalCreationDTO.getUsername());
        rental.setPassword(passwordEncoder.encode(rentalCreationDTO.getPassword()));
        rental.setEmail(rentalCreationDTO.getEmail());
        rental.setName(rentalCreationDTO.getName());
        rental.setOwner(rentalCreationDTO.getOwner());
        rental.setAddress1(rentalCreationDTO.getAddress1());
        rental.setAddress2(rentalCreationDTO.getAddress2());
        rental.setCity(rentalCreationDTO.getCity());
        rental.setCountry(rentalCreationDTO.getCountry());
        rental.setLogo(logoName);
        // Set default role
        Role rentalRole = roleRepository.findByName(Roles.ROLE_RENTAL)
                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_RENTAL is not found."));
        rental.setRoles(Collections.singleton(rentalRole));

        // Save Rental
        rentalRepository.save(rental);
    }

    // READ : Find Rental DTO by ID
    @Transactional
    public RentalDTO readRentalDTOById(Long rentalId) {
        Rental rental = readRentalById(rentalId);

        return rental != null ? RentalDTO.convertToDTO(rental) : null;
    }

    // READ : Find Rental by ID
    public Rental readRentalById(Long rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id: " + rentalId));
    }

    // READ : Find All Rentals
    public List<Rental> readAllRentals() {
        return this.rentalRepository.findAll();
    }


}
