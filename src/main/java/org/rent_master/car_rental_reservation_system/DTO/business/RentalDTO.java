package org.rent_master.car_rental_reservation_system.DTO.business;

import lombok.Builder;
import lombok.Data;
import org.rent_master.car_rental_reservation_system.models.business.Rental;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class RentalDTO {

    private Long id;
    private String name;
    private String owner;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private MultipartFile logo;
    private List<BranchDTO> branches;

    public static RentalDTO convertToDTO(Rental rental) {
        List<BranchDTO> branchDTOs = rental.getBranches().stream()
                .map(BranchDTO::convertToDTO)
                .collect(Collectors.toList());

        return RentalDTO.builder()
                .id(rental.getId())
                .name(rental.getName())
                .owner(rental.getOwner())
                .email(rental.getEmail())
                .address1(rental.getAddress1())
                .address2(rental.getAddress2())
                .city(rental.getCity())
                .country(rental.getCountry())
                .branches(branchDTOs)
                .build();
    }

}

