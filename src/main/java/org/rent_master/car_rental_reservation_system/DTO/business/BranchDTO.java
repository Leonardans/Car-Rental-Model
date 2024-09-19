package org.rent_master.car_rental_reservation_system.DTO.business;

import lombok.Builder;
import lombok.Data;
import org.rent_master.car_rental_reservation_system.DTO.car.CarDTO;
import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.springframework.beans.factory.annotation.Value;


import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class BranchDTO {
    @Value("${car.resource-url}")
    private static String carResourceUrl;

    private Long id;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private List<CarDTO> cars;
    private List<EmployeeDTO> employees;

    public static BranchDTO convertToDTO(Branch branch) {
        List<CarDTO> carDTOs = branch.getCars().stream()
                .map(car -> {
                    List<String> imagePaths = car.getPictures().stream()
                            .map(carPicture -> carResourceUrl + carPicture.getPicture())
                            .collect(Collectors.toList());
                    return CarDTO.fromBaseCar(car, imagePaths, branch);
                })
                .collect(Collectors.toList());

        List<EmployeeDTO> employeeDTOs = branch.getEmployees().stream()
                .map(employee -> EmployeeDTO.convertToDTO(branch, employee))
                .collect(Collectors.toList());

        return BranchDTO.builder()
                .id(branch.getId())
                .address1(branch.getAddress1())
                .address2(branch.getAddress2())
                .city(branch.getCity())
                .country(branch.getCountry())
                .cars(carDTOs)
                .employees(employeeDTOs)
                .build();
    }
}