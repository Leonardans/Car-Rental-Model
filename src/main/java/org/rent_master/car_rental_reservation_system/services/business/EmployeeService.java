package org.rent_master.car_rental_reservation_system.services.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.rent_master.car_rental_reservation_system.DTO.business.EmployeeCreationDTO;
import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.rent_master.car_rental_reservation_system.models.business.Employee;
import org.rent_master.car_rental_reservation_system.models.business.Position;
import org.rent_master.car_rental_reservation_system.models.user.Role;
import org.rent_master.car_rental_reservation_system.models.user.Roles;
import org.rent_master.car_rental_reservation_system.repositories.business.EmployeeRepository;
import org.rent_master.car_rental_reservation_system.repositories.user.RoleRepository;
import org.rent_master.car_rental_reservation_system.repositories.user.UserRepository;
import org.rent_master.car_rental_reservation_system.utils.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeService {

    private final BranchService branchService;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, BranchService branchService, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.branchService = branchService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // CREATE : Create new Employee
    @Transactional
    public void createEmployee(EmployeeCreationDTO employeeCreationDTO, Long branchId, Long rentalId) {
        // Check Branch in Rental
        Branch branch;
        try {
            branch = branchService.readBranchByBranchIdAndRentalId(branchId, rentalId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Branch not found!");
        }

        // Check username existing
        if (userRepository.existsByUsername(employeeCreationDTO.getUsername())) {
            throw new IllegalArgumentException("User with this username already exists");
        }

        // Check email existing
        if (userRepository.existsByEmail(employeeCreationDTO.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Check Combo firstname + lastname + position Existing
        if (employeeRepository.existsByFirstnameAndLastnameAndPosition(
                employeeCreationDTO.getFirstname(),
                employeeCreationDTO.getLastname(),
                Position.valueOf(employeeCreationDTO.getPosition()))) {
            throw new IllegalArgumentException("Employee with the same name and position already exists");
        }

        System.out.println(branch);
        // All Necessary Validation
        if (Validations.isValidUsername(employeeCreationDTO.getUsername())) {
            throw new IllegalArgumentException("Invalid username");
        } else {
            System.out.println("Good username!");
        }
        if (Validations.isValidEmail(employeeCreationDTO.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        } else {
            System.out.println("Good email!");
        }
        if (Validations.isValidPassword(employeeCreationDTO.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        } else {
            System.out.println("Good password!");
        }
        if (Validations.isValidFirstName(employeeCreationDTO.getFirstname())) {
            throw new IllegalArgumentException("Invalid firstname");
        } else {
            System.out.println("Good firstname!");
        }
        if (Validations.isValidLastName(employeeCreationDTO.getLastname())) {
            throw new IllegalArgumentException("Invalid lastname");
        } else {
            System.out.println("Good lastname!");
        }

        // Create new Employee
        Employee employee = new Employee();
        employee.setUsername(employeeCreationDTO.getUsername());
        employee.setPassword(passwordEncoder.encode(employeeCreationDTO.getPassword()));
        employee.setEmail(employeeCreationDTO.getEmail());
        employee.setFirstname(employeeCreationDTO.getFirstname());
        employee.setLastname(employeeCreationDTO.getLastname());
        System.out.println(Position.valueOf(employeeCreationDTO.getPosition()));
        employee.setPosition(Position.valueOf(employeeCreationDTO.getPosition()));
        employee.setBranch(branch);
        Role rentalRole = roleRepository.findByName(Roles.ROLE_MANAGER)
                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_MANAGER is not found."));
        employee.setRoles(Collections.singleton(rentalRole));

        System.out.println("Some shit!");
        System.out.println(employee);
        this.employeeRepository.save(employee);
    }

    // READ : Check Manager Existing
    public boolean ifManagerExistsInBranch(Long branchId, Position manager) {
        return employeeRepository.existsByBranchIdAndPosition(branchId, manager);
    }

    // READ : Find all branch employees
    public List<Employee> findAllEmployeesByBranchId(Long branchId) {
        return employeeRepository.findAllByBranchId(branchId);
    }

    // DELETE : Delete new Employee
    public void deleteEmployee(Long employeeId, Long branchId, Long rentalId) {
        // Check Branch in Rental
        try {
            branchService.readBranchByBranchIdAndRentalId(branchId, rentalId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Branch not found!");
        }

        if(!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee with this ID does not exists!");
        }

        this.employeeRepository.deleteById(employeeId);
    }

}



