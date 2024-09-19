package org.rent_master.car_rental_reservation_system.models.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.rent_master.car_rental_reservation_system.models.car.Car;
import org.rent_master.car_rental_reservation_system.models.customer.Customer;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateOfBooking;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    @NotNull
    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "branch_of_loan_id", nullable = false)
    private Branch branchOfLoan;

    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "branch_of_return_id", nullable = false)
    private Branch branchOfReturn;

    @NotNull
    private BigDecimal amount;

}
