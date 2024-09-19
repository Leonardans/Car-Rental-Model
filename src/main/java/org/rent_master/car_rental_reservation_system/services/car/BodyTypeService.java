package org.rent_master.car_rental_reservation_system.services.car;

import jakarta.persistence.EntityNotFoundException;
import org.rent_master.car_rental_reservation_system.models.car.BodyType;
import org.rent_master.car_rental_reservation_system.repositories.car.BodyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyTypeService {
    public final BodyTypeRepository bodyTypeRepository;

    @Autowired
    public BodyTypeService(BodyTypeRepository bodyTypeRepository) {
        this.bodyTypeRepository = bodyTypeRepository;
    }


    // READ : Find all Body Types
    public List<BodyType> readAllBodyTypes() {
        return this.bodyTypeRepository.findAll();
    }

    // READ : Brand by Name
    public BodyType readByName(String name) {
        return bodyTypeRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Body type not found"));
    }

}

