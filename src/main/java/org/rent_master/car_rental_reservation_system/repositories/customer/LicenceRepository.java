package org.rent_master.car_rental_reservation_system.repositories.customer;

import org.rent_master.car_rental_reservation_system.models.customer.Categories;
import org.rent_master.car_rental_reservation_system.models.customer.Licence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long> {

    // READ : Find Licence by Category
    Optional<Licence> findByCategory(Categories category);

}
