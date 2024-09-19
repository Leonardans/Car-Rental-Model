package org.rent_master.car_rental_reservation_system.repositories.car;

import org.rent_master.car_rental_reservation_system.models.car.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {

    // READ : Find Car Brand by Name
    Optional<Brand> findByName(String name);

}
