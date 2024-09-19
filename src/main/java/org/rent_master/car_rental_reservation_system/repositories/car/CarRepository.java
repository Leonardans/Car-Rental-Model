package org.rent_master.car_rental_reservation_system.repositories.car;

import org.rent_master.car_rental_reservation_system.models.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    // READ : Check if Car Exist by Number Plate
    boolean existsByCarStateNumberPlate(String carStateNumberPlate);

}
