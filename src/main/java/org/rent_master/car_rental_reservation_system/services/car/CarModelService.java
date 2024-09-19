package org.rent_master.car_rental_reservation_system.services.car;

import jakarta.persistence.EntityNotFoundException;
import org.rent_master.car_rental_reservation_system.models.car.CarModel;
import org.rent_master.car_rental_reservation_system.repositories.car.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarModelService {

    public final CarModelRepository carModelRepository;

    @Autowired
    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }


    // READ : Find all Models
    public List<CarModel> readAllCarModels() {
        return this.carModelRepository.findAll();
    }

    // READ :  Brand by Name
    public CarModel readByName(String name) {
        return carModelRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Car model not found"));
    }

}

