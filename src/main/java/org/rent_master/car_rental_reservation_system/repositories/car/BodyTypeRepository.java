package org.rent_master.car_rental_reservation_system.repositories.car;

import org.rent_master.car_rental_reservation_system.models.car.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {

    // READ : Find Car Body Type by Name
    Optional<BodyType> findByName(String name);

}
