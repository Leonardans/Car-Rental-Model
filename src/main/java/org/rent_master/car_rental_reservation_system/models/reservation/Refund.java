package org.rent_master.car_rental_reservation_system.models.reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.business.Employee;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refunds")
public class Refund {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        @ManyToOne
        private Employee employee;

        @NotNull
        @OneToOne
        private Reservation reservation;

        @NotNull
        @Temporal(TemporalType.DATE)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate dateOfReturn;

        @NotNull
        private BigDecimal surcharge;

        @Max(value = 250, message = "No more than 250 characters")
        @NotBlank
        private String comments;

}


