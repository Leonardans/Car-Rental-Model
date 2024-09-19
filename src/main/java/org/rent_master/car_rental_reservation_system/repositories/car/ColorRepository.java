package org.rent_master.car_rental_reservation_system.repositories.car;

import org.rent_master.car_rental_reservation_system.models.car.CarColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<CarColor, Long> {

    // READ : Find Color by Name
    Optional<CarColor> findByName(String name);

}
