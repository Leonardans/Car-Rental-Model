package org.rent_master.car_rental_reservation_system.repositories.business;

import org.rent_master.car_rental_reservation_system.models.business.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface RentalRepository extends JpaRepository<Rental, Long> {

    // Check if Rental with this name exist
    boolean existsByName(String name);

}
