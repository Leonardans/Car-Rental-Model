package org.rent_master.car_rental_reservation_system.repositories.reservation;

import org.rent_master.car_rental_reservation_system.models.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    // Find all Reservations by Car ID
    List<Reservation> findByCarId(Long carId);

    // Find all Reservations by Customer ID
    List<Reservation> findByCustomerId(Long customerId);

    // Find Reservations by Branch of Loan ID
    List<Reservation> findByBranchOfLoanId(Long branchId);

}
