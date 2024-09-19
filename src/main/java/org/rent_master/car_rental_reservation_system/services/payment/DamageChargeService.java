package org.rent_master.car_rental_reservation_system.services.payment;

import org.rent_master.car_rental_reservation_system.models.payment.DamageCharge;
import org.rent_master.car_rental_reservation_system.repositories.payment.DamageChargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DamageChargeService {

    private final DamageChargeRepository damageChargeRepository;


    @Autowired
    public DamageChargeService(DamageChargeRepository damageChargeRepository) {
        this.damageChargeRepository = damageChargeRepository;
    }


    // CREATE : Add new Damage Charge
    public void saveDamageCharge(DamageCharge charge) {
        damageChargeRepository.save(charge);
    }

    // READ : Find all Damage Charges
    public List<DamageCharge> readAllDamageCharges() {
        return damageChargeRepository.findAll();
    }

    // READ : Find by Reservation ID
    public Optional<DamageCharge> readDamageChargeById(Long reservationId) {
        return damageChargeRepository.findByReservationId(reservationId);
    }

    // READ : Find all Damage Charges by Status
    public List<DamageCharge> readDamageChargesByStatus(DamageCharge.ChargeStatus status) {
        return damageChargeRepository.findAllByStatus(status);
    }

    // UPDATE :Update Damage Charge Status
    public Optional<DamageCharge> updateDamageChargeStatus(Long reservationId, DamageCharge.ChargeStatus newStatus) {
        Optional<DamageCharge> optionalPayment = damageChargeRepository.findByReservationId(reservationId);
        if (optionalPayment.isPresent()) {
            DamageCharge payment = optionalPayment.get();
            payment.setStatus(newStatus);
            damageChargeRepository.save(payment);
            return Optional.of(payment);
        }
        return Optional.empty();
    }

}
