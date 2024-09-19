package org.rent_master.car_rental_reservation_system.DTO.reservation;

import lombok.Builder;
import lombok.Data;
import org.rent_master.car_rental_reservation_system.models.business.Branch;

@Data
@Builder
public class BranchInfoDTO {

    private Long id;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String name;
    private String logoUrl;

    public static BranchInfoDTO fromBranch(Branch branch) {
        return BranchInfoDTO.builder()
                .id(branch.getId())
                .address1(branch.getAddress1())
                .address2(branch.getAddress2())
                .city(branch.getCity())
                .country(branch.getCountry())
                .build();
    }
}
