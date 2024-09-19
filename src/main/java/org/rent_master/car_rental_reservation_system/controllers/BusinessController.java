package org.rent_master.car_rental_reservation_system.controllers;

import org.rent_master.car_rental_reservation_system.DTO.business.BranchCreationDTO;
import org.rent_master.car_rental_reservation_system.DTO.business.EmployeeCreationDTO;
import org.rent_master.car_rental_reservation_system.DTO.business.EmployeeDTO;
import org.rent_master.car_rental_reservation_system.DTO.business.RentalDTO;
import org.rent_master.car_rental_reservation_system.DTO.car.CarCreationDTO;
import org.rent_master.car_rental_reservation_system.DTO.business.RentalCreationDTO;
import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.rent_master.car_rental_reservation_system.models.business.Employee;
import org.rent_master.car_rental_reservation_system.services.business.BranchService;
import org.rent_master.car_rental_reservation_system.services.business.EmployeeService;
import org.rent_master.car_rental_reservation_system.services.business.RentalService;
import org.rent_master.car_rental_reservation_system.services.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/rentals")
public class BusinessController {

    private final RentalService rentalService;
    private final BranchService branchService;
    private final EmployeeService employeeService;
    private final CarService carService;


    @Autowired
    public BusinessController(RentalService rentalService, BranchService branchService,
                              EmployeeService employeeService, CarService carService) {
        this.rentalService = rentalService;
        this.branchService = branchService;
        this.employeeService = employeeService;
        this.carService = carService;
    }


    // POST : Rental (Business) Registration
    @PostMapping("/sign-up")
    public ResponseEntity<String> createRental(@ModelAttribute RentalCreationDTO rentalCreationDTO) {

        // Creating new Rental Office
        try {
            rentalService.createRentalBusiness(rentalCreationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // POST : Create Branch
    @PostMapping("/{rentalId}/branches")
    public ResponseEntity<String> createRentalBranch(@PathVariable Long rentalId,
                                                     @RequestBody BranchCreationDTO branchCreationDTO) {
        try {
            branchService.createBranch(branchCreationDTO, rentalId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    // POST : Create Employee into Branch
    @PostMapping("/{rentalId}/branches/{branchId}/employees")
    public ResponseEntity<String> createBranchEmployee(@PathVariable Long rentalId,
                                                       @PathVariable Long branchId,
                                                       @RequestBody EmployeeCreationDTO employeeCreationDTO) {

        System.out.println(employeeCreationDTO);
        try {
            employeeService.createEmployee(employeeCreationDTO, branchId, rentalId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // POST : Create Car into rental one branch
    @PostMapping("/{rentalId}/branches/{branchId}/cars")
    public ResponseEntity<String> createCar(@PathVariable("rentalId") Long rentalId,
                                            @PathVariable("branchId") Long branchId,
                                            @ModelAttribute CarCreationDTO carCreationDTO) {

        System.out.println(carCreationDTO);
        try {
            carService.createCar(carCreationDTO, branchId, rentalId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // GET : Rental Data
    @GetMapping("/{rentalId}")
    public ResponseEntity<?> readRentalData(@PathVariable Long rentalId) {
        RentalDTO rentalDTO = rentalService.readRentalDTOById(rentalId);

        if (rentalDTO == null) {
            return new ResponseEntity<>("Rental office " + rentalId + " not found.", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(rentalDTO);
    }

    // GET : All Rental Employees Data by Rental ID
    @GetMapping("/{rentalId}/employees")
    public ResponseEntity<List<EmployeeDTO>> readRentalEmployeesData(@PathVariable Long rentalId) {
        List<Branch> branches = branchService.readAllBranchesByRentalId(rentalId);
        List<EmployeeDTO> allEmployees = new ArrayList<>();

        for (Branch branch : branches) {
            List<Employee> employees = employeeService.findAllEmployeesByBranchId(branch.getId());
            for (Employee employee : employees) {
                EmployeeDTO employeeDTO = EmployeeDTO.convertToDTO(branch, employee);
                allEmployees.add(employeeDTO);
            }
        }

        return ResponseEntity.ok(allEmployees);
    }

    // DELETE : Delete Branch
    @DeleteMapping("/{rentalId}/branches/{branchId}")
    public ResponseEntity<String> deleteBranch(@PathVariable Long rentalId,
                                               @PathVariable Long branchId) {

        try {
            branchService.deleteBranch(branchId, rentalId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // DELETE : Delete Employee
    @DeleteMapping("/{rentalId}/branches/{branchId}/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long rentalId,
                                                 @PathVariable Long branchId,
                                                 @PathVariable Long employeeId) {

        try {
            employeeService.deleteEmployee(employeeId, branchId, rentalId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // DELETE : Delete Car from rental branch
    @DeleteMapping("/{rentalId}/branches/{branchId}/cars/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable Long rentalId,
                                            @PathVariable Long branchId,
                                            @PathVariable Long carId) {

        try {
            carService.deleteCar(carId, branchId, rentalId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
