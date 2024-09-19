package org.rent_master.car_rental_reservation_system.models.payment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.reservation.Refund;
import org.rent_master.car_rental_reservation_system.models.reservation.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "damage_charges")
public class DamageCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "refund_id", nullable = false)
    private Refund refund;

    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "charge_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime chargeDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChargeStatus status;

    @NotBlank
    @Column(nullable = false)
    private String method;

    @NotBlank
    @Column
    private String comment;

    public enum ChargeStatus {
        PENDING,
        CHARGED,
        WAIVED
    }

}