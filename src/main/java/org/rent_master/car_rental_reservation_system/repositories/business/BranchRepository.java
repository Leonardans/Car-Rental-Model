package org.rent_master.car_rental_reservation_system.repositories.business;

import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch,Long> {

    // Find Branch by ID and Rental ID
    Optional<Branch> findByIdAndRentalId(Long id, Long rentalId);

    // Check all rental branches
    List<Branch> findByRentalId(Long rentalId);

}

