package org.rent_master.car_rental_reservation_system.models.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.user.User;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class Rental extends User {

    @NotNull
    @NotBlank(message = "Company name is required")
    private String name;

    @NotNull
    @NotBlank(message = "Your logotype image is required")
    private String logo;

    @NotNull
    @NotBlank(message = "Owner name is required")
    private String owner;

    @NotNull
    @NotBlank(message = "Your location is required")
    private String address1;

    private String address2;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL)
    private List<Branch> branches;

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", owner='" + owner + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' + '}';
    }


}