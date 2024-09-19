package org.rent_master.car_rental_reservation_system.DTO.reservation;

import lombok.Builder;
import lombok.Data;
import org.rent_master.car_rental_reservation_system.models.reservation.Reservation;

import java.math.BigDecimal;

@Data
@Builder
public class ReservationDTO {

    private Long id;
    private String dateOfBooking;
    private String dateFrom;
    private String dateTo;
    private Long customerId;
    private Long carId;
    private BigDecimal amount;
    private Long branchOfLoanId;
    private Long branchOfReturnId;


    public static ReservationDTO fromReservation(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .dateOfBooking(reservation.getDateOfBooking().toString())
                .dateFrom(reservation.getDateFrom().toString())
                .dateTo(reservation.getDateTo().toString())
                .customerId(reservation.getCustomer().getId())
                .carId(reservation.getCar().getId())
                .amount(reservation.getAmount())
                .branchOfLoanId(reservation.getBranchOfLoan().getId())
                .branchOfReturnId(reservation.getBranchOfReturn().getId())
                .build();

    }

}
