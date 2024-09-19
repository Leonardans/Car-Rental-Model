package org.rent_master.car_rental_reservation_system.controllers;

import org.rent_master.car_rental_reservation_system.DTO.reservation.RentalPartnerDTO;
import org.rent_master.car_rental_reservation_system.models.business.Rental;
import org.rent_master.car_rental_reservation_system.services.business.RentalService;
import org.rent_master.car_rental_reservation_system.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rental-partners")
public class RentalPartnersController {

    private final RentalService rentalService;

    @Value("${logo.upload-dir}")
    private String logoUploadDir;
    @Value("${logo.resource-url}")
    private String logoResourceUrl;

    @Autowired
    public RentalPartnersController(RentalService rentalService) {
        this.rentalService = rentalService;
    }


    // GET : Get all partners logos for slider
    @GetMapping("/logos")
    public ResponseEntity<List<RentalPartnerDTO>> readARentalsLogos() {
        List<Rental> rentalOffices = rentalService.readAllRentals();
        List<RentalPartnerDTO> partners = new ArrayList<>();

        for (Rental office : rentalOffices) {
            RentalPartnerDTO logoDTO = new RentalPartnerDTO();
            logoDTO.setName(office.getName());

            String logoUrl = logoResourceUrl + office.getLogo();
            logoDTO.setLogoUrl(logoUrl);

            partners.add(logoDTO);
        }

        return ResponseEntity.ok(partners);
    }

    // GET : For Browser Logo fetching
    @GetMapping("/logos/{filename:.+}")
    public ResponseEntity<Resource> readRentalLogo(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(logoUploadDir + filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                String contentType = FileUtils.determineContentType(filename);
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
