package org.rent_master.car_rental_reservation_system.DTO.payment;

import lombok.Builder;
import lombok.Data;
import org.rent_master.car_rental_reservation_system.models.reservation.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentCreationDTO {

    private Reservation reservation;
    private BigDecimal amount;
    private LocalDateTime time;
    private boolean isPaid;

    public static PaymentCreationDTO paymentCreation(Reservation reservation, boolean isPaid) {

        return PaymentCreationDTO.builder()
                .reservation(reservation)
                .amount(reservation.getAmount())
                .time(LocalDateTime.now())
                .isPaid(isPaid)
                .build();
    }
}
