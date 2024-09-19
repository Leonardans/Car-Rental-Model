package org.rent_master.car_rental_reservation_system.services.reservation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.rent_master.car_rental_reservation_system.DTO.payment.PaymentCreationDTO;
import org.rent_master.car_rental_reservation_system.DTO.reservation.ReservationCreationDTO;
import org.rent_master.car_rental_reservation_system.DTO.reservation.ReservationDTO;
import org.rent_master.car_rental_reservation_system.models.reservation.Reservation;
import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.rent_master.car_rental_reservation_system.models.car.Car;
import org.rent_master.car_rental_reservation_system.models.car.Status;
import org.rent_master.car_rental_reservation_system.models.customer.Customer;
import org.rent_master.car_rental_reservation_system.repositories.reservation.ReservationRepository;
import org.rent_master.car_rental_reservation_system.repositories.car.CarRepository;
import org.rent_master.car_rental_reservation_system.repositories.customer.CustomerRepository;
import org.rent_master.car_rental_reservation_system.services.payment.PaymentService;
import org.rent_master.car_rental_reservation_system.utils.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final PaymentService paymentService;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository,
                              CustomerRepository customerRepository, PaymentService paymentService) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.paymentService = paymentService;
    }


    // CREATE : Create new Reservation
    @Transactional
    public void createReservation(ReservationCreationDTO reservationCreationDTO) {
        System.out.println(reservationCreationDTO+ " 1: step");
        try {
            Validations.validateReservationDates(reservationCreationDTO.getDateFrom(), reservationCreationDTO.getDateTo());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        Reservation reservation = new Reservation();
        reservation.setAmount(reservationCreationDTO.getAmount());
        Long carId = reservationCreationDTO.getCarId();

        // Fetch existing Customer and Car objects
        Customer customer = customerRepository.findById(reservationCreationDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        Branch branchOfLoan = car.getBranch();


        // Set the fetched objects in the reservation
        reservation.setCustomer(customer);
        reservation.setCar(car);
        reservation.setDateFrom(LocalDate.parse(reservationCreationDTO.getDateFrom()));
        reservation.setDateTo(LocalDate.parse(reservationCreationDTO.getDateTo()));
        reservation.setBranchOfLoan(branchOfLoan);
        reservation.setBranchOfReturn(branchOfLoan);

        // Set the date of booking to the current date and time
        reservation.setDateOfBooking(LocalDateTime.now());

        // Get all existing reservations for the car
        List<Reservation> existingReservations = reservationRepository.findByCarId(carId);
        System.out.println(existingReservations+ " 2: step");

        // Check for date conflicts
        if (!existingReservations.isEmpty()) {
            for (Reservation existingReservation : existingReservations) {
                if (!(reservation.getDateTo().isBefore(existingReservation.getDateFrom()) ||
                        reservation.getDateFrom().isAfter(existingReservation.getDateTo()))) {
                    throw new IllegalArgumentException("The car is already booked for these dates.");
                }
            }
        }

        // Save the reservation
        System.out.println(reservation + " 3: step");
        reservationRepository.save(reservation);

        // Create and save the payment
        paymentService.createPayment(PaymentCreationDTO.paymentCreation(reservation, reservationCreationDTO.isPaid()));

        // Update the car status to BOOKED if the reservation is for today
        LocalDate today = LocalDate.now();
        if (!today.isBefore(reservation.getDateFrom()) && !today.isAfter(reservation.getDateTo())) {
            car.setStatus(Status.BOOKED);
            carRepository.save(car);
        }
    }

    // READ : Get all Car Reservations for Car by Car ID
    public List<ReservationDTO> readCarReservations(Long carId) {
        List<Reservation> allReservations = reservationRepository.findByCarId(carId);

        return allReservations.stream()
                .map(ReservationDTO::fromReservation)
                .toList();
    }

    // READ : Get all Car Reservations for Customer by Customer ID
    public List<ReservationDTO> readCustomerReservations(Long customerId) {
        List<Reservation> allReservations = reservationRepository.findByCustomerId(customerId);

        return allReservations.stream()
                .map(ReservationDTO::fromReservation)
                .toList();
    }

    // READ : Get all Branch of Loan Reservations by Branch of Loan ID
    public List<ReservationDTO> readBranchReservations(Long branchOfLoanId) {
        List<Reservation> allReservations = reservationRepository.findByBranchOfLoanId(branchOfLoanId);

        return allReservations.stream()
                .map(ReservationDTO::fromReservation)
                .toList();
    }

    // UPDATE : Update Reservation
    @Transactional
    public void updateReservation(Long id, ReservationCreationDTO reservationCreationDTO) {
        try {
            Validations.validateReservationDates(reservationCreationDTO.getDateFrom(), reservationCreationDTO.getDateTo());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        // Check Reservation Existing
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("This Reservation not found"));

        // Amount updating
        if (reservationCreationDTO.getAmount() != null) {
            existingReservation.setAmount(reservationCreationDTO.getAmount());
        }

        // Check Customer ID
        if (reservationCreationDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(reservationCreationDTO.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            existingReservation.setCustomer(customer);
        }

        // Check car ID + branches
        if (reservationCreationDTO.getCarId() != null) {
            Long carId = reservationCreationDTO.getCarId();
            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> new EntityNotFoundException("Car not found"));
            existingReservation.setCar(car);
            existingReservation.setBranchOfLoan(car.getBranch());
            existingReservation.setBranchOfReturn(car.getBranch());
        }

        // Check for reservation conflicts by Date
        List<Reservation> existingReservations = reservationRepository.findByCarId(existingReservation.getCar().getId());
        for (Reservation reservation : existingReservations) {
            if (!(reservation.getDateTo().isBefore(reservation.getDateFrom()) ||
                    existingReservation.getDateFrom().isAfter(existingReservation.getDateTo()))) {
                throw new IllegalArgumentException("The car is already booked for these dates.");
            }
        }

        // Save updated reservation
        reservationRepository.save(existingReservation);
    }

    // DELETE : Delete one Reservation
    public void deleteReservation(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty()) {
            throw new IllegalArgumentException("Reservation not found with ID " + reservationId);
        }

        this.reservationRepository.deleteById(reservationId);
    }

}
