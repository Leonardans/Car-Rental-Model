package org.rent_master.car_rental_reservation_system.repositories.customer;

import org.rent_master.car_rental_reservation_system.models.customer.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {

}
