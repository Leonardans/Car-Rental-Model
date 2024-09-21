package org.rent_master.car_rental_reservation_system.models.payment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.reservation.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String method;

    @NotNull
    @NotBlank
    @Column
    private String comment;

    public enum PaymentStatus {
        UNPAID,
        PAID,
        FAILED
    }

}
