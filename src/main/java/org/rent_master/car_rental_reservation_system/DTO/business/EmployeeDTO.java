package org.rent_master.car_rental_reservation_system.DTO.business;

import lombok.Builder;
import lombok.Data;
import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.rent_master.car_rental_reservation_system.models.business.Employee;
import org.rent_master.car_rental_reservation_system.models.business.Position;


@Data
@Builder
public class EmployeeDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Position position;
    private Long branchId;

    public static EmployeeDTO convertToDTO(Branch branch, Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .email(employee.getEmail())
                .position(employee.getPosition())
                .branchId(branch.getId())
                .build();
    }

}
