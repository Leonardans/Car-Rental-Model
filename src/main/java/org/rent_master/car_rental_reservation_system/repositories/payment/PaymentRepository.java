package org.rent_master.car_rental_reservation_system.repositories.payment;

import org.rent_master.car_rental_reservation_system.models.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // READ : Find by Reservation ID
    Optional<Payment> findByReservationId(Long reservationId);

    // READ : Find all by Status
    List<Payment> findAllByStatus(Payment.PaymentStatus status);

}
