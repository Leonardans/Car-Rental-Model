package org.rent_master.car_rental_reservation_system.services.payment;

import org.rent_master.car_rental_reservation_system.DTO.payment.PaymentCreationDTO;
import org.rent_master.car_rental_reservation_system.models.payment.Payment;
import org.rent_master.car_rental_reservation_system.repositories.payment.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    // CREATE : Add new Payment
    public void createPayment(PaymentCreationDTO paymentCreationDTO) {
        Payment payment = new Payment();
        payment.setReservation(paymentCreationDTO.getReservation());
        payment.setAmount(paymentCreationDTO.getAmount());
        payment.setOrderDate(paymentCreationDTO.getTime());

        if (paymentCreationDTO.isPaid()) {
            payment.setPaymentDate(paymentCreationDTO.getTime());
            payment.setStatus(Payment.PaymentStatus.PAID);
            payment.setMethod("Bank Transfer");
            payment.setComment("Paid");
        } else {
            payment.setPaymentDate(paymentCreationDTO.getReservation().getDateTo().atStartOfDay());
            payment.setStatus(Payment.PaymentStatus.UNPAID);
            payment.setMethod("Cash");
            payment.setComment("Pay in location");
        }

        System.out.println(payment);
        paymentRepository.save(payment);
    }

    // READ : Find all Payments
    public List<Payment> readAllPayments() {
        return paymentRepository.findAll();
    }

    // READ : Find by Reservation ID
    public Optional<Payment> readPaymentById(Long reservationId) {
        return paymentRepository.findByReservationId(reservationId);
    }

    // READ : Find all Payments by Status
    public List<Payment> readAllPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentRepository.findAllByStatus(status);
    }

    // UPDATE : Update Payment Status
    public Optional<Payment> updatePaymentStatus(Long reservationId, Payment.PaymentStatus newStatus) {
        Optional<Payment> optionalPayment = paymentRepository.findByReservationId(reservationId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setStatus(newStatus);
            paymentRepository.save(payment);
            return Optional.of(payment);
        }
        return Optional.empty();
    }

}
