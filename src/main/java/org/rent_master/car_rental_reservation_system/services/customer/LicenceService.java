package org.rent_master.car_rental_reservation_system.services.customer;

import org.rent_master.car_rental_reservation_system.models.customer.Categories;
import org.rent_master.car_rental_reservation_system.models.customer.Licence;
import org.rent_master.car_rental_reservation_system.repositories.customer.LicenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LicenceService {
    private final LicenceRepository licenceRepository;

    @Autowired
    public LicenceService(LicenceRepository licenceRepository) {
        this.licenceRepository = licenceRepository;
    }


    // CREATE : Add Licence to Database
    public void save(Licence licence) {
        this.licenceRepository.save(licence);
    }

    // READ : Get Licence by Category
    public Optional<Licence> readByCategory(Categories category) {
        return licenceRepository.findByCategory(category);
    }

}
