package org.rent_master.car_rental_reservation_system.repositories.user;

import org.rent_master.car_rental_reservation_system.models.user.Role;
import org.rent_master.car_rental_reservation_system.models.user.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

   // READ : Find Role by Name
   Optional<Role> findByName(Roles name);

}