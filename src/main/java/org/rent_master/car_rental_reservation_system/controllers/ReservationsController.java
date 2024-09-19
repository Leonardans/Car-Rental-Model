package org.rent_master.car_rental_reservation_system.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.rent_master.car_rental_reservation_system.DTO.reservation.BranchInfoDTO;
import org.rent_master.car_rental_reservation_system.DTO.reservation.ReservationCreationDTO;
import org.rent_master.car_rental_reservation_system.DTO.reservation.ReservationDTO;
import org.rent_master.car_rental_reservation_system.services.reservation.ReservationService;
import org.rent_master.car_rental_reservation_system.services.business.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationsController {

    public final ReservationService reservationService;
    private final BranchService branchService;

    @Autowired
    public ReservationsController(ReservationService reservationService, BranchService branchService) {
        this.reservationService = reservationService;
        this.branchService = branchService;
    }


    // POST : Create new Reservation
    @PostMapping("")
    public ResponseEntity<String> createReservation(@RequestBody ReservationCreationDTO reservationCreationDTO) {
        try {
            reservationService.createReservation(reservationCreationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // GET : Get all Reservations by Branch of Loan ID
    @GetMapping("/branches/{branchId}")
    public ResponseEntity<List<ReservationDTO>> readBranchReservations(@PathVariable Long branchId) {
        List<ReservationDTO> reservations = reservationService.readBranchReservations(branchId);

        return ResponseEntity.ok(reservations);
    }

    // GET : Get all car Reservations by Car ID
    @GetMapping("/cars/{carId}")
    public ResponseEntity<List<ReservationDTO>> readCarReservations(@PathVariable Long carId) {
        List<ReservationDTO> reservations = reservationService.readCarReservations(carId);

        return ResponseEntity.ok(reservations);
    }

    // GET : Get all car Reservations by Customer ID
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<ReservationDTO>> readCustomerReservations(@PathVariable Long customerId) {
        List<ReservationDTO> reservations = reservationService.readCustomerReservations(customerId);

        return ResponseEntity.ok(reservations);
    }

    // GET : Get car BranchInfo for confirm Booking
    @GetMapping("/rentals/{rentalId}/branches/{branchId}")
    public ResponseEntity<?> readBranchInfo(@PathVariable Long rentalId, @PathVariable Long branchId) {
        BranchInfoDTO branchInfoDTO;

        try {
            branchInfoDTO = branchService.readBranchInfo(branchId, rentalId);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Branch " + branchId + " not found.", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(branchInfoDTO);
    }

    // PUT : Renting Prolongation
    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReservation(@PathVariable Long reservationId,
                                                    @RequestBody ReservationCreationDTO reservationCreationDTO) {
        try {
            reservationService.updateReservation(reservationId, reservationCreationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    // DELETE : Delete Concretely Reservation
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long reservationId) {

        try {
            reservationService.deleteReservation(reservationId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
