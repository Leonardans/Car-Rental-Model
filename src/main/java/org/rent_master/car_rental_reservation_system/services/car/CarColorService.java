package org.rent_master.car_rental_reservation_system.services.car;

import jakarta.persistence.EntityNotFoundException;
import org.rent_master.car_rental_reservation_system.models.car.CarColor;
import org.rent_master.car_rental_reservation_system.repositories.car.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarColorService {

    public final ColorRepository colorRepository;

    @Autowired
    public CarColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }


    // READ : Find all Colors
    public List<CarColor> readAllColors() {
        return this.colorRepository.findAll();
    }

    // READ : Find Color by Name
    public CarColor readByName(String name) {
        return colorRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Car color not found"));
    }

}


