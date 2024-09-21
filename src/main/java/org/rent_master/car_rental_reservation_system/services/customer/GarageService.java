package org.rent_master.car_rental_reservation_system.services.customer;


import jakarta.persistence.EntityNotFoundException;
import org.rent_master.car_rental_reservation_system.models.car.Car;
import org.rent_master.car_rental_reservation_system.models.customer.Garage;
import org.rent_master.car_rental_reservation_system.repositories.customer.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarageService {

    private final GarageRepository garageRepository;

    @Autowired
    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }


    // CREATE : Create new Garage
    public void save(Garage garage) {
        this.garageRepository.save(garage);
    }

    // READ : Get Cars from Garage
    public List<Car> readCarsFromGarage(Long garageId) {
        Optional<Garage> optionalGarage = garageRepository.findById(garageId);
        if (optionalGarage.isPresent()) {
            Garage garage = optionalGarage.get();
            return garage.getGarageCars();
        } else {
            throw new EntityNotFoundException("Garage not found with id: " + garageId);
        }
    }

    // UPDATE : Add Car to Garage
    public void updateGarageByNewCar(Long garageId, Car car) {
        Optional<Garage> optionalGarage = garageRepository.findById(garageId);
        if (optionalGarage.isPresent()) {
            Garage garage = optionalGarage.get();
            garage.getGarageCars().add(car);
            garageRepository.save(garage);
        } else {
            throw new EntityNotFoundException("Garage not found with id: " + garageId);
        }
    }

    // DELETE : Remove Car to Garage
    public void deleteCarFromGarage(Long garageId, Long carId) {
        Optional<Garage> optionalGarage = garageRepository.findById(garageId);
        if (optionalGarage.isPresent()) {
            Garage garage = optionalGarage.get();
            garage.getGarageCars().removeIf(car -> car.getId().equals(carId));
            garageRepository.save(garage);
        } else {
            throw new EntityNotFoundException("Garage not found with id: " + garageId);
        }
    }

}

