package org.rent_master.car_rental_reservation_system.services.car;

import jakarta.persistence.EntityNotFoundException;
import org.rent_master.car_rental_reservation_system.models.car.Brand;
import org.rent_master.car_rental_reservation_system.repositories.car.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    public final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    // READ : Find all Brands
    public List<Brand> readAllBrands() {
        return this.brandRepository.findAll();
    }

    // READ :  Brand by Name
    public Brand readByName(String name) {
        return brandRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found"));
    }

}

