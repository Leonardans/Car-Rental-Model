package org.rent_master.car_rental_reservation_system.repositories.payment;

import org.rent_master.car_rental_reservation_system.models.payment.DamageCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DamageChargeRepository extends JpaRepository<DamageCharge, Long> {

    // READ : Find by Reservation ID
    Optional<DamageCharge> findByReservationId(Long reservationId);

    // READ : Find all By Status
    List<DamageCharge> findAllByStatus(DamageCharge.ChargeStatus status);

}
