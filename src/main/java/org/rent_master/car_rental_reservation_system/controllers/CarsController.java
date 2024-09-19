package org.rent_master.car_rental_reservation_system.controllers;

import org.rent_master.car_rental_reservation_system.DTO.car.CarDTO;
import org.rent_master.car_rental_reservation_system.services.car.*;
import org.rent_master.car_rental_reservation_system.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@RestController
@RequestMapping("/api/cars")

public class CarsController {
    private final CarService carService;


    @Value("${car.upload-dir}")
    private String carUploadDir;

    @Autowired
    public CarsController(CarService carService) {
        this.carService = carService;
    }


    // GET : Fetch all Cars
    @GetMapping("")
    public ResponseEntity<List<CarDTO>> readCarsData() {
        List<CarDTO> carDTOs = carService.readAllCars();
        return ResponseEntity.ok(carDTOs);
    }

    // GET : Fetch Cars Images for Browser
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> readCarImage(@PathVariable String imageName) {
        Path imagePath = Paths.get(carUploadDir + imageName);
        Resource resource;

        try {
            resource = new UrlResource(imagePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                String contentType = FileUtils.determineContentType(imageName);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}


