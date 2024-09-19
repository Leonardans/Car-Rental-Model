package org.rent_master.car_rental_reservation_system.repositories.car;

import org.rent_master.car_rental_reservation_system.models.car.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel,Long> {

    // READ : Find Car Model by Name
    Optional<CarModel> findByName(String name);

}
