package org.rent_master.car_rental_reservation_system.controllers;

import org.rent_master.car_rental_reservation_system.services.business.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentals/managers")
public class ManagersController {

    private final EmployeeService employeeService;


    @Autowired
    public ManagersController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

}
