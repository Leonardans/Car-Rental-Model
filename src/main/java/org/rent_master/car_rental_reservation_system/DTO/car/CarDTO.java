package org.rent_master.car_rental_reservation_system.DTO.car;

import lombok.Builder;
import lombok.Data;
import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.rent_master.car_rental_reservation_system.models.car.Car;

import java.util.List;


@Data
@Builder
public class CarDTO {

    private Long id;
    private Long amount;
    private String carStateNumberPlate;
    private String mileage;
    private String status;
    private String year;
    private String brand;
    private String model;
    private String bodyType;
    private String color;
    private String city;
    private String[] imagePaths;
    private Long branchId;
    private Long rentalId;

    public static CarDTO fromBaseCar(Car car, List<String> imagePaths, Branch branch) {
        assert branch != null;
        return CarDTO.builder()
                .id(car.getId())
                .amount(car.getAmount().longValue())
                .carStateNumberPlate(car.getCarStateNumberPlate())
                .mileage(car.getMileage().toString())
                .status(car.getStatus().toString())
                .year(car.getYear().toString())
                .brand(car.getBrand().getName())
                .model(car.getCarModel().getName())
                .bodyType(car.getBodyType().getName())
                .color(car.getCarColor().getName())
                .city(car.getCity())
                .imagePaths(imagePaths.toArray(new String[0]))
                .branchId(branch.getId())
                .rentalId(branch.getRental().getId())
                .build();
    }

}